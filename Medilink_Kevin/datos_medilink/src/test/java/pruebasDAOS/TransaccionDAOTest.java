package pruebasDAOS;

import daos.TransaccionDAO;
import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import conexion.MongoConection;

/**
 * @author keppler
 */
public class TransaccionDAOTest {

    // contador de ID para no tener que usar currentTimeMillis o algo asi muy largo, porque aqui no encuentro el drop and create 
    // y da error con los id repetidos en cada compilación
    private static int contadorId = 1;

    @BeforeAll
    public static void limpiarBaseDeDatos() {
        // drop and create para mongo, porque da error en cada compilación por los ids
        MongoConection.obtenerBaseDatos().getCollection("transacciones").drop();
    }
    
    private String generarIdLimpio() {
        return "TRANSACCION-" + (contadorId++);
    }

    private Transaccion crearTransaccionPrueba(String id) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(id);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("Pendiente");
        transaccion.setIdPaciente(1);
        transaccion.setNombrePaciente("Kevin Mendoza");
        transaccion.setIdMedico(10);
        transaccion.setNombreMedico("Dra. Nathalie Mendoza");
        transaccion.setTipoConsulta("Consulta general");
        transaccion.setMontoEsperado(500.0);
        transaccion.setReferenciaStripe("referencia1");
        transaccion.setMontoRecibido(500.0);
        transaccion.setMensajeEstado("Exitoso");
        return transaccion;
    }

    @Test
    public void testInsertarExito() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        Transaccion transaccion = crearTransaccionPrueba(id);

        Transaccion resultado = dao.insertar(transaccion);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
    }

    @Test
    public void testInsertarNuloLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();
        assertThrows(PersistenciaException.class, () -> dao.insertar(null));
    }

    @Test
    public void testBuscarPorIdExito() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        Transaccion resultado = dao.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Kevin Mendoza", resultado.getNombrePaciente());
        assertEquals("Dra. Nathalie Mendoza", resultado.getNombreMedico());
        assertEquals(500.0, resultado.getMontoRecibido());
    }

    @Test
    public void testBuscarPorIdInexistente() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        assertNull(dao.buscarPorId("NO-EXISTE-XYZ"));
    }

    @Test
    public void testBuscarPorIdNuloLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId(null));
    }

    @Test
    public void testContarPendientes() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        Integer pendientes = dao.contarPendientes();

        assertNotNull(pendientes);
        assertTrue(pendientes >= 1);
    }

    @Test
    public void testActualizarEstado() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        dao.actualizarEstado(id, "Auditada");

        Transaccion actualizada = dao.buscarPorId(id);
        assertEquals("Auditada", actualizada.getEstado());
    }

    @Test
    public void testAgregarAuditoria() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        Auditoria auditoria = new Auditoria(1, LocalDateTime.now(), "Auditada", 1, "Adrian");
        dao.agregarAuditoria(id, auditoria);

        Transaccion resultado = dao.buscarPorId(id);
        assertNotNull(resultado.getAuditorias());
        assertFalse(resultado.getAuditorias().isEmpty());
        assertEquals("Auditada", resultado.getAuditorias().get(0).getResultado());
        assertEquals("Auditada", resultado.getEstado());
    }

    @Test
    public void testBuscarPorPaciente() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        List<Transaccion> lista = dao.buscarPorPaciente("Kevin");

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    public void testBuscarPorRango() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        dao.insertar(crearTransaccionPrueba(id));

        LocalDateTime ayer = LocalDateTime.now().minusDays(1);
        LocalDateTime manana = LocalDateTime.now().plusDays(1);

        List<Transaccion> lista = dao.buscarPorRango(ayer, manana);

        assertNotNull(lista);
        assertFalse(lista.isEmpty());
    }

    @Test
    public void testBuscarPorPacienteVacioLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();
        assertThrows(PersistenciaException.class, () -> dao.buscarPorPaciente(""));
    }

    @Test
    public void testBuscarPorRangoNuloLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();

        // fechaInicio nula
        assertThrows(PersistenciaException.class,
                () -> dao.buscarPorRango(null, LocalDateTime.now()));

        // fechaFin nula
        assertThrows(PersistenciaException.class,
                () -> dao.buscarPorRango(LocalDateTime.now(), null));
    }

    @Test
    public void testBuscarPorRangoSinResultados() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();

        // buscar en año que ni al caso
        LocalDateTime inicio = LocalDateTime.of(1901, 1, 1, 0, 0);
        LocalDateTime fin = LocalDateTime.of(1950, 12, 31, 23, 59);

        List<Transaccion> lista = dao.buscarPorRango(inicio, fin);

        // lista debe venir vacía, pero sin excepción
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
    
    @Test
    public void testBuscarPorPacienteMayusculasMinusculas() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        String id = generarIdLimpio();
        Transaccion transaccion = crearTransaccionPrueba(id);
        transaccion.setNombrePaciente("kEvIn MeNdOzA"); // Nombre con mayúsculas y minúsculas irregulares
        dao.insertar(transaccion);

        // todo en minúsculas, debería encontrarlo
        List<Transaccion> lista = dao.buscarPorPaciente("kevin mendoza");

        assertFalse(lista.isEmpty());
    }
    
    @Test
    public void testBuscarPorPacienteInexistente() throws PersistenciaException {
        TransaccionDAO dao = new TransaccionDAO();
        List<Transaccion> lista = dao.buscarPorPaciente("Manganito Tangamandapio Parangaricutimiricuaro");
        
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
    
    @Test
    public void testActualizarEstadoIdNuloLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();
        assertThrows(PersistenciaException.class,
                () -> dao.actualizarEstado(null, "Auditada"));
    }
    
    @Test
    public void testAgregarAuditoriaIDNulosLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();

        assertThrows(PersistenciaException.class,
                () -> dao.agregarAuditoria(null, new Auditoria()));

        assertThrows(PersistenciaException.class,
                () -> dao.agregarAuditoria("TRANSACCION-6969", null));
    }
    
    @Test
    public void testBuscarPorIdEnBlancoLanzaExcepcion() {
        TransaccionDAO dao = new TransaccionDAO();
        // id solo con espacios
        assertThrows(PersistenciaException.class, () -> dao.buscarPorId("   "));
    }
   
}
