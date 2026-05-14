/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidades.Transaccion;
import org.bson.Document;

/**
 * Convierte entre Transaccion y Document de Mongo
 *
 * @author keppler
 */
public class TransaccionDocumentoAdaptador {

    public Document convertirADocumento(Transaccion transaccion) {
        Document doc = new Document();
        if (transaccion.getId() != null) {
            doc.append("id", transaccion.getId());
        }
        doc.append("fecha", transaccion.getFecha())
                .append("montoCobrado", transaccion.getMontoCobrado())
                .append("confirmacionStripe", transaccion.getConfirmacionStripe())
                .append("estadoAuditoria", transaccion.getEstadoAuditoria())
                .append("idPaciente", transaccion.getIdPaciente())
                .append("idDoctor", transaccion.getIdDoctor())
                .append("idServicio", transaccion.getIdServicio());
        return doc;
    }

    public Transaccion convertirAEntidad(Document doc) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(doc.getInteger("id"));
        transaccion.setFecha(doc.getDate("fecha"));
        transaccion.setMontoCobrado(doc.getDouble("montoCobrado"));
        transaccion.setConfirmacionStripe(doc.getString("confirmacionStripe"));
        transaccion.setEstadoAuditoria(doc.getString("estadoAuditoria"));
        transaccion.setIdPaciente(doc.getInteger("idPaciente"));
        transaccion.setIdDoctor(doc.getInteger("idDoctor"));
        transaccion.setIdServicio(doc.getInteger("idServicio"));
        return transaccion;
    }
}
