/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pruebasDAOS;

import daos.PacienteDAO;
import adaptadores.PacienteDocumentoAdaptador;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import objetosNegocio.Paciente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class PacienteDAOTest {

    @BeforeAll
    public static void prepararDatos() {
        MongoConection.obtenerBaseDatos().getCollection("pacientes").drop();
        PacienteDocumentoAdaptador adaptador = new PacienteDocumentoAdaptador();
        MongoConection.obtenerBaseDatos().getCollection("pacientes")
                .insertOne(adaptador.convertirADocumento(
                        new Paciente(1, "Kevin Mendoza",
                                "kevin@gmail.com", "6441234567")));
    }

    @Test
    public void testBuscarPorIdExito() throws PersistenciaException {
        PacienteDAO dao = new PacienteDAO();
        Paciente p = dao.buscarPorId(1);
        
        assertNotNull(p);
        assertEquals("Kevin Mendoza", p.getNombre());
        assertEquals("kevin@gmail.com", p.getCorreo());
    }

    @Test
    public void testBuscarPorIdInexistente() throws PersistenciaException {
        PacienteDAO dao = new PacienteDAO();
        
        assertNull(dao.buscarPorId(9999));
    }

    @Test
    public void testBuscarPorIdNuloLanzaExcepcion() {
        PacienteDAO dao = new PacienteDAO();
       
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId(null));
    }
}
