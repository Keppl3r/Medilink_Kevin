/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.CitaDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConection;
import entidadesMongo.CitaMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.ICitaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Cita;

/**
 *
 * @author keppler
 */
public class CitaDAO implements ICitaDAO {

    private static final Logger LOG = Logger.getLogger(CitaDAO.class.getName());

    private final MongoCollection<CitaMongoEntidad> coleccion;
    private final CitaDocumentoAdaptador adaptador;

    public CitaDAO() {
        this.coleccion = MongoConection.obtenerColeccionCitas();
        this.adaptador = new CitaDocumentoAdaptador();
    }

    @Override
    public Cita guardar(Cita cita) throws PersistenciaException {
        if (cita == null) {
            throw new PersistenciaException("La cita no puede ser nula");
        }
        LOG.log(Level.INFO, "Guardando cita con ID: {0}", cita.getId());
        CitaMongoEntidad existente = coleccion.find(
                Filters.eq("folio", cita.getId())).first();
        if (existente == null) {
            coleccion.insertOne(adaptador.convertirAMongo(cita));
        } else {
            coleccion.replaceOne(
                    Filters.eq("folio", cita.getId()),
                    adaptador.convertirAMongo(cita));
        }
        return cita;
    }

    @Override
    public Cita buscarPorId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            throw new PersistenciaException("El ID no puede ser nulo o vacío");
        }
        LOG.log(Level.INFO, "Buscando cita en Mongo con ID: {0}", id);
        CitaMongoEntidad doc = coleccion.find(Filters.eq("folio", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirADominio(doc);
    }

    @Override
    public boolean eliminar(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            throw new PersistenciaException("El ID no puede ser nulo o vacío");
        }
        LOG.log(Level.INFO, "Eliminando cita con ID: {0}", id);
        return coleccion.deleteOne(Filters.eq("folio", id))
                .getDeletedCount() > 0;
    }
}
