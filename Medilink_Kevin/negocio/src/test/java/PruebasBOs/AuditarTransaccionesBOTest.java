package PruebasBOs;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import BOs.AuditarTransaccionesBO;
import conexion.MongoConection;
import daos.TransaccionDAO;
import dtos.DetalleTransaccionDTO;
import dtos.FiltrosBusquedaDTO;
import dtos.PagoDTO;
import dtos.TransaccionDTO;
import entidades.Transaccion;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author keppler
 */
public class AuditarTransaccionesBOTest {
private static int contadorId = 1;

    @BeforeAll
    public static void setUp() throws PersistenciaException {
        MongoConection.obtenerBaseDatos().getCollection("transacciones").drop();

        TransaccionDAO dao = new TransaccionDAO();
        dao.insertar(crearTransaccion("AUDITAR-1", "Pendiente", 500.0, 500.0, "Exitoso"));
        dao.insertar(crearTransaccion("AUDITAR-2", "Pendiente", 300.0, 300.0, "Exitoso"));
        dao.insertar(crearTransaccion("AUDITAR-3", "Auditada", 700.0, 700.0, "Exitoso"));
        dao.insertar(crearTransaccion("AUDITAR-4", "Pendiente", 400.0, 500.0, "Exitoso")); // inconsistente
        dao.insertar(crearTransaccion("AUDITAR-5", "Pendiente", 600.0, 600.0, "Rechazado")); // pago fallido
    }

    //utilería
    private static Transaccion crearTransaccion(String id, String estado,
            Double montoRecibido, Double montoEsperado, String mensajeEstado) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(id);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado(estado);
        transaccion.setIdPaciente(1);
        transaccion.setNombrePaciente("Kevin Mendoza");
        transaccion.setIdMedico(10);
        transaccion.setNombreMedico("Dra. Althay Valle");
        transaccion.setTipoConsulta("Cardiologia"); 
        transaccion.setMontoEsperado(montoEsperado);
        transaccion.setReferenciaStripe("referencia_" + id);
        transaccion.setMontoRecibido(montoRecibido);
        transaccion.setMensajeEstado(mensajeEstado);
        return transaccion;
    }

    @Test
    public void testContarPendientes() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        Integer pendientes = bo.contarPendientes();
        assertNotNull(pendientes);
        assertTrue(pendientes >= 3);
    }

    @Test
    public void testBuscarPorPeriodo() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
        filtros.setInicio(LocalDateTime.now().minusDays(1));
        filtros.setFin(LocalDateTime.now().plusDays(1));

        List<TransaccionDTO> lista = bo.buscarPorPeriodo(filtros);

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    public void testBuscarPorPeriodoFechasInvertidas() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
        filtros.setInicio(LocalDateTime.now().plusDays(1));
        filtros.setFin(LocalDateTime.now().minusDays(1));

        assertThrows(NegocioException.class, () -> bo.buscarPorPeriodo(filtros));
    }

    @Test
    public void testBuscarPorPaciente() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
        filtros.setNombrePaciente("Kevin");

        List<TransaccionDTO> lista = bo.buscarPorPaciente(filtros);

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
        assertEquals("Kevin Mendoza", lista.get(0).getNombrePaciente());
    }

    @Test
    public void testBuscarPorPacienteVacio() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        FiltrosBusquedaDTO filtros = new FiltrosBusquedaDTO();
        filtros.setNombrePaciente("");

        assertThrows(NegocioException.class, () -> bo.buscarPorPaciente(filtros));
    }

    @Test
    public void testObtenerDetalle() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        DetalleTransaccionDTO detalle = bo.obtenerDetalle("AUDITAR-1");

        assertNotNull(detalle);
        assertEquals("AUDITAR-1", detalle.getId());
        assertEquals("Kevin Mendoza", detalle.getNombrePaciente());
        assertEquals("Cardiologia", detalle.getTipoConsulta());
        assertEquals(500.0, detalle.getMontoRecibido());
    }

    @Test
    public void testObtenerDetalleInexistente() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        assertThrows(NegocioException.class, () -> bo.obtenerDetalle("Tangamandapio"));
    }

    @Test
    public void testAuditarTransaccionExito() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        bo.auditarTransaccion("AUDITAR-1");

        DetalleTransaccionDTO detalle = bo.obtenerDetalle("AUDITAR-1");
        assertEquals("Auditada", detalle.getEstado());
    }

    @Test
    public void testAuditarTransaccionInconsistente() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        // AUDITAR-4 es inconsistente
        assertThrows(NegocioException.class, () -> bo.auditarTransaccion("AUDITAR-4"));
    }

    @Test
    public void testAuditarTransaccionPagoRechazado() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        // AUDITAR-5 tiene pago rechazado
        assertThrows(NegocioException.class, () -> bo.auditarTransaccion("AUDITAR-5"));
    }

    @Test
    public void testMarcarPendiente() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        bo.marcarPendiente("AUDITAR-3"); // ya estaba auditada

        DetalleTransaccionDTO detalle = bo.obtenerDetalle("AUDITAR-3");
        assertEquals("Pendiente", detalle.getEstado());
    }

    @Test
    public void testObtenerPago() throws NegocioException {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        PagoDTO pago = bo.obtenerPago("AUDITAR-2");

        assertNotNull(pago);
        assertEquals("referencia_AUDITAR-2", pago.getReferenciaStripe());
        assertEquals(300.0, pago.getMontoRecibido());
        assertEquals("Exitoso", pago.getMensajeEstado());
    }

    @Test
    public void testObtenerPagoInexistente() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        assertThrows(NegocioException.class, () -> bo.obtenerPago("Tangamandapiopio"));
    }

    @Test
    public void testObtenerDetalleIdNulo() {
        AuditarTransaccionesBO bo = new AuditarTransaccionesBO();
        assertThrows(NegocioException.class, () -> bo.obtenerDetalle(null));
    }
}
