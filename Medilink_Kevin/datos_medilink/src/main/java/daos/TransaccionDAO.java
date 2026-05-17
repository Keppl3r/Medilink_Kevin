/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.TransaccionDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import conexion.MongoConection;
import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import excepciones.PersistenciaException;
import interfaces.ITransaccionDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author keppler
 */
public class TransaccionDAO implements ITransaccionDAO {

    private static final Logger LOG = Logger.getLogger(TransaccionDAO.class.getName());

    private final MongoCollection<Document> coleccion;
    private final TransaccionDocumentoAdaptador adaptador;

    public TransaccionDAO() {
        this.coleccion = MongoConection
                .obtenerBaseDatos()
                .getCollection("transacciones");
        this.adaptador = new TransaccionDocumentoAdaptador();
    }

    @Override
    public Transaccion insertar(Transaccion transaccion) throws PersistenciaException {
        if (transaccion == null) {
            throw new PersistenciaException("La transacción no puede ser nula");
        }
        LOG.log(Level.INFO, "Insertando nueva transaccion con ID: {0}", transaccion.getId());
        Document doc = adaptador.convertirADocumento(transaccion);
        InsertOneResult resultado = coleccion.insertOne(doc);
        if (resultado.getInsertedId() == null) {
            throw new PersistenciaException("Error al insertar la transacción");
        }
        return transaccion;
    }

    @Override
    public Transaccion buscarPorId(String id) throws PersistenciaException {
        if (id == null || id.isBlank()) {
            throw new PersistenciaException("El ID no puede ser nulo o vacío");
        }
        LOG.log(Level.INFO, "Buscando transaccion en Mongo con ID: {0}", id);
        Document doc = coleccion.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return adaptador.convertirAEntidad(doc);
    }

    @Override
    public List<Transaccion> buscarPorRango(LocalDateTime inicio, LocalDateTime fin)
            throws PersistenciaException {
        if (inicio == null || fin == null) {
            throw new PersistenciaException("Las fechas no pueden ser nulas");
        }
        LOG.log(Level.INFO, "Buscando transacciones por rango de fechas");

        List<Document> docs = coleccion.find(Filters.and(
                Filters.gte("fecha", inicio),
                Filters.lte("fecha", fin)))
                .into(new ArrayList<>());

        List<Transaccion> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(adaptador.convertirAEntidad(doc));
        }
        return lista;
    }

    @Override
    public List<Transaccion> buscarPorPaciente(String nombre)
            throws PersistenciaException {
        if (nombre == null || nombre.isBlank()) {
            throw new PersistenciaException("El nombre no puede estar vacío");
        }
        LOG.log(Level.INFO, "Ejecutando busqueda con regex para paciente: {0}", nombre); 
        List<Document> docs = coleccion.find(
                Filters.regex("paciente.nombre", nombre, "i"))
                .into(new ArrayList<>());

        List<Transaccion> lista = new ArrayList<>();
        for (Document doc : docs) {
            lista.add(adaptador.convertirAEntidad(doc));
        }
        return lista;
    }

    @Override
    public Integer contarPendientes() throws PersistenciaException {
        LOG.log(Level.INFO, "Contando transacciones pendientes"); 
        long count = coleccion.countDocuments(
                Filters.eq("estado", "Pendiente"));
        return (int) count;
    }

    @Override
    public void actualizarEstado(String id, String estado)
            throws PersistenciaException {
        if (id == null) {
            throw new PersistenciaException("El ID no puede ser nulo");
        }
        LOG.log(Level.INFO, "Actualizando estado en transaccion: {0}", id); 
        coleccion.updateOne(
                Filters.eq("_id", id),
                Updates.set("estado", estado));
    }

    @Override
    public void agregarAuditoria(String idTransaccion, Auditoria auditoria)
            throws PersistenciaException {
        if (idTransaccion == null || auditoria == null) {
            throw new PersistenciaException("ID y auditoría son requeridos");
        }
        LOG.log(Level.INFO, "Insertando subdocumento de auditoria en transaccion: {0}", idTransaccion); 
        coleccion.updateOne(
                Filters.eq("_id", idTransaccion),
                Updates.combine(
                        Updates.set("estado", auditoria.getResultado()),
                        Updates.push("auditorias",
                                adaptador.auditoriaADocumento(auditoria))
                ));
    }
}
