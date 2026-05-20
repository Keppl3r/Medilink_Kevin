/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

import dto.CitaDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.Main;

/**
 *
 * @author keppler
 */
public class FrmGracias extends VBox {

     public FrmGracias(CitaDTO cita) {
        setAlignment(Pos.CENTER);
        setSpacing(14);
        setPadding(new Insets(50));

        Label titulo = new Label("¡Cita agendada!");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; "
                + "-fx-text-fill: #1280E3;");

        Label info = new Label("Tu cita " + cita.getId()
                + " fue agendada con éxito. Recibirás el comprobante "
                + "y tu receta en tu correo electrónico.");
        info.setWrapText(true);
        info.setMaxWidth(440);
        info.setStyle("-fx-font-size: 14px;");

        TextField txtCorreo = new TextField();
        txtCorreo.setPromptText("Tu correo electrónico");
        txtCorreo.setMaxWidth(340);

        Button btnEnviar = new Button("Enviar confirmación");
        btnEnviar.setStyle("-fx-background-color: #1280E3; "
                + "-fx-text-fill: white; -fx-font-size: 15px; "
                + "-fx-padding: 10 28; -fx-background-radius: 8; "
                + "-fx-cursor: hand;");
        btnEnviar.setOnAction(e -> {
            if (txtCorreo.getText().isBlank()) {
                new Alert(Alert.AlertType.WARNING,
                        "Ingresa un correo").showAndWait();
                return;
            }
            try {
                new CoordinadorAgendarCita()
                        .enviarConfirmacion(cita.getId(), txtCorreo.getText());
                new Alert(Alert.AlertType.INFORMATION,
                        "Confirmación enviada a " + txtCorreo.getText())
                        .showAndWait();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage())
                        .showAndWait();
            }
        });

        Button btnInicio = new Button("Volver al inicio");
        btnInicio.setStyle("-fx-background-color: transparent; "
                + "-fx-text-fill: #1280E3; -fx-font-size: 14px; "
                + "-fx-cursor: hand;");
        btnInicio.setOnAction(e -> Main.mostrarSelector());

        getChildren().addAll(titulo, info, txtCorreo, btnEnviar, btnInicio);
    }
}
