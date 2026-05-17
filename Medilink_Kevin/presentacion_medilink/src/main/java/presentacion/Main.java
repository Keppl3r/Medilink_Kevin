/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author keppler
 */
public class Main extends Application {
private static BorderPane root;
    private static final String AZUL = "#1280E3";

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setTop(crearHeader());
        root.setStyle("-fx-background-color: white;");
        mostrarInicio();
        Scene scene = new Scene(root, 950, 620);
        stage.setTitle("Medilink — Auditar Transacciones");
        stage.setScene(scene);
        stage.show();
    }

    private HBox crearHeader() {
        Label logo = new Label("medilink");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnInicio = new Button("Inicio");
        btnInicio.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;");
        btnInicio.setOnAction(e -> mostrarInicio());
        HBox header = new HBox(logo, spacer, btnInicio);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: " + AZUL + "; -fx-padding: 12 20;");
        return header;
    }

    public static void mostrarInicio() {
        root.setCenter(new FrmInicio());
    }

    public static void mostrarBusqueda() {
        root.setCenter(new FrmBusqueda());
    }

    public static void mostrarLista(java.util.List<dto.TransaccionDTO> transacciones) {
        root.setCenter(new FrmListaTransacciones(transacciones));
    }

    public static void mostrarDetalle(String idTransaccion) {
        root.setCenter(new FrmDetalle(idTransaccion));
    }

    public static void mostrarConfirmacion(String titulo, String mensaje) {
        root.setCenter(new FrmConfirmacion(titulo, mensaje));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
