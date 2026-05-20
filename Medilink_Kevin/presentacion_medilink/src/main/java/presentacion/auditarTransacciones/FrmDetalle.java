package presentacion;

import dto.DetalleTransaccionDTO;
import auditarTransacciones.excepciones.NegocioException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author keppler
 */
public class FrmDetalle extends VBox {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final CoordinadorAuditarTransacciones coordinador;
    private final String idTransaccion;

    public FrmDetalle(String idTransaccion) {
        this.coordinador = new CoordinadorAuditarTransacciones();
        this.idTransaccion = idTransaccion;
        setSpacing(20);
        setPadding(new Insets(20, 40, 20, 40));

        Button btnAtras = new Button("Atrás");
        btnAtras.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnAtras.setOnAction(e -> Main.mostrarBusqueda());

        Label titulo = new Label("Información de transacción");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        try {
            DetalleTransaccionDTO det = coordinador.obtenerDetalle(idTransaccion);

            if (det.getReferenciaStripe() == null || det.getReferenciaStripe().isBlank()) {
                FrmFacturaNoEmitida.mostrar();
            }

            // Tarjetas
            HBox tarjetas = new HBox(20,
                    crearTarjetaDetalle(det),
                    crearTarjetaMontos(det),
                    crearTarjetaFacturacion(det));
            tarjetas.setAlignment(Pos.CENTER);

            // Botones
            Button btnPendiente = new Button("Transacción pendiente");
            btnPendiente.setStyle("-fx-background-color: #E8A317; -fx-text-fill: white; "
                    + "-fx-font-size: 14px; -fx-padding: 12 30; -fx-background-radius: 8; -fx-cursor: hand;");
            btnPendiente.setOnAction(e -> marcarPendiente());

            Button btnAuditar = new Button("Transacción auditada");
            btnAuditar.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                    + "-fx-font-size: 14px; -fx-padding: 12 30; -fx-background-radius: 8; -fx-cursor: hand;");
            btnAuditar.setOnAction(e -> auditar());

            HBox botones = new HBox(20, btnPendiente, btnAuditar);
            botones.setAlignment(Pos.CENTER);

            getChildren().addAll(btnAtras, titulo, tarjetas, botones);

        } catch (NegocioException e) {
            getChildren().addAll(btnAtras, titulo, new Label("Error: " + e.getMessage()));
        }
    }

    private VBox crearTarjetaDetalle(DetalleTransaccionDTO d) {
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(15);
        grid.add(new Label("ID:"), 0, 0);
        grid.add(new Label(d.getId()), 1, 0);
        grid.add(new Label("Fecha:"), 0, 1);
        grid.add(new Label(d.getFecha() != null ? d.getFecha().format(FMT) : ""), 1, 1);
        grid.add(new Label("Paciente:"), 0, 2);
        grid.add(new Label(d.getNombrePaciente()), 1, 2);
        grid.add(new Label("Médico:"), 0, 3);
        grid.add(new Label(d.getNombreMedico()), 1, 3);
        grid.add(new Label("Tipo:"), 0, 4);
        grid.add(new Label(d.getTipoConsulta()), 1, 4);
        return envolverTarjeta("Detalle de transacción", grid);
    }

    private VBox crearTarjetaMontos(DetalleTransaccionDTO d) {
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(15);
        grid.add(new Label("Monto esperado:"), 0, 0);
        grid.add(new Label("$" + d.getMontoEsperado()), 1, 0);
        grid.add(new Label("Monto recibido:"), 0, 1);
        grid.add(new Label("$" + d.getMontoRecibido()), 1, 1);
        grid.add(new Label("Mensaje Stripe:"), 0, 2);
        grid.add(new Label(d.getMensajeEstado()), 1, 2);
        return envolverTarjeta("Montos", grid);
    }

    private VBox crearTarjetaFacturacion(DetalleTransaccionDTO d) {
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(15);
        grid.add(new Label("Folio:"), 0, 0);
        grid.add(new Label(d.getReferenciaStripe() != null ? d.getReferenciaStripe() : "N/A"), 1, 0);
        grid.add(new Label("Estado:"), 0, 1);
        grid.add(new Label(d.getEstado()), 1, 1);
        return envolverTarjeta("Facturación", grid);
    }

    private VBox envolverTarjeta(String titulo, GridPane contenido) {
        Label lbl = new Label(titulo);
        lbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        VBox tarjeta = new VBox(10, lbl, contenido);
        tarjeta.setPadding(new Insets(15));
        tarjeta.setStyle("-fx-border-color: #1280E3; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: white;");
        tarjeta.setPrefWidth(270);
        return tarjeta;
    }

    private void auditar() {
        try {
            coordinador.auditarTransaccion(idTransaccion);
            Main.mostrarConfirmacion("Transacción auditada",
                    "El registro ha sido actualizado correctamente");
        } catch (NegocioException e) {
            if (e.getMessage().contains("monto") || e.getMessage().contains("Inconsistencia")
                    || e.getMessage().contains("Stripe") || e.getMessage().contains("exitoso")) {
                FrmInconsistencia.mostrar(e.getMessage());
            } else {
                FrmFacturaNoEmitida.mostrar();
            }
        }
    }

    private void marcarPendiente() {
        try {
            coordinador.marcarPendiente(idTransaccion);
            Main.mostrarConfirmacion("Transacción marcada pendiente",
                    "El registro ha sido actualizado correctamente");
        } catch (NegocioException e) {
            FrmInconsistencia.mostrar(e.getMessage());
        }
    }
}
