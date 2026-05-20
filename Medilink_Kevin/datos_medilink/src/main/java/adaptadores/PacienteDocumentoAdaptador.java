/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidadesMongo.PacienteMongoEntidad;
import objetosNegocio.Paciente;

/**
 *
 * @author keppler
 */
public class PacienteDocumentoAdaptador {

    public PacienteMongoEntidad convertirAMongo(Paciente p) {
        if (p == null) return null;
        PacienteMongoEntidad m = new PacienteMongoEntidad();
        m.setIdNegocio(p.getId());
        m.setNombre(p.getNombre());
        m.setCorreo(p.getCorreo());
        return m;
    }

    public Paciente convertirADominio(PacienteMongoEntidad m) {
        if (m == null) return null;
        Paciente p = new Paciente();
        p.setId(m.getIdNegocio());
        p.setNombre(m.getNombre());
        p.setCorreo(m.getCorreo());
        return p;
    }
}
