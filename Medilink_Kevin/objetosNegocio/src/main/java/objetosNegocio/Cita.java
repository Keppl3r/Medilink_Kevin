/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * es una cita medica, el id se maneja en negocio, y el id de mongo se mantiene igual
 * @author keppler
 */
public class Cita {

    private String id;
    private LocalDateTime fecha;
    private String hora;
    private String ubicacion;
    private String motivo;
    private List<String> sintomas;
    private String estado;
    private Double monto;
    private Paciente paciente;
    private Doctor medico;
    private String referenciaStripe;
    private Double montoPagado;
    private String mensajeEstadoPago;

    public Cita() {
        this.sintomas = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public List<String> getSintomas() {
        return sintomas;
    }

    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Doctor getMedico() {
        return medico;
    }

    public void setMedico(Doctor medico) {
        this.medico = medico;
    }

    public String getReferenciaStripe() {
        return referenciaStripe;
    }

    public void setReferenciaStripe(String referenciaStripe) {
        this.referenciaStripe = referenciaStripe;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getMensajeEstadoPago() {
        return mensajeEstadoPago;
    }

    public void setMensajeEstadoPago(String mensajeEstadoPago) {
        this.mensajeEstadoPago = mensajeEstadoPago;
    }
}
