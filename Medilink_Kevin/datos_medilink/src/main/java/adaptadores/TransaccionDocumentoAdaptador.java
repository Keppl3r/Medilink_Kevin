/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Convierte entre Transaccion y un documento de Mongo
 *
 * @author keppler
 */
public class TransaccionDocumentoAdaptador {

    public Document convertirADocumento(Transaccion transaccion) {
        Document doc = new Document("_id", transaccion.getId())
                .append("fecha", aDate(transaccion.getFecha()))                .append("estado", transaccion.getEstado())
                .append("paciente", new Document("id", transaccion.getIdPaciente())
                .append("nombre", transaccion.getNombrePaciente()))
                .append("medico", new Document("id", transaccion.getIdMedico())
                .append("nombre", transaccion.getNombreMedico()))
                .append("servicio", new Document("tipo_consulta", transaccion.getTipoConsulta())
                .append("monto_esperado", transaccion.getMontoEsperado()))
                .append("pago", new Document("referencia_stripe", transaccion.getReferenciaStripe())
                        .append("monto_recibido", transaccion.getMontoRecibido())
                        .append("mensaje_estado", transaccion.getMensajeEstado()));

        // Auditorías
        List<Document> auditDocs = new ArrayList<>();
        if (transaccion.getAuditorias() != null) {
            for (Auditoria a : transaccion.getAuditorias()) {
                auditDocs.add(auditoriaADocumento(a));
            }
        }
        doc.append("auditorias", auditDocs);
        return doc;
    }

    public Transaccion convertirAEntidad(Document doc) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(doc.getString("_id"));
        transaccion.setFecha(aLocalDateTime(doc.getDate("fecha")));
        transaccion.setEstado(doc.getString("estado"));

        // Paciente 
        Document pac = doc.get("paciente", Document.class);
        if (pac != null) {
            transaccion.setIdPaciente(pac.getInteger("id"));
            transaccion.setNombrePaciente(pac.getString("nombre"));
        }

        // Médico 
        Document med = doc.get("medico", Document.class);
        if (med != null) {
            transaccion.setIdMedico(med.getInteger("id"));
            transaccion.setNombreMedico(med.getString("nombre"));
        }

        // Servicio 
        Document serv = doc.get("servicio", Document.class);
        if (serv != null) {
            transaccion.setTipoConsulta(serv.getString("tipo_consulta"));
            transaccion.setMontoEsperado(serv.getDouble("monto_esperado"));
        }

        // Pago 
        Document pago = doc.get("pago", Document.class);
        if (pago != null) {
            transaccion.setReferenciaStripe(pago.getString("referencia_stripe"));
            transaccion.setMontoRecibido(pago.getDouble("monto_recibido"));
            transaccion.setMensajeEstado(pago.getString("mensaje_estado"));
        }

        // Auditorías 
        List<Document> auditDocs = doc.getList("auditorias", Document.class);
        if (auditDocs != null) {
            List<Auditoria> auditorias = new ArrayList<>();
            for (Document aDoc : auditDocs) {
                auditorias.add(documentoAAuditoria(aDoc));
            }
            transaccion.setAuditorias(auditorias);
        }

        return transaccion;
    }

    public Document auditoriaADocumento(Auditoria a) {
        return new Document("id_auditoria", a.getIdAuditoria())
                .append("fecha_auditoria", aDate(a.getFechaAuditoria()))
                .append("resultado", a.getResultado())
                .append("administrador", new Document("id", a.getIdAdministrador())
                        .append("nombre", a.getNombreAdministrador()));
    }

    private Auditoria documentoAAuditoria(Document doc) {
        Auditoria auditoria = new Auditoria();
        auditoria.setIdAuditoria(doc.getInteger("id_auditoria"));
        auditoria.setFechaAuditoria(aLocalDateTime(doc.getDate("fecha_auditoria")));        
        auditoria.setResultado(doc.getString("resultado"));
        Document admin = doc.get("administrador", Document.class);
        if (admin != null) {
            auditoria.setIdAdministrador(admin.getInteger("id"));
            auditoria.setNombreAdministrador(admin.getString("nombre"));
        }
        return auditoria;
    }

    //utilerías para convertir de Date a LocalDateTime para poder usar el now y no batallar
    private Date aDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime aLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
