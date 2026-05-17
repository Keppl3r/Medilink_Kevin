/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import objetosNegocio.Doctor;
import org.bson.Document;

/**
 *
 * @author keppler
 */
public class DoctorDocumentoAdaptador {

    public Document convertirADocumento(Doctor doctor) {
        return new Document("_id", doctor.getId())
                .append("nombre", doctor.getNombre())
                .append("especialidad", doctor.getEspecialidad())
                .append("costo_consulta", doctor.getCostoConsulta())
                .append("disponible", doctor.getDisponible());
    }

    public Doctor convertirAEntidad(Document doc) {
        Doctor doctor = new Doctor();
        doctor.setId(doc.getInteger("_id"));
        doctor.setNombre(doc.getString("nombre"));
        doctor.setEspecialidad(doc.getString("especialidad"));
        doctor.setCostoConsulta(doc.getDouble("costo_consulta"));
        doctor.setDisponible(doc.getBoolean("disponible"));
        return doctor;
    }
}
