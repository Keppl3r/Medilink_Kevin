package presentacion.auditarTransacciones;


import dto.FiltrosBusquedaDTO;
import dto.TransaccionDTO;
import auditarTransacciones.excepciones.NegocioException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.List;
import main.Main;

/**
 * 
 * @author keppler
 */
public class FrmBusqueda extends VBox {

    private final CoordinadorAuditarTransacciones coordinador;

    public FrmBusqueda() {
        this.coordinador = new CoordinadorAuditarTransacciones();
        setSpacing(20);
        setPadding(new Insets(20, 40, 20, 40));

        Button btnAtras = new Button("Atrás");
        btnAtras.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnAtras.setOnAction(e -> Main.mostrarInicioAuditar());

        Label titulo = new Label("Búsqueda de transacciones");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        HBox paneles = new HBox(30, crearPanelPeriodo(), crearPanelNombre());
        paneles.setAlignment(Pos.CENTER);

        getChildren().addAll(btnAtras, titulo, paneles);
    }

    private VBox crearPanelPeriodo() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-border-color: #1280E3; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: white;");
        panel.setPrefWidth(380);

        Label lbl = new Label("Búsqueda por periodo");
        lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        DatePicker dpInicio = new DatePicker();
        dpInicio.setPromptText("dd/mm/aaaa");
        DatePicker dpFin = new DatePicker();
        dpFin.setPromptText("dd/mm/aaaa");

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(5);
        grid.add(new Label("Fecha Inicio"), 0, 0);
        grid.add(new Label("Fecha Fin"), 1, 0);
        grid.add(dpInicio, 0, 1);
        grid.add(dpFin, 1, 1);

        Button btn = new Button("Buscar          ");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> {
            if (dpInicio.getValue() == null || dpFin.getValue() == null) {
                Main.mostrarErrorFechas(() -> Main.mostrarBusqueda());
                return;
            }
            FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
            filtros.setInicio(dpInicio.getValue().atStartOfDay());
            filtros.setFin(dpFin.getValue().atTime(23, 59, 59));
            try {
                List<TransaccionDTO> lista = coordinador.buscarPorPeriodo(filtros);
                if (lista.isEmpty()) {
                    Main.mostrarSinResultados(() -> Main.mostrarBusqueda());
                } else {
                    Main.mostrarLista(lista);
                }
            } catch (NegocioException ex) {
                Main.mostrarErrorFechas(() -> Main.mostrarBusqueda());
            }
        });

        panel.getChildren().addAll(lbl, grid, btn);
        return panel;
    }

    private VBox crearPanelNombre() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-border-color: #1280E3; -fx-border-radius: 8; -fx-background-radius: 8; -fx-background-color: white;");
        panel.setPrefWidth(380);

        Label lbl = new Label("Búsqueda por nombre");
        lbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblNombre = new Label("Nombre");
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Kevin Mendoza");

        Button btn = new Button("Buscar          ");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 8; -fx-cursor: hand;");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> {
            if (txtNombre.getText().isBlank()) {
                Main.mostrarSinResultados(() -> Main.mostrarBusqueda());
                return;
            }
            FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
            filtros.setNombrePaciente(txtNombre.getText().trim());
            try {
                List<TransaccionDTO> lista= coordinador.buscarPorPaciente(filtros);
                if (lista.isEmpty()) {
                    Main.mostrarSinResultados(() -> Main.mostrarBusqueda());
                } else {
                    Main.mostrarLista(lista);
                }
            } catch (NegocioException ex) {
                Main.mostrarSinResultados(() -> Main.mostrarBusqueda());
            }
        });
        panel.getChildren().addAll(lbl, lblNombre, txtNombre, btn);
        return panel;
    }
}
