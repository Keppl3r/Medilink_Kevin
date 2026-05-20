/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import dto.CitaDTO;
import dto.DoctorDTO;
import dto.TransaccionDTO;
import presentacion.selectorCU.FrmSeleccionVista;
import presentacion.auditarTransacciones.FrmBusqueda;
import presentacion.auditarTransacciones.FrmConfirmacion;
import presentacion.auditarTransacciones.FrmDetalle;
import presentacion.auditarTransacciones.FrmListaTransacciones;
import presentacion.agendarCitas.FrmSeleccionEspecialista;
import presentacion.agendarCitas.FrmMotivo;
import presentacion.agendarCitas.FrmResumen;
import presentacion.agendarCitas.FrmGracias;
import java.util.List;


/**
 *
 * @author keppler
 */
public class Main extends Application {

    private static BorderPane root;
    private static final String AZUL = "#1280E3";

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        root.setTop(crearHeader());
        root.setStyle("-fx-background-color: white;");
        mostrarSelector();
        Scene scene = new Scene(root, 950, 620);
        stage.setTitle("Medilink");
        stage.setScene(scene);
        stage.show();
    }

    private HBox crearHeader() {
        Label logo = new Label("medilink");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 18px; "+ "-fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnInicio = new Button("Inicio");
        btnInicio.setStyle("-fx-background-color: transparent; "+ "-fx-text-fill: white; -fx-font-size: 14px; "+ "-fx-cursor: hand;");
        btnInicio.setOnAction(e -> mostrarSelector());
        HBox header = new HBox(logo, spacer, btnInicio);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: " + AZUL+ "; -fx-padding: 12 20;");
        return header;
    }

    public static void mostrarSelector() {
        root.setCenter(new FrmSeleccionVista());
    }

    //agendar cita
    public static void mostrarInicioCita() {
        root.setCenter(new presentacion.agendarCitas.FrmInicio());
    }

    public static void mostrarSeleccionEspecialista() {
        root.setCenter(new FrmSeleccionEspecialista());
    }

    public static void mostrarMotivo(DoctorDTO doctor) {
        root.setCenter(new FrmMotivo(doctor));
    }

    public static void mostrarResumen(CitaDTO cita) {
        root.setCenter(new FrmResumen(cita));
    }

    public static void mostrarGracias(CitaDTO cita) {
        root.setCenter(new FrmGracias(cita));
    }
      public static void mostrarNoDisponible() {
        root.setCenter(new presentacion.agendarCitas.FrmNoDisponible(
                () -> mostrarSeleccionEspecialista()));
    }

    public static void mostrarDatosFaltantes(Runnable alVolver) {
        root.setCenter(new presentacion.agendarCitas.FrmDatosFaltantes(
                alVolver));
    }

    public static void mostrarDatosErroneos(Runnable alVolver) {
        root.setCenter(new presentacion.agendarCitas.FrmDatosErroneos(
                alVolver));
    }

    public static void mostrarFondosInsuficientes(Runnable alVolver) {
        root.setCenter(new presentacion.agendarCitas.FrmFondosInsuficientes(
                alVolver));
    }

    //auditar 
    public static void mostrarInicioAuditar() {
        root.setCenter(new presentacion.auditarTransacciones.FrmInicio());
    }

    public static void mostrarBusqueda() {
        root.setCenter(new FrmBusqueda());
    }

    public static void mostrarLista(List<TransaccionDTO> transacciones) {
        root.setCenter(new FrmListaTransacciones(transacciones));
    }

    public static void mostrarDetalle(String idTransaccion) {
        root.setCenter(new FrmDetalle(idTransaccion));
    }

    public static void mostrarConfirmacion(String titulo, String mensaje) {
        root.setCenter(new FrmConfirmacion(titulo, mensaje));
    }

     public static void mostrarErrorFechas(Runnable alVolver) {
        root.setCenter(new presentacion.auditarTransacciones
                .FrmErrorFechas(alVolver));
    }

    public static void mostrarSinResultados(Runnable alVolver) {
        root.setCenter(new presentacion.auditarTransacciones
                .FrmSinResultados(alVolver));
    }

    public static void mostrarFacturaNoEmitida(Runnable alVolver) {
        root.setCenter(new presentacion.auditarTransacciones
                .FrmFacturaNoEmitida(alVolver));
    }

    public static void mostrarInconsistencia(String detalle,
            Runnable alVolver) {
        root.setCenter(new presentacion.auditarTransacciones
                .FrmInconsistencia(detalle, alVolver));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
