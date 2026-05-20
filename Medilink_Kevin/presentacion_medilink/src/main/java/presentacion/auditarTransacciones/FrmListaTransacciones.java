package presentacion.auditarTransacciones;

import dto.TransaccionDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Main;

/**
 * @author keppler
 */
public class FrmListaTransacciones extends VBox {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<TransaccionDTO> transacciones;

    public FrmListaTransacciones(List<TransaccionDTO> transacciones) {
        this.transacciones = transacciones;
        setSpacing(15);
        setPadding(new Insets(20, 40, 20, 40));

        Button btnAtras = new Button("Atrás");
        btnAtras.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnAtras.setOnAction(e -> Main.mostrarBusqueda());

        Label titulo = new Label("Resultados de búsqueda");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        TableView<TransaccionDTO> tabla = crearTabla();

        getChildren().addAll(btnAtras, titulo, tabla);
    }

    @SuppressWarnings("unchecked")
    private TableView<TransaccionDTO> crearTabla() {
        TableView<TransaccionDTO> tabla = new TableView<>();
        tabla.setItems(FXCollections.observableArrayList(transacciones));

        TableColumn<TransaccionDTO, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getFecha() != null ? c.getValue().getFecha().format(FMT) : ""));
        colFecha.setPrefWidth(100);

        TableColumn<TransaccionDTO, String> colPaciente = new TableColumn<>("Paciente");
        colPaciente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombrePaciente()));
        colPaciente.setPrefWidth(200);

        TableColumn<TransaccionDTO, String> colMedico = new TableColumn<>("Médico");
        colMedico.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreMedico()));
        colMedico.setPrefWidth(180);

        TableColumn<TransaccionDTO, String> colMotivo = new TableColumn<>("Motivo");
        colMotivo.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getTipoConsulta() != null ? c.getValue().getTipoConsulta() : ""));
        colMotivo.setPrefWidth(160);

        TableColumn<TransaccionDTO, String> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getMontoRecibido() != null ? "$" + c.getValue().getMontoRecibido() : ""));
        colMonto.setPrefWidth(90);

        TableColumn<TransaccionDTO, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado()));
        colEstado.setPrefWidth(100);

        TableColumn<TransaccionDTO, Void> colAccion = new TableColumn<>("");
        colAccion.setPrefWidth(100);
        colAccion.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button();

            {
                btn.setOnAction(e -> {
                    TransaccionDTO t = getTableView().getItems().get(getIndex());
                    Main.mostrarDetalle(t.getId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    TransaccionDTO t = getTableView().getItems().get(getIndex());
                    if ("Pendiente".equals(t.getEstado())) {
                        btn.setText("Auditar");
                        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                                + "-fx-background-radius: 5; -fx-cursor: hand; -fx-font-size: 12px;");
                    } else {
                        btn.setText("Detalle");
                        btn.setStyle("-fx-background-color: #E8A317; -fx-text-fill: white; "
                                + "-fx-background-radius: 5; -fx-cursor: hand; -fx-font-size: 12px;");
                    }
                    setGraphic(btn);
                }
            }
        });
        tabla.getColumns().addAll(colFecha, colPaciente, colMedico, colMotivo, colMonto, colEstado, colAccion);
        return tabla;
    }
}
