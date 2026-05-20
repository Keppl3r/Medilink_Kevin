/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

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
public class FrmInicio extends VBox {

    public FrmInicio() {
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(60));

        Label titulo = new Label("Hola, Paciente");
        titulo.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Label subtitulo = new Label("Bienvenido a Medilink");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");

        Button btn = new Button("Agendar Nueva Cita");
        btn.setStyle("-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-padding: 14 34; "
                + "-fx-background-radius: 8; -fx-cursor: hand;");
        btn.setOnAction(e -> Main.mostrarSeleccionEspecialista());

        getChildren().addAll(titulo, subtitulo, btn);
    }
}
