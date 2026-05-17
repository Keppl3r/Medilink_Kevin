/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import objetosNegocio.Paciente;
import org.bson.Document;

/**
 *
 * @author keppler
 */
public class PacienteDocumentoAdaptador {

    public Document convertirADocumento(Paciente paciente) {
        return new Document("_id", paciente.getId())
                .append("nombre", paciente.getNombre())
                .append("correo", paciente.getCorreo())
                .append("telefono", paciente.getTelefono());
    }

    public Paciente convertirAEntidad(Document doc) {
        Paciente paciente = new Paciente();
        paciente.setId(doc.getInteger("_id"));
        paciente.setNombre(doc.getString("nombre"));
        paciente.setCorreo(doc.getString("correo"));
        paciente.setTelefono(doc.getString("telefono"));
        return paciente;
    }
}
