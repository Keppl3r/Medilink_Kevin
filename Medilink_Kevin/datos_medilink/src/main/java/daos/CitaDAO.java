/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.CitaDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import interfaces.ICitaDAO;
import org.bson.Document;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Cita;

/**
 *
 * @author keppler
 */
public class CitaDAO implements ICitaDAO {

    private static final Logger LOG = Logger.getLogger(CitaDAO.class.getName());

    private final MongoCollection<Document> coleccion;
    private final CitaDocumentoAdaptador adaptador;

    public CitaDAO() {
        this.coleccion = MongoConection
                .obtenerBaseDatos()
                .getCollection("citas");
        this.adaptador = new CitaDocumentoAdaptador();
    }

    @Override
    public Cita guardar(Cita cita) throws PersistenciaException {
        if (cita == null) {
            throw new PersistenciaException("La cita no puede ser nula");
        }
        LOG.log(Level.INFO, "Guardando cita con ID: {0}", cita.getId());
        Document existente = coleccion.find(Filters.eq("_id", cita.getId())).first();
        if (existente == null) {
            Document doc = adaptador.convertirADocumento(cita);
            InsertOneResult resultado = coleccion.insertOne(doc);
            if (resultado.getInsertedId() == null) {
                throw new PersistenciaException("Error al insertar la cita");
            }
        } else {
            coleccion.replaceOne(
                    Filters.eq("_id", cita.getId()),
                    adaptador.convertirADocumento(cita));
        }
        return cita;
    }

    @Override
    public Cita buscarPorId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            throw new PersistenciaException("El ID no puede ser nulo o vacío");
        }
        LOG.log(Level.INFO, "Buscando cita en Mongo con ID: {0}", id);
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirAEntidad(doc);
    }
}
