/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;

/**
 * DTO con el detalle completo de una transacción para la pantalla de auditoría.
 *
 * @author keppler
 */
public class DetalleTransaccionDTO {

    private String id;
    private Date fecha;
    private String estado;
    private String nombrePaciente;
    private String nombreMedico;
    private String tipoConsulta;
    private Double montoEsperado;
    private Double montoRecibido;
    private String referenciaStripe;
    private String mensajeEstado;

    public DetalleTransaccionDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public Double getMontoEsperado() {
        return montoEsperado;
    }

    public void setMontoEsperado(Double montoEsperado) {
        this.montoEsperado = montoEsperado;
    }

    public Double getMontoRecibido() {
        return montoRecibido;
    }

    public void setMontoRecibido(Double montoRecibido) {
        this.montoRecibido = montoRecibido;
    }

    public String getReferenciaStripe() {
        return referenciaStripe;
    }

    public void setReferenciaStripe(String referenciaStripe) {
        this.referenciaStripe = referenciaStripe;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public void setMensajeEstado(String mensajeEstado) {
        this.mensajeEstado = mensajeEstado;
    }
}
