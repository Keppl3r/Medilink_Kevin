/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

import agendarCitas.excepciones.NegocioAgendarException;
import dto.DoctorDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import main.Main;

/**
 *
 * @author keppler
 */
public class FrmSeleccionEspecialista extends VBox {

    private final FlowPane contenedor = new FlowPane();
    private List<DoctorDTO> todos = new ArrayList<>();

    public FrmSeleccionEspecialista() {
        setSpacing(15);
        setPadding(new Insets(30));

        Label titulo = new Label("Selecciona un especialista");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        Label sub = new Label("Encuentra el profesional adecuado "+ "para tu atención médica");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");


        Button btnTodos = new Button("Todos");
        btnTodos.setStyle("-fx-background-color: #1280E3; "
                + "-fx-text-fill: white; -fx-background-radius: 14; "
                + "-fx-padding: 5 16; -fx-cursor: hand;");

        ComboBox<String> cboEsp = new ComboBox<>();
        cboEsp.setPromptText("Especialidad");

        try {
            todos = new CoordinadorAgendarCita().obtenerEspecialistas();
        } catch (NegocioAgendarException e) {
            sub.setText("Error al cargar especialistas: "
                    + e.getMessage());
        }


        Set<String> especialidades = new LinkedHashSet<>();
        for (DoctorDTO d : todos) {
            especialidades.add(d.getEspecialidad());
        }
        cboEsp.getItems().addAll(especialidades);

        btnTodos.setOnAction(e -> {
            cboEsp.getSelectionModel().clearSelection();
            pintarTarjetas(todos);
        });
        cboEsp.setOnAction(e -> {
            String esp = cboEsp.getValue();
            if (esp == null) {
                pintarTarjetas(todos);
                return;
            }
            List<DoctorDTO> filtrados = new ArrayList<>();
            for (DoctorDTO d : todos) {
                if (esp.equals(d.getEspecialidad())) {
                    filtrados.add(d);
                }
            }
            pintarTarjetas(filtrados);
        });

        HBox filtros = new HBox(10, btnTodos, cboEsp);
        filtros.setAlignment(Pos.CENTER_LEFT);

        contenedor.setHgap(18);
        contenedor.setVgap(18);
        contenedor.setPadding(new Insets(15, 0, 0, 0));
        ScrollPane scroll = new ScrollPane(contenedor);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scroll, javafx.scene.layout.Priority.ALWAYS);

        pintarTarjetas(todos);

        getChildren().addAll(titulo, sub, filtros, scroll);
    }

    private void pintarTarjetas(List<DoctorDTO> doctores) {
        contenedor.getChildren().clear();
        for (DoctorDTO d : doctores) {
            contenedor.getChildren().add(crearTarjeta(d));
        }
    }

    private VBox crearTarjeta(DoctorDTO d) {
        boolean disponible = Boolean.TRUE.equals(d.getDisponible());

        Label estado = new Label(disponible ? "● Disponible" : "● Ocupado");
        estado.setStyle("-fx-font-size: 12px; -fx-text-fill: "
                + (disponible ? "#2Ba84a" : "#d23b3b") + ";");

        Label nombre = new Label(d.getNombre());
        nombre.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label esp = new Label(d.getEspecialidad());
        esp.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");

        Label costo = new Label("$" + d.getCostoConsulta());
        costo.setStyle("-fx-font-size: 13px; -fx-text-fill: #1280E3; "
                + "-fx-font-weight: bold;");

        Button btn = new Button(disponible ? "Agendar Cita"
                : "No Disponible");
        btn.setDisable(!disponible);
        btn.setStyle(disponible
                ? "-fx-background-color: #1280E3; -fx-text-fill: white; "
                + "-fx-background-radius: 6; -fx-cursor: hand; "
                + "-fx-padding: 6 18;"
                : "-fx-background-color: #cfd4da; -fx-text-fill: #888; "
                + "-fx-background-radius: 6; -fx-padding: 6 18;");
        btn.setOnAction(e -> {
            try {
                boolean disp = new CoordinadorAgendarCita()
                        .verificarDisponibilidad(d.getId());
                if (disp) {
                    Main.mostrarMotivo(d);
                } else {
                    Main.mostrarNoDisponible();
                }
            } catch (NegocioAgendarException ex) {
                Main.mostrarNoDisponible();
            }
        });

        VBox card = new VBox(8, estado, nombre, esp, costo, btn);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setMinWidth(170);
        card.setMaxWidth(170);
        card.setStyle("-fx-background-color: white; "
                + "-fx-border-color: #e3e7eb; -fx-border-radius: 10; "
                + "-fx-background-radius: 10;");
        return card;
    }
}
