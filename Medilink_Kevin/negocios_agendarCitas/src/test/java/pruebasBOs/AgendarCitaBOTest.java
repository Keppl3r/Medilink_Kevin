/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pruebasBOs;

import agendarCitas.AgendarCitaBO;
import agendarCitas.excepciones.NegocioAgendarException;
import adaptadores.DoctorDocumentoAdaptador;
import conexion.MongoConection;
import dto.CitaDTO;
import dto.DoctorDTO;
import objetosNegocio.Doctor;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class AgendarCitaBOTest {

    @BeforeAll
    public static void setUp() {
        MongoConection.obtenerBaseDatos().getCollection("doctores").drop();
        MongoConection.obtenerBaseDatos().getCollection("citas").drop();
        DoctorDocumentoAdaptador ad = new DoctorDocumentoAdaptador();
        var col = MongoConection.obtenerBaseDatos().getCollection("doctores");
        col.insertOne(ad.convertirADocumento(
                new Doctor(10, "Dra. Althay Valle", "Cardiologia", 500.0, true)));
        col.insertOne(ad.convertirADocumento(
                new Doctor(11, "Dr. Carlos Ruiz", "General", 300.0, true)));
        col.insertOne(ad.convertirADocumento(
                new Doctor(12, "Dr. No Disponible", "Pediatria", 400.0, false)));
    }

    @Test
    public void testObtenerEspecialistas() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        List<DoctorDTO> lista = bo.obtenerEspecialistas();
        
        assertNotNull(lista);
        assertEquals(2, lista.size()); //disponibles
    }

    @Test
    public void testVerificarDisponibilidadTrue() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        
        assertTrue(bo.verificarDisponibilidad(10));
    }

    @Test
    public void testVerificarDisponibilidadFalse() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        
        assertFalse(bo.verificarDisponibilidad(12));
    }

    @Test
    public void testVerificarDisponibilidadIdNulo() {
        AgendarCitaBO bo = new AgendarCitaBO();
        
        assertThrows(NegocioAgendarException.class,
                () -> bo.verificarDisponibilidad(null));
    }

    @Test
    public void testVerificarDisponibilidadInexistente() {
        AgendarCitaBO bo = new AgendarCitaBO();
       
        assertThrows(NegocioAgendarException.class,
                () -> bo.verificarDisponibilidad(9999));
    }

    @Test
    public void testRegistrarCitaExito() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Dolor de cabeza");
        dto.setNombrePaciente("Kevin Mendoza");
        dto.setNombreMedico("Dra. Althay Valle");
        dto.setEspecialidadMedico("Cardiologia");
        dto.setMonto(500.0);

        CitaDTO resultado = bo.registrarCita(dto);
        
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("AGENDADA", resultado.getEstado());
    }

    @Test
    public void testRegistrarCitaDatosIncompletos() {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo(""); // incompleto
        
        assertThrows(NegocioAgendarException.class,
                () -> bo.registrarCita(dto));
    }

    @Test
    public void testProcesarPagoExito() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta");
        dto.setNombrePaciente("Kevin Mendoza");
        dto.setNombreMedico("Dra. Althay Valle");
        dto.setEspecialidadMedico("Cardiologia");
        dto.setMonto(500.0);
        
        CitaDTO cita = bo.registrarCita(dto);

        assertTrue(bo.procesarPago(cita.getId(), "tarjeta-valida-123"));
    }

    @Test
    public void testProcesarPagoFondosInsuficientes()
            throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta");
        dto.setNombrePaciente("Kevin Mendoza");
        dto.setNombreMedico("Dr. Carlos Ruiz");
        dto.setEspecialidadMedico("General");
        dto.setMonto(300.0);
        
        CitaDTO cita = bo.registrarCita(dto);

        assertFalse(bo.procesarPago(cita.getId(), "FONDOS-insuficientes"));
    }

    @Test
    public void testProcesarPagoCitaInexistente() {
        AgendarCitaBO bo = new AgendarCitaBO();
        
        assertThrows(NegocioAgendarException.class,
                () -> bo.procesarPago("NO-EXISTE", "tarjeta"));
    }

    @Test
    public void testEnviarConfirmacionExito() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta");
        dto.setNombrePaciente("Kevin Mendoza");
        dto.setNombreMedico("Dra. Althay Valle");
        dto.setEspecialidadMedico("Cardiologia");
        dto.setMonto(500.0);
        
        CitaDTO cita = bo.registrarCita(dto);

        assertTrue(bo.enviarConfirmacion(cita.getId(), "kevin@mail.com"));
    }

    @Test
    public void testEnviarConfirmacionCitaInexistente() {
        AgendarCitaBO bo = new AgendarCitaBO();
       
        assertThrows(NegocioAgendarException.class,
                () -> bo.enviarConfirmacion("NO-EXISTE", "kkk@mail.com"));
    }
}
