/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebasBOs;
import adaptadores.DoctorDocumentoAdaptador;
import adaptadores.PacienteDocumentoAdaptador;
import agendarCitas.AgendarCitaBO;
import agendarCitas.excepciones.NegocioAgendarException;
import conexion.MongoConection;
import daos.DoctorDAO;
import daos.PacienteDAO;
import dto.CitaDTO;
import dto.DoctorDTO;
import excepciones.PersistenciaException;
import java.util.List;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class AgendarCitaBOTest {

   @BeforeAll
    public static void setUp() throws PersistenciaException {
        //limpia bd
        MongoConection.obtenerBaseDatos().getCollection("doctores").drop();
        MongoConection.obtenerBaseDatos().getCollection("citas").drop();
        MongoConection.obtenerBaseDatos().getCollection("pacientes").drop();

        //insert con los dao
        DoctorDAO doctorDAO = new DoctorDAO();
        doctorDAO.guardar(new Doctor(10, "Dra. Althay Valle", "Cardiologia", 500.0, true));
        doctorDAO.guardar(new Doctor(11, "Dr. Carlos Ruiz", "General", 300.0, true));
        doctorDAO.guardar(new Doctor(12, "Dr. No Disponible", "Pediatria", 400.0, false));

        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.guardar(new Paciente(1, "Kevin Mendoza", "kevin@mail.com", "6441234567"));
    }

    @Test
    public void testObtenerEspecialistas() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        List<DoctorDTO> lista = bo.obtenerEspecialistas();

        assertNotNull(lista);
        assertEquals(2, lista.size()); 
    }

    @Test
    public void testDoctorDisponible() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();
        assertTrue(bo.verificarDisponibilidad(10));
    }

    @Test
    public void testDoctorNoDisponible() throws NegocioAgendarException {
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
    public void testRegistrarCitaExito() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();

        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Dolor de cabeza fuerte");
        dto.setIdMedico(10);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO resultado = bo.registrarCita(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("PENDIENTE_PAGO", resultado.getEstado());
        assertEquals(500.0, resultado.getMonto());
    }

    @Test
    public void testRegistrarCitaDatosIncompletos() {
        AgendarCitaBO bo = new AgendarCitaBO();
        CitaDTO dto = new CitaDTO();
        dto.setMotivo(""); 

        assertThrows(NegocioAgendarException.class,
                () -> bo.registrarCita(dto));
    }

    @Test
    public void testProcesarPagoExito() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();

        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta de prueba");
        dto.setIdMedico(10);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO cita = bo.registrarCita(dto);

        String resultado = bo.procesarPago(cita.getId(), "tarjeta-valida-123");
        assertEquals("EXITOSO", resultado);
    }

    @Test
    public void testProcesarPagoFondosInsuficientes() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();

        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta de prueba");
        dto.setIdMedico(11);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO cita = bo.registrarCita(dto);

        String resultado = bo.procesarPago(cita.getId(), "FONDOS-insuficientes");
        assertEquals("FONDOS_INSUFICIENTES", resultado);
    }

    @Test
    public void testProcesarPagoDatosErroneos() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();

        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta de prueba");
        dto.setIdMedico(10);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO cita = bo.registrarCita(dto);

        String resultado = bo.procesarPago(cita.getId(), "");
        assertEquals("DATOS_ERRONEOS", resultado);
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
        dto.setMotivo("Consulta de prueba");
        dto.setIdMedico(10);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO cita = bo.registrarCita(dto);
        bo.procesarPago(cita.getId(), "tarjeta-valida"); 

        boolean enviado = bo.enviarConfirmacion(cita.getId(), "kevin@mail.com");
        assertTrue(enviado);
    }

    @Test
    public void testEnviarConfirmacionCitaNoPagada() throws NegocioAgendarException {
        AgendarCitaBO bo = new AgendarCitaBO();

        CitaDTO dto = new CitaDTO();
        dto.setMotivo("Consulta de prueba");
        dto.setIdMedico(10);
        dto.setIdPaciente(1);
        dto.setNombrePaciente("Kevin Mendoza");

        CitaDTO cita = bo.registrarCita(dto);

        assertThrows(NegocioAgendarException.class,
                () -> bo.enviarConfirmacion(cita.getId(), "kevin@mail.com"));
    }
}