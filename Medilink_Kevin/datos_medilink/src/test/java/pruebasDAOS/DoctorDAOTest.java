/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pruebasDAOS;

import daos.DoctorDAO;
import adaptadores.DoctorDocumentoAdaptador;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import objetosNegocio.Doctor;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class DoctorDAOTest {
  @BeforeAll
    public static void prepararDatos() {
        MongoConection.obtenerBaseDatos().getCollection("doctores").drop();
        DoctorDocumentoAdaptador ad = new DoctorDocumentoAdaptador();
        var coleccion = MongoConection.obtenerBaseDatos().getCollection("doctores");
        coleccion.insertOne(ad.convertirADocumento(
                new Doctor(10, "Dra. Althay Valle", "Cardiologia", 500.0, true)));
        coleccion.insertOne(ad.convertirADocumento(
                new Doctor(11, "Dr. Chespirito", "General", 300.0, true)));
        coleccion.insertOne(ad.convertirADocumento(
                new Doctor(12, "Dr. No Disponible", "Pediatria", 400.0, false)));
    }

    @Test
    public void testBuscarDisponibles() throws PersistenciaException {
        DoctorDAO dao = new DoctorDAO();
        
        List<Doctor> lista = dao.buscarDisponibles();
        
        assertNotNull(lista);
        assertEquals(2, lista.size()); // solo 2 disponibles
    }

    @Test
    public void testBuscarPorIdExito() throws PersistenciaException {
        DoctorDAO dao = new DoctorDAO();
      
        Doctor d = dao.buscarPorId(10);
       
        assertNotNull(d);
        assertEquals("Dra. Althay Valle", d.getNombre());
        assertTrue(d.getDisponible());
    }

    @Test
    public void testBuscarPorIdInexistente() throws PersistenciaException {
        DoctorDAO dao = new DoctorDAO();
        
        assertNull(dao.buscarPorId(9999));
    }

    @Test
    public void testBuscarPorIdNuloLanzaExcepcion() {
        DoctorDAO dao = new DoctorDAO();
       
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId(null));
    }
}