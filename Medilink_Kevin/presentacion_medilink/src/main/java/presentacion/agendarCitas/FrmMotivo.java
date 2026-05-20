/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

import dto.CitaDTO;
import dto.DoctorDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import main.Main;

/**
 *
 * @author keppler
 */
public class FrmMotivo extends VBox {

    public FrmMotivo(DoctorDTO doctor) {
        setAlignment(Pos.CENTER);
        setSpacing(16);
        setPadding(new Insets(40));

        Label titulo = new Label("¿Cuál es el motivo de tu visita?");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Tu nombre completo");
        txtNombre.setMaxWidth(460);

        TextArea txtMotivo = new TextArea();
        txtMotivo.setPromptText("Describe cómo te sientes...");
        txtMotivo.setMaxWidth(460);
        txtMotivo.setMaxHeight(110);

        ToggleButton tgFiebre = chip("Fiebre");
        ToggleButton tgDolor = chip("Dolor de cabeza");
        ToggleButton tgPresion = chip("Presión Alta");
        ToggleButton tgTos = chip("Tos");
        HBox chips = new HBox(10, tgFiebre, tgDolor, tgPresion, tgTos);
        chips.setAlignment(Pos.CENTER);

        Button btnAnalisis = new Button("Sube tu análisis médico  ⤒");
        Button btnHistorial = new Button("Sube tu historial clínico  ⤒");
        for (Button b : new Button[]{btnAnalisis, btnHistorial}) {
            b.setStyle("-fx-background-color: white; "
                    + "-fx-border-color: #ccc; -fx-border-radius: 8; "
                    + "-fx-text-fill: #555; -fx-cursor: hand; "
                    + "-fx-padding: 10 18;");
            b.setOnAction(e -> b.setText("Archivo cargado ✓"));
        }
        HBox archivos = new HBox(14, btnAnalisis, btnHistorial);
        archivos.setAlignment(Pos.CENTER);

        Button enviar = new Button("Enviar");
        enviar.setStyle("-fx-background-color: #1280E3; "
                + "-fx-text-fill: white; -fx-font-size: 15px; "
                + "-fx-padding: 10 30; -fx-background-radius: 8; "
                + "-fx-cursor: hand;");
        enviar.setOnAction(e -> {
            if (txtNombre.getText().isBlank()
                    || txtMotivo.getText().isBlank()) {
                Main.mostrarDatosFaltantes(() ->
                        Main.mostrarMotivo(doctor));
                return;
            }
            List<String> sintomas = new ArrayList<>();
            if (tgFiebre.isSelected()) sintomas.add("Fiebre");
            if (tgDolor.isSelected()) sintomas.add("Dolor de cabeza");
            if (tgPresion.isSelected()) sintomas.add("Presión Alta");
            if (tgTos.isSelected()) sintomas.add("Tos");

            CitaDTO dto = new CitaDTO();
            dto.setMotivo(txtMotivo.getText());
            dto.setSintomas(sintomas);
            dto.setIdMedico(doctor.getId());
            dto.setIdPaciente(1); // paciente sembrado (Nivel 1)
            dto.setNombrePaciente(txtNombre.getText());
            dto.setHora("10:00 AM - 10:45 AM");
            try {
                CitaDTO reg = new CoordinadorAgendarCita()
                        .registrarCita(dto);
                Main.mostrarResumen(reg);
            } catch (Exception ex) {
                Main.mostrarDatosFaltantes(() ->
                        Main.mostrarMotivo(doctor));
            }
        });

        VBox tarjeta = new VBox(14, txtMotivo, chips, archivos);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setMaxWidth(520);
        tarjeta.setPadding(new Insets(25));
        tarjeta.setStyle("-fx-border-color: #333; "
                + "-fx-border-radius: 4;");

        HBox pieEnviar = new HBox(enviar);
        pieEnviar.setAlignment(Pos.CENTER_RIGHT);
        pieEnviar.setMaxWidth(520);

        getChildren().addAll(titulo, txtNombre, tarjeta, pieEnviar);
    }

    private ToggleButton chip(String texto) {
        ToggleButton t = new ToggleButton(texto);
        t.setStyle("-fx-background-color: white; "
                + "-fx-border-color: #bbb; -fx-border-radius: 14; "
                + "-fx-background-radius: 14; -fx-padding: 5 14; "
                + "-fx-cursor: hand;");
        t.selectedProperty().addListener((o, a, sel) ->
            t.setStyle(sel
                ? "-fx-background-color: #5bc0de; -fx-text-fill: white; "
                  + "-fx-background-radius: 14; -fx-padding: 5 14; "
                  + "-fx-cursor: hand;"
                : "-fx-background-color: white; -fx-border-color: #bbb; "
                  + "-fx-border-radius: 14; -fx-background-radius: 14; "
                  + "-fx-padding: 5 14; -fx-cursor: hand;"));
        return t;
    }
}