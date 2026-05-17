/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.DoctorDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import interfaces.IDoctorDAO;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Doctor;

/**
 *
 * @author keppler
 */
public class DoctorDAO implements IDoctorDAO {

    private static final Logger LOG = Logger.getLogger(DoctorDAO.class.getName());

    private final MongoCollection<Document> coleccion;
    private final DoctorDocumentoAdaptador adaptador;

    public DoctorDAO() {
        this.coleccion = MongoConection
                .obtenerBaseDatos()
                .getCollection("doctores");
        this.adaptador = new DoctorDocumentoAdaptador();
    }

    @Override
    public List<Doctor> buscarDisponibles() throws PersistenciaException {
        LOG.log(Level.INFO, "Buscando doctores disponibles");
        List<Document> docs = coleccion.find(Filters.eq("disponible", true))
                .into(new ArrayList<>());
        List<Doctor> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(adaptador.convertirAEntidad(doc));
        }
        return lista;
    }

    @Override
    public Doctor buscarPorId(Integer id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El ID no puede ser nulo");
        }
        LOG.log(Level.INFO, "Buscando doctor con ID: {0}", id);
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirAEntidad(doc);
    }
}
