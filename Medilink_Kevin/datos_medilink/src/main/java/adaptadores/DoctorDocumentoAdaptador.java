/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidadesMongo.DoctorMongoEntidad;
import objetosNegocio.Doctor;

/**
 *
 * @author keppler
 */
public class DoctorDocumentoAdaptador {

   public DoctorMongoEntidad convertirAMongo(Doctor d) {
        if (d == null) return null;
        DoctorMongoEntidad m = new DoctorMongoEntidad();
        m.setIdNegocio(d.getId());
        m.setNombre(d.getNombre());
        m.setEspecialidad(d.getEspecialidad());
        m.setCostoConsulta(d.getCostoConsulta());
        m.setDisponible(d.getDisponible());
        return m;
    }

    public Doctor convertirADominio(DoctorMongoEntidad m) {
        if (m == null) return null;
        Doctor d = new Doctor();
        d.setId(m.getIdNegocio());
        d.setNombre(m.getNombre());
        d.setEspecialidad(m.getEspecialidad());
        d.setCostoConsulta(m.getCostoConsulta());
        d.setDisponible(m.getDisponible());
        return d;
    }
}