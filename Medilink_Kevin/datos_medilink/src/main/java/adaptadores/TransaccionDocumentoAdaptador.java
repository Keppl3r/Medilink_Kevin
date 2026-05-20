/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidadesMongo.AuditoriaEmbebida;
import entidadesMongo.TransaccionMongoEntidad;
import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import java.util.ArrayList;
import java.util.List;

/**
 * Convierte entre Transaccion y un documento de Mongo
 *
 * @author keppler
 */
public class TransaccionDocumentoAdaptador {

    public TransaccionMongoEntidad convertirAMongo(Transaccion t) {
        if (t == null) return null;
        TransaccionMongoEntidad m = new TransaccionMongoEntidad();
        m.setFolio(t.getId());
        m.setFecha(t.getFecha());
        m.setEstado(t.getEstado());
        m.setIdPaciente(t.getIdPaciente());
        m.setNombrePaciente(t.getNombrePaciente());
        m.setIdMedico(t.getIdMedico());
        m.setNombreMedico(t.getNombreMedico());
        m.setTipoConsulta(t.getTipoConsulta());
        m.setMontoEsperado(t.getMontoEsperado());
        m.setReferenciaStripe(t.getReferenciaStripe());
        m.setMontoRecibido(t.getMontoRecibido());
        m.setMensajeEstado(t.getMensajeEstado());

        List<AuditoriaEmbebida> lista = new ArrayList<>();
        if (t.getAuditorias() != null) {
            for (Auditoria a : t.getAuditorias()) {
                AuditoriaEmbebida ae = new AuditoriaEmbebida();
                ae.setIdAuditoria(a.getIdAuditoria());
                ae.setFechaAuditoria(a.getFechaAuditoria());
                ae.setResultado(a.getResultado());
                ae.setIdAdministrador(a.getIdAdministrador());
                ae.setNombreAdministrador(a.getNombreAdministrador());
                lista.add(ae);
            }
        }
        m.setAuditorias(lista);
        return m;
    }

    public Transaccion convertirADominio(TransaccionMongoEntidad m) {
        if (m == null) return null;
        Transaccion t = new Transaccion();
        t.setId(m.getFolio());
        t.setFecha(m.getFecha());
        t.setEstado(m.getEstado());
        t.setIdPaciente(m.getIdPaciente());
        t.setNombrePaciente(m.getNombrePaciente());
        t.setIdMedico(m.getIdMedico());
        t.setNombreMedico(m.getNombreMedico());
        t.setTipoConsulta(m.getTipoConsulta());
        t.setMontoEsperado(m.getMontoEsperado());
        t.setReferenciaStripe(m.getReferenciaStripe());
        t.setMontoRecibido(m.getMontoRecibido());
        t.setMensajeEstado(m.getMensajeEstado());

        List<Auditoria> lista = new ArrayList<>();
        if (m.getAuditorias() != null) {
            for (AuditoriaEmbebida ae : m.getAuditorias()) {
                Auditoria a = new Auditoria();
                a.setIdAuditoria(ae.getIdAuditoria());
                a.setFechaAuditoria(ae.getFechaAuditoria());
                a.setResultado(ae.getResultado());
                a.setIdAdministrador(ae.getIdAdministrador());
                a.setNombreAdministrador(ae.getNombreAdministrador());
                lista.add(a);
            }
        }
        t.setAuditorias(lista);
        return t;
    }
}