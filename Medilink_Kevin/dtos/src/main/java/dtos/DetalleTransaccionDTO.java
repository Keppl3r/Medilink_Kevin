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

    private Integer id;
    private Date fecha;
    private Double montoCobrado;
    private Double montoEsperado;
    private String resultadoStripe;
    private String estadoAuditoria;
    private String nombrePaciente;
    private String nombreDoctor;
    private Boolean facturaEmitida;
    private String folioFactura;

    public DetalleTransaccionDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMontoCobrado() {
        return montoCobrado;
    }

    public void setMontoCobrado(Double montoCobrado) {
        this.montoCobrado = montoCobrado;
    }

    public Double getMontoEsperado() {
        return montoEsperado;
    }

    public void setMontoEsperado(Double montoEsperado) {
        this.montoEsperado = montoEsperado;
    }

    public String getResultadoStripe() {
        return resultadoStripe;
    }

    public void setResultadoStripe(String resultadoStripe) {
        this.resultadoStripe = resultadoStripe;
    }

    public String getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(String estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public Boolean getFacturaEmitida() {
        return facturaEmitida;
    }

    public void setFacturaEmitida(Boolean facturaEmitida) {
        this.facturaEmitida = facturaEmitida;
    }

    public String getFolioFactura() {
        return folioFactura;
    }

    public void setFolioFactura(String folioFactura) {
        this.folioFactura = folioFactura;
    }
}
