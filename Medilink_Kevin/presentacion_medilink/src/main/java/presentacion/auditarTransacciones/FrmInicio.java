/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.auditarTransacciones;

import auditarTransacciones.excepciones.NegocioException;
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

        Button btnReporte = new Button("Ver reporte por estado");
        VBox reporteBox = new VBox(8);
        reporteBox.setAlignment(Pos.CENTER);
        reporteBox.setPadding(new Insets(20, 0, 0, 0));

        btnReporte.setOnAction(e -> {
            reporteBox.getChildren().clear();
            try {
                var reporte = new CoordinadorAuditarTransacciones().reportePorEstado();
                if (reporte.isEmpty()) {
                    Label vacio = new Label("Sin datos para generar el reporte.");
                    vacio.setStyle("-fx-text-fill: #888;");
                    reporteBox.getChildren().add(vacio);
                    return;
                }
                Label tituloReporte = new Label("Reporte por estado");
                tituloReporte.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; "
                        + "-fx-padding: 0 0 6 0;");
                reporteBox.getChildren().add(tituloReporte);

                for (var r : reporte) {
                    HBox fila = new HBox(24);
                    fila.setAlignment(Pos.CENTER_LEFT);
                    fila.setStyle("-fx-background-color: #f5f7fa; "
                            + "-fx-background-radius: 8; -fx-padding: 10 18;");
                    fila.setMinWidth(420);

                    Label lblEstado = new Label(r.getEstado());
                    lblEstado.setStyle("-fx-font-weight: bold; -fx-text-fill: #1280E3; "
                            + "-fx-min-width: 90;");

                    Label lblTotal = new Label("Total: " + r.getTotalTransacciones());
                    lblTotal.setStyle("-fx-min-width: 80;");

                    Label lblProm = new Label(
                            String.format("Promedio: $%.2f", r.getMontoPromedio()));

                    fila.getChildren().addAll(lblEstado, lblTotal, lblProm);
                    reporteBox.getChildren().add(fila);
                }
            } catch (NegocioException ex) {
                Label err = new Label("Error al generar el reporte");
                err.setStyle("-fx-text-fill: #c0392b;");
                reporteBox.getChildren().add(err);
            }
        });

        getChildren().addAll(titulo, subtitulo, btn, btnReporte, reporteBox);
    }
}
