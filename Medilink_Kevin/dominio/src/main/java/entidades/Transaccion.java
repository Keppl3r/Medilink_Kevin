/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 * Clase de dominio de transacción, representa una consulta realizada y ya cobrada.
 * @author keppler
 */
public class Transaccion {

    private Integer id;
    private Date fecha;
    private Double montoCobrado;
    private String confirmacionStripe;
    private String estadoAuditoria; // puede ser PENDIENTE, AUDITADA
    private Integer idPaciente;
    private Integer idDoctor;
    private Integer idServicio;

    public Transaccion() {
    }

    public Transaccion(Integer id, Date fecha, Double montoCobrado,
            String confirmacionStripe, String estadoAuditoria,
            Integer idPaciente, Integer idDoctor, Integer idServicio) {
        this.id = id;
        this.fecha = fecha;
        this.montoCobrado = montoCobrado;
        this.confirmacionStripe = confirmacionStripe;
        this.estadoAuditoria = estadoAuditoria;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.idServicio = idServicio;
    }

    public Double obtenerMontoCobrado() {
        return montoCobrado;
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

    public String getConfirmacionStripe() {
        return confirmacionStripe;
    }

    public void setConfirmacionStripe(String confirmacionStripe) {
        this.confirmacionStripe = confirmacionStripe;
    }

    public String getEstadoAuditoria() {
        return estadoAuditoria;
    }

    public void setEstadoAuditoria(String estadoAuditoria) {
        this.estadoAuditoria = estadoAuditoria;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(Integer idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    @Override
    public String toString() {
        return "Transaccion{" + "id=" + id + ", fecha=" + fecha + ", montoCobrado=" + montoCobrado + ", confirmacionStripe=" + confirmacionStripe + ", estadoAuditoria=" + estadoAuditoria + ", idPaciente=" + idPaciente + ", idDoctor=" + idDoctor + ", idServicio=" + idServicio + '}';
    }
    
}
