/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 * Clase dominio de auditoria, asociada con la transacción.
 * @author keppler
 */
public class Auditoria {

    private Integer id;
    private Date fechaAuditoria;
    private String auditor;
    private String resultado;
    private Integer idTransaccion;

    public Auditoria() {
    }

    public Auditoria(Integer id, Date fechaAuditoria, String auditor,
            String resultado, Integer idTransaccion) {
        this.id = id;
        this.fechaAuditoria = fechaAuditoria;
        this.auditor = auditor;
        this.resultado = resultado;
        this.idTransaccion = idTransaccion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(Integer idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public String toString() {
        return "Auditoria{" + "id=" + id + ", fechaAuditoria=" + fechaAuditoria + ", auditor=" + auditor + ", resultado=" + resultado + ", idTransaccion=" + idTransaccion + '}';
    }
    
    
}
