package entidadesMongo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 *
 * @author keppler
 */
public class CitaMongoEntidad {

    @BsonId
    private ObjectId id;
    private String folio;         
    private LocalDateTime fecha;
    private String hora;
    private String ubicacion;
    private String motivo;
    private List<String> sintomas;
    private String estado;
    private Double monto;
    private PacienteEmbebido paciente;
    private DoctorEmbebido medico;
    private String referenciaStripe;
    private Double montoPagado;
    private String mensajeEstadoPago;

    public CitaMongoEntidad() {
        this.sintomas = new ArrayList<>();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
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

    public PacienteEmbebido getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteEmbebido paciente) {
        this.paciente = paciente;
    }

    public DoctorEmbebido getMedico() {
        return medico;
    }

    public void setMedico(DoctorEmbebido medico) {
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
