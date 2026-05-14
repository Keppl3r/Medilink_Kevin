/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import excepciones.NegocioException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author keppler
 */
public class FrmInicio extends VBox {

    public FrmInicio() {
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(60));

        Label titulo = new Label("Hola, Administrador");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label subtitulo = new Label();
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        try {
            int count = new CoordinadorAuditarTransacciones().contarPendientes();
            subtitulo.setText("Tienes " + count + " transacciones por auditar");
        } catch (NegocioException e) {
            subtitulo.setText("Error al cargar pendientes");
        }

        Button btn = new Button("Auditoría de transacciones");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 12 30; -fx-background-radius: 8; -fx-cursor: hand;");
        btn.setOnAction(e -> Main.mostrarBusqueda());

        getChildren().addAll(titulo, subtitulo, btn);
    }
}
