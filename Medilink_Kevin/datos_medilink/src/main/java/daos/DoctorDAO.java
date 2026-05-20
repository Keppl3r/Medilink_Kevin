/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.DoctorDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConection;
import entidadesMongo.DoctorMongoEntidad;
import excepciones.PersistenciaException;
import interfaces.IDoctorDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Doctor;

/**
 *
 * @author keppler
 */
public class DoctorDAO implements IDoctorDAO {

    private static final Logger LOG = Logger.getLogger(DoctorDAO.class.getName());

    private final MongoCollection<DoctorMongoEntidad> coleccion;
    private final DoctorDocumentoAdaptador adaptador;

    public DoctorDAO() {
        this.coleccion = MongoConection.obtenerColeccionDoctores();
        this.adaptador = new DoctorDocumentoAdaptador();
    }

    public Doctor guardar(Doctor doctor) throws PersistenciaException {
        if (doctor == null) {
            throw new PersistenciaException("El doctor no puede ser nulo");
        }
        LOG.log(Level.INFO, "Guardando doctor con ID: {0}", doctor.getId());
        coleccion.insertOne(adaptador.convertirAMongo(doctor));
        return doctor;
    }

    @Override
    public List<Doctor> buscarDisponibles() throws PersistenciaException {
        LOG.log(Level.INFO, "Buscando doctores disponibles");
        List<DoctorMongoEntidad> docs = coleccion.find(
                Filters.eq("disponible", true)).into(new ArrayList<>());
        List<Doctor> lista = new ArrayList<>();
        for (DoctorMongoEntidad doc : docs) {
            lista.add(adaptador.convertirADominio(doc));
        }
        return lista;
    }

    @Override
    public Doctor buscarPorId(Integer id) throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El ID no puede ser nulo");
        }
        LOG.log(Level.INFO, "Buscando doctor con ID: {0}", id);
        DoctorMongoEntidad doc = coleccion.find(
                Filters.eq("idNegocio", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirADominio(doc);
    }
}