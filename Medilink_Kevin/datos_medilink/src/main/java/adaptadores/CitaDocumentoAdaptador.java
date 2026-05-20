/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidadesMongo.CitaMongoEntidad;
import entidadesMongo.DoctorEmbebido;
import entidadesMongo.PacienteEmbebido;
import objetosNegocio.Cita;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;

/**
 * Convierte entre Transaccion y un documento de Mongo
 *
 * @author keppler
 */
public class CitaDocumentoAdaptador {

   
    public CitaMongoEntidad convertirAMongo(Cita c) {
        if (c == null) return null;
        CitaMongoEntidad m = new CitaMongoEntidad();
        m.setFolio(c.getId());
        m.setFecha(c.getFecha());
        m.setHora(c.getHora());
        m.setUbicacion(c.getUbicacion());
        m.setMotivo(c.getMotivo());
        m.setSintomas(c.getSintomas());
        m.setEstado(c.getEstado());
        m.setMonto(c.getMonto());
        if (c.getPaciente() != null) {
            PacienteEmbebido pe = new PacienteEmbebido();
            pe.setId(c.getPaciente().getId());
            pe.setNombre(c.getPaciente().getNombre());
            m.setPaciente(pe);
        }
        if (c.getMedico() != null) {
            DoctorEmbebido de = new DoctorEmbebido();
            de.setId(c.getMedico().getId());
            de.setNombre(c.getMedico().getNombre());
            de.setEspecialidad(c.getMedico().getEspecialidad());
            m.setMedico(de);
        }
        m.setReferenciaStripe(c.getReferenciaStripe());
        m.setMontoPagado(c.getMontoPagado());
        m.setMensajeEstadoPago(c.getMensajeEstadoPago());
        return m;
    }

    public Cita convertirADominio(CitaMongoEntidad m) {
        if (m == null) return null;
        Cita c = new Cita();
        c.setId(m.getFolio());
        c.setFecha(m.getFecha());
        c.setHora(m.getHora());
        c.setUbicacion(m.getUbicacion());
        c.setMotivo(m.getMotivo());
        c.setSintomas(m.getSintomas());
        c.setEstado(m.getEstado());
        c.setMonto(m.getMonto());
        if (m.getPaciente() != null) {
            Paciente p = new Paciente();
            p.setId(m.getPaciente().getId());
            p.setNombre(m.getPaciente().getNombre());
            c.setPaciente(p);
        }
        if (m.getMedico() != null) {
            Doctor d = new Doctor();
            d.setId(m.getMedico().getId());
            d.setNombre(m.getMedico().getNombre());
            d.setEspecialidad(m.getMedico().getEspecialidad());
            c.setMedico(d);
        }
        c.setReferenciaStripe(m.getReferenciaStripe());
        c.setMontoPagado(m.getMontoPagado());
        c.setMensajeEstadoPago(m.getMensajeEstadoPago());
        return c;
    }
}
