/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 * Clase dominio para una factura asociada a una transacción
 *
 * @author keppler
 */
public class Factura {

    private String folio;
    private Date fechaEmision;
    private Double monto;
    private Double iva;
    private String estado;
    private Integer idTransaccion;

    public Factura() {
    }

    public Factura(String folio, Date fechaEmision, Double monto,
            Double iva, String estado, Integer idTransaccion) {
        this.folio = folio;
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.iva = iva;
        this.estado = estado;
        this.idTransaccion = idTransaccion;
    }

    public Boolean estaEmitida() {
        return folio != null && !folio.isBlank();
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public String toString() {
        return "Factura{" + "folio=" + folio + ", fechaEmision=" + fechaEmision + ", monto=" + monto + ", iva=" + iva + ", estado=" + estado + ", idTransaccion=" + idTransaccion + '}';
    }
    
}
