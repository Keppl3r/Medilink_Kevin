/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.auditarTransacciones;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import main.Main;
/**
 *
 * @author keppler
 */
public class FrmConfirmacion extends VBox {

    public FrmConfirmacion(String titulo, String mensaje) {
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(80));

        Button btnAtras = new Button("← Atrás");
        btnAtras.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        btnAtras.setOnAction(e -> Main.mostrarBusqueda());

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label lblMensaje = new Label(mensaje);
        lblMensaje.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        Button btnRegresar = new Button("Regresar a la lista");
        btnRegresar.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-padding: 10 25; -fx-background-radius: 8; -fx-cursor: hand;");
        btnRegresar.setOnAction(e -> Main.mostrarBusqueda());

        getChildren().addAll(btnAtras, lblTitulo, lblMensaje, btnRegresar);
    }
}
