/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import objetosNegocio.Cita;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;
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
public class CitaDocumentoAdaptador {

    public Document convertirADocumento(Cita cita) {
        Document doc = new Document("_id", cita.getId())
                .append("fecha", aDate(cita.getFecha()))
                .append("hora", cita.getHora())
                .append("ubicacion", cita.getUbicacion())
                .append("motivo", cita.getMotivo())
                .append("sintomas", cita.getSintomas())
                .append("estado", cita.getEstado())
                .append("monto", cita.getMonto())
                .append("paciente", new Document("id", cita.getPaciente().getId())
                        .append("nombre", cita.getPaciente().getNombre()))
                .append("medico", new Document("id", cita.getMedico().getId())
                        .append("nombre", cita.getMedico().getNombre())
                        .append("especialidad", cita.getMedico().getEspecialidad()))
                .append("pago", new Document("referencia_stripe", cita.getReferenciaStripe())
                        .append("monto_pagado", cita.getMontoPagado())
                        .append("mensaje_estado_pago", cita.getMensajeEstadoPago()));
        return doc;
    }

    public Cita convertirAEntidad(Document doc) {
        Cita cita = new Cita();
        cita.setId(doc.getString("_id"));
        cita.setFecha(aLocalDateTime(doc.getDate("fecha")));
        cita.setHora(doc.getString("hora"));
        cita.setUbicacion(doc.getString("ubicacion"));
        cita.setMotivo(doc.getString("motivo"));
        cita.setSintomas(doc.getList("sintomas", String.class));
        cita.setEstado(doc.getString("estado"));
        cita.setMonto(doc.getDouble("monto"));

        Document pac = doc.get("paciente", Document.class);
        if (pac != null) {
            Paciente p = new Paciente();
            p.setId(pac.getInteger("id"));
            p.setNombre(pac.getString("nombre"));
            cita.setPaciente(p);
        }

        Document med = doc.get("medico", Document.class);
        if (med != null) {
            Doctor d = new Doctor();
            d.setId(med.getInteger("id"));
            d.setNombre(med.getString("nombre"));
            d.setEspecialidad(med.getString("especialidad"));
            cita.setMedico(d);
        }

        Document pago = doc.get("pago", Document.class);
        if (pago != null) {
            cita.setReferenciaStripe(pago.getString("referencia_stripe"));
            cita.setMontoPagado(pago.getDouble("monto_pagado"));
            cita.setMensajeEstadoPago(pago.getString("mensaje_estado_pago"));
        }

        return cita;
    }

    private Date aDate(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        }
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime aLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
