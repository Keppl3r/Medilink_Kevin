/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesMongo;

import java.time.LocalDateTime;

/**
 *
 * @author keppler
 */
public class AuditoriaEmbebida {

    private Integer idAuditoria;
    private LocalDateTime fechaAuditoria;
    private String resultado;
    private Integer idAdministrador;
    private String nombreAdministrador;

    public AuditoriaEmbebida() {
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

    public void setFechaAuditoria(LocalDateTime f) {
        this.fechaAuditoria = f;
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

    public void setIdAdministrador(Integer id) {
        this.idAdministrador = id;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String n) {
        this.nombreAdministrador = n;
    }
}
