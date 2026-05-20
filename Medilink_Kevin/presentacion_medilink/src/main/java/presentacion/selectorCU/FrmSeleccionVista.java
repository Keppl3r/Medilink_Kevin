/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.selectorCU;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

/**
 *
 * @author keppler
 */
public class FrmSeleccionVista extends VBox {

    public FrmSeleccionVista() {
        setAlignment(Pos.CENTER);
        setSpacing(28);
        setPadding(new Insets(60));

        Label titulo = new Label("Bienvenido a Medilink");
        titulo.setStyle("-fx-font-size: 34px; -fx-font-weight: bold;");

        Label subtitulo = new Label("¿Cómo deseas ingresar?");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        VBox cardPaciente = crearCard("Paciente",
                "Agenda tu cita médica",
                e -> Main.mostrarInicioCita());

        VBox cardAdmin = crearCard("Administrador",
                "Audita pagos y facturaciones",
                e -> Main.mostrarInicioAuditar());

        HBox opciones = new HBox(30, cardPaciente, cardAdmin);
        opciones.setAlignment(Pos.CENTER);

        getChildren().addAll(titulo, subtitulo, opciones);
    }

    private VBox crearCard(String rol, String desc,
            javafx.event.EventHandler<javafx.event.ActionEvent> accion) {
        Label lblRol = new Label(rol);
        lblRol.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; "
                + "-fx-text-fill: #1280E3;");
        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");
        lblDesc.setWrapText(true);

        Button btn = new Button("Entrar como " + rol);
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-font-size: 14px; -fx-padding: 10 24; "
                + "-fx-background-radius: 8; -fx-cursor: hand;");
        btn.setOnAction(accion);

        VBox card = new VBox(12, lblRol, lblDesc, btn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setMinWidth(240);
        card.setStyle("-fx-background-color: #f5f7fa; "
                + "-fx-background-radius: 12; "
                + "-fx-border-color: #e0e4e8; -fx-border-radius: 12;");
        return card;
    }
}
