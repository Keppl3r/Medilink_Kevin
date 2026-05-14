/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDateTime;


/**
 * es un subdocumento que está ubicado en el arreglo dentro de Transaccion
 *
 * @author keppler
 */
public class Auditoria {

    private Integer idAuditoria;
    private LocalDateTime fechaAuditoria;
    private String resultado;       //puede ser Auditada o Pendiente
    private Integer idAdministrador;
    private String nombreAdministrador;

    public Auditoria() {
    }

    public Auditoria(Integer idAuditoria, LocalDateTime fechaAuditoria,
            String resultado, Integer idAdministrador,
            String nombreAdministrador) {
        this.idAuditoria = idAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.resultado = resultado;
        this.idAdministrador = idAdministrador;
        this.nombreAdministrador = nombreAdministrador;
    }

    public Integer getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(Integer idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public LocalDateTime getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(LocalDateTime fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Integer getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(Integer idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }
}
