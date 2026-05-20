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
public class FrmErrorFechas {

    public static void mostrar() {
        Stage dialog = crearDialogo(
                "Rango de fechas inválido");
        dialog.showAndWait();
    }

    private static Stage crearDialogo(String mensaje) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Aviso");

        Label lbl = new Label(mensaje);
        lbl.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Button btn = new Button("Volver");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-padding: 10 30; -fx-background-radius: 8; -fx-cursor: hand;");
        btn.setOnAction(e -> dialog.close());

        VBox root = new VBox(25, lbl, btn);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: white;");

        dialog.setScene(new Scene(root, 420, 220));
        return dialog;
    }
}
