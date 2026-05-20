/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author keppler
 */
public class CitaDTO {

    private String id;
    private LocalDateTime fecha;
    private String hora;
    private String ubicacion;
    private String motivo;
    private List<String> sintomas;
    private String estado;
    private Double monto;
    private Integer idPaciente;
    private Integer idMedico;
    private String nombrePaciente;
    private String nombreMedico;
    private String especialidadMedico;

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

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String n) {
        this.nombrePaciente = n;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String n) {
        this.nombreMedico = n;
    }

    public String getEspecialidadMedico() {
        return especialidadMedico;
    }

    public void setEspecialidadMedico(String e) {
        this.especialidadMedico = e;
    }
}
