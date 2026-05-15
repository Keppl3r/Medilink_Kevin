/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author keppler
 */
public class FrmInconsistencia {

    public static void mostrar(String detalle) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Aviso");

        Label lbl = new Label("Inconsistencia de pago encontrada");
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label lblDetalle = new Label(detalle);
        lblDetalle.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");
        lblDetalle.setWrapText(true);

        Button btn = new Button("Volver");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-padding: 10 30; -fx-background-radius: 8; -fx-cursor: hand;");
        btn.setOnAction(e -> dialog.close());

        VBox root = new VBox(15, lbl, lblDetalle, btn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: white;");

        dialog.setScene(new Scene(root, 450, 240));
        dialog.showAndWait();
    }
}
