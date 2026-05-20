/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author keppler
 */
public class PagoCitaDTO {
    private String referenciaStripe;
    private Double montoPagado;
    private String mensajeEstadoPago;
    private String idCita;

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

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }


}
