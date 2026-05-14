/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidades.Auditoria;
import org.bson.Document;

/**
 * Convierte entre Auditoria y Document de Mongo
 *
 * @author keppler
 */
public class AuditoriaDocumentoAdaptador {

    public Document convertirADocumento(Auditoria auditoria) {
        Document doc = new Document();
        if (auditoria.getId() != null) {
            doc.append("id", auditoria.getId());
        }
        doc.append("fechaAuditoria", auditoria.getFechaAuditoria())
                .append("auditor", auditoria.getAuditor())
                .append("resultado", auditoria.getResultado())
                .append("idTransaccion", auditoria.getIdTransaccion());
        return doc;
    }

    public Auditoria convertirAEntidad(Document doc) {
        Auditoria auditoria = new Auditoria();
        auditoria.setId(doc.getInteger("id"));
        auditoria.setFechaAuditoria(doc.getDate("fechaAuditoria"));
        auditoria.setAuditor(doc.getString("auditor"));
        auditoria.setResultado(doc.getString("resultado"));
        auditoria.setIdTransaccion(doc.getInteger("idTransaccion"));
        return auditoria;
    }
}
