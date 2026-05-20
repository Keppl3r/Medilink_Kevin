package pruebasDAOS;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import conexion.MongoConection;
import daos.ReporteTransaccionDAO;
import daos.TransaccionDAO;
import excepciones.PersistenciaException;
import objetosNegocio.Transaccion;
import java.time.LocalDateTime;
import java.util.List;
import objetosNegocio.ReporteEstado;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author keppler
 */
public class ReporteTransaccionDAOTest {
 @BeforeAll
    public static void setUp() throws PersistenciaException {
        MongoConection.obtenerBaseDatos()
                .getCollection("transacciones").drop();

        TransaccionDAO dao = new TransaccionDAO();
        dao.insertar(crear("R-1", "Pendiente", 500.0));
        dao.insertar(crear("R-2", "Pendiente", 300.0));
        dao.insertar(crear("R-3", "Auditada", 700.0));
    }

    private static Transaccion crear(String id, String estado, double monto) {
        Transaccion transaccion = new Transaccion();
        transaccion.setId(id);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado(estado);
        transaccion.setIdPaciente(1);
        transaccion.setNombrePaciente("Kevin Mendoza");
        transaccion.setIdMedico(10);
        transaccion.setNombreMedico("Dra. Nathalie Mendoza");
        transaccion.setTipoConsulta("Consulta general");
        transaccion.setMontoEsperado(monto);
        transaccion.setReferenciaStripe("ref-" + id);
        transaccion.setMontoRecibido(monto);
        transaccion.setMensajeEstado("Exitoso");
        return transaccion;
    }

    @Test
    public void testReportePorEstado() throws PersistenciaException {
        ReporteTransaccionDAO dao = new ReporteTransaccionDAO();
        List<ReporteEstado> reporte = dao.reportePorEstado();

        assertNotNull(reporte);
        // pendiente y auditada
        assertEquals(2, reporte.size());
    }

    @Test
    public void testPromedioPendiente() throws PersistenciaException {
        ReporteTransaccionDAO dao = new ReporteTransaccionDAO();
        List<ReporteEstado> reporte = dao.reportePorEstado();

        ReporteEstado pendiente = null;
        for (ReporteEstado r : reporte) {
            if ("Pendiente".equals(r.getEstado())) {
                pendiente = r;
            }
        }
        assertNotNull(pendiente);
        assertEquals(2, pendiente.getTotalTransacciones());
        // promedio= 400
        assertEquals(400.0, pendiente.getMontoPromedio());
    }
}
