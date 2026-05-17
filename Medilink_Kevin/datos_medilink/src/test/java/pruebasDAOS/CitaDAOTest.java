/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pruebasDAOS;

import daos.CitaDAO;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import objetosNegocio.Cita;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class CitaDAOTest {

    private static int contadorId = 1;

    @BeforeAll
    public static void limpiarBaseDeDatos() {
        MongoConection.obtenerBaseDatos().getCollection("citas").drop();
    }

    private String generarId() {
        return "CITA-" + (contadorId++);
    }

    private Cita crearCitaPrueba(String id) {
        Cita cita = new Cita();
        cita.setId(id);
        cita.setFecha(LocalDateTime.now());
        cita.setHora("10:00");
        cita.setUbicacion("Consultorio 3");
        cita.setMotivo("Dolor de cabeza");
        cita.setEstado("AGENDADA");
        cita.setMonto(500.0);
        Paciente p = new Paciente();
        p.setId(1);
        p.setNombre("Kevin Mendoza");
        cita.setPaciente(p);
        Doctor d = new Doctor();
        d.setId(10);
        d.setNombre("Dra. Althay Valle");
        d.setEspecialidad("Cardiologia");
        cita.setMedico(d);
        return cita;
    }

    @Test
    public void testGuardarExito() throws PersistenciaException {
        CitaDAO dao = new CitaDAO();
        String id = generarId();
       
        Cita resultado = dao.guardar(crearCitaPrueba(id));
        
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    public void testGuardarNuloLanzaExcepcion() {
        CitaDAO dao = new CitaDAO();
       
        assertThrows(PersistenciaException.class, () -> dao.guardar(null));
    }

    @Test
    public void testBuscarPorIdExito() throws PersistenciaException {
        CitaDAO dao = new CitaDAO();
        String id = generarId();
       
        dao.guardar(crearCitaPrueba(id));
        Cita resultado = dao.buscarPorId(id);
        
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Kevin Mendoza", resultado.getPaciente().getNombre());
        assertEquals("Dra. Althay Valle", resultado.getMedico().getNombre());
    }

    @Test
    public void testBuscarPorIdInexistente() throws PersistenciaException {
        CitaDAO dao = new CitaDAO();
       
        assertNull(dao.buscarPorId("sabe-wey"));
    }

    @Test
    public void testBuscarPorIdNuloLanzaExcepcion() {
        CitaDAO dao = new CitaDAO();
        
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId(null));
    }

    @Test
    public void testBuscarPorIdEnBlancoLanzaExcepcion() {
        CitaDAO dao = new CitaDAO();
        
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId("   "));
    }

    @Test
    public void testGuardarActualizaSiExiste() throws PersistenciaException {
        CitaDAO dao = new CitaDAO();
        String id = generarId();
        Cita cita = crearCitaPrueba(id);
        
        dao.guardar(cita);
        cita.setEstado("PAGADA");
        dao.guardar(cita);
        Cita actualizada = dao.buscarPorId(id);
        
        assertEquals("PAGADA", actualizada.getEstado());
    }
}
