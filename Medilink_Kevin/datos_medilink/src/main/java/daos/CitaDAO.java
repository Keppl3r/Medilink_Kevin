/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import adaptadores.CitaDocumentoAdaptador;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import interfaces.ICitaDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Cita buscarPorId(String id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
