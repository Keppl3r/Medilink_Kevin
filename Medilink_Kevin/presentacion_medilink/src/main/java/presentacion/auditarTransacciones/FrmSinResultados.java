/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.auditarTransacciones;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author keppler
 */
public class FrmSinResultados extends StackPane {

    public FrmSinResultados(Runnable alVolver) {
        setStyle("-fx-background-color: rgba(0,0,0,0.45);");
        setPrefSize(950, 560);

        Label titulo = new Label("Sin resultados");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label msg = new Label("No se encontraron transacciones "+ "con esos criterios");
        msg.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        Button volver = new Button("Volver");
        volver.setStyle("-fx-background-color: #1280E3; "+ "-fx-text-fill: white; -fx-padding: 8 28; "+ "-fx-background-radius: 8; -fx-cursor: hand;");
        volver.setOnAction(e -> alVolver.run());

        VBox card = new VBox(20, titulo, msg, volver);
        card.setAlignment(Pos.CENTER);
        card.setMaxSize(460, 280);
        card.setPadding(new Insets(40));
        card.setStyle("-fx-background-color: white; "+ "-fx-background-radius: 6;");

        getChildren().add(card);
    }
}