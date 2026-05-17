/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.PacienteDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import interfaces.IPacienteDAO;
import org.bson.Document;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Paciente;

/**
 *
 * @author keppler
 */
public class PacienteDAO implements IPacienteDAO {

    private static final Logger LOG = Logger.getLogger(PacienteDAO.class.getName());

    private final MongoCollection<Document> coleccion;
    private final PacienteDocumentoAdaptador adaptador;

    public PacienteDAO() {
        this.coleccion = MongoConection
                .obtenerBaseDatos()
                .getCollection("pacientes");
        this.adaptador = new PacienteDocumentoAdaptador();
    }

    @Override
    public Paciente buscarPorId(Integer id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El ID no puede ser nulo");
        }
        LOG.log(Level.INFO, "Buscando paciente con ID: {0}", id);
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirAEntidad(doc);
    }
}
