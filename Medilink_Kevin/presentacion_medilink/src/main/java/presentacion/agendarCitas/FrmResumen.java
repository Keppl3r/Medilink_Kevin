/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

import dto.CitaDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

/**
 *
 * @author keppler
 */
public class FrmResumen extends VBox {

     public FrmResumen(CitaDTO cita) {
        setSpacing(10);
        setPadding(new Insets(30));

        Label titulo = new Label("Resumen de Cita");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");
        Label sub = new Label("Revisa los detalles antes de "
                + "completar su pago");
        sub.setStyle("-fx-font-size: 14px; -fx-text-fill: #777;");


        Label estado = new Label("Disponible");
        estado.setStyle("-fx-text-fill: #1280E3; -fx-font-size: 12px;");
        Label med = new Label(cita.getNombreMedico());
        med.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label esp = new Label("Especialidad "
                + cita.getEspecialidadMedico());
        esp.setStyle("-fx-font-size: 12px; -fx-text-fill: #777;");
        Label fecha = new Label("📅  " + (cita.getFecha() != null
                ? cita.getFecha().toLocalDate() : "Por confirmar"));
        Label hora = new Label("🕐  " + (cita.getHora() != null
                ? cita.getHora() : "10:00 AM"));
        Label lugar = new Label("📍  " + (cita.getUbicacion() != null
                ? cita.getUbicacion() : "Centro Médico Medilink"));
        Label total = new Label("TOTAL A PAGAR:  $" + cita.getMonto());
        total.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; "
                + "-fx-text-fill: #1280E3;");

        VBox izq = new VBox(8, estado, med, esp, fecha, hora, lugar,
                new Label(" "), total);
        izq.setPadding(new Insets(22));
        izq.setMinWidth(260);
        izq.setStyle("-fx-background-color: white; "
                + "-fx-border-color: #e3e7eb; -fx-border-radius: 10; "
                + "-fx-background-radius: 10;");

        Label tituloPago = new Label("Información del pago");
        tituloPago.setStyle("-fx-font-size: 18px; "
                + "-fx-font-weight: bold;");

        TextField txtTitular = new TextField();
        txtTitular.setPromptText("Nombre como aparece en la tarjeta");
        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Número de tarjeta");
        TextField txtVenc = new TextField();
        txtVenc.setPromptText("MM/AA");
        TextField txtCvc = new TextField();
        txtCvc.setPromptText("CVC");
        HBox fila = new HBox(12, txtVenc, txtCvc);

        Button btn = new Button("Proceder al Pago  →");
        btn.setStyle("-fx-background-color: #1280E3; "
                + "-fx-text-fill: white; -fx-font-size: 15px; "
                + "-fx-padding: 10 28; -fx-background-radius: 8; "
                + "-fx-cursor: hand;");
        btn.setOnAction(e -> {

            if (txtTitular.getText().isBlank()
                    || txtNumero.getText().isBlank()) {
                Main.mostrarDatosFaltantes(() ->
                        Main.mostrarResumen(cita));
                return;
            }
            String datosPago = txtTitular.getText() + "|"
                    + txtNumero.getText() + "|" + txtVenc.getText()
                    + "|" + txtCvc.getText();
            try {
                String r = new CoordinadorAgendarCita()
                        .procesarPago(cita.getId(), datosPago);
                switch (r) {
                    case "EXITOSO" -> Main.mostrarGracias(cita);
                    case "FONDOS_INSUFICIENTES" ->
                        Main.mostrarFondosInsuficientes(() ->
                                Main.mostrarResumen(cita));
                    default ->
                        Main.mostrarDatosErroneos(() ->
                                Main.mostrarResumen(cita));
                }
            } catch (Exception ex) {
                Main.mostrarDatosErroneos(() ->
                        Main.mostrarResumen(cita));
            }
        });

        VBox der = new VBox(12, tituloPago,
                new Label("Titular de la tarjeta"), txtTitular,
                new Label("Número de tarjeta"), txtNumero,
                new Label("Vencimiento / CVC"), fila, btn);
        der.setPadding(new Insets(22));
        der.setMinWidth(380);
        der.setStyle("-fx-background-color: #eef1f4; "
                + "-fx-background-radius: 10;");

        HBox cols = new HBox(24, izq, der);
        cols.setAlignment(Pos.CENTER);

        getChildren().addAll(titulo, sub, cols);
    }
}