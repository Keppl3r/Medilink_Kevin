/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package insertDatos;

import conexion.MongoConection;
import daos.TransaccionDAO;
import java.time.LocalDateTime;
import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;

/**
 *
 * @author keppler
 */
public class insertDatosAuditarTransacciones {

    public static void main(String[] args) throws Exception {
        MongoConection.obtenerBaseDatos()
                .getCollection("transacciones").drop();

        TransaccionDAO dao = new TransaccionDAO();

        dao.insertar(t("AAA-2026-001", "2026-03-10", "Pendiente", 1,
                "Kevin Adrian Mendoza Moreno", 10, "Raul Almada García",
                "Consulta general", 999.0, "eeeee1", 999.0, "Exitoso"));
        dao.insertar(t("AAA-2026-002", "2026-03-11", "Pendiente", 2,
                "Kevin García Razo", 11, "Francisco Beltrán",
                "Consulta especialidad", 1500.0, "eeeee2", 1500.0, "Exitoso"));
        dao.insertar(t("AAA-2026-003", "2026-03-12", "Pendiente", 3,
                "María López Hernández", 12, "Karla Villegas",
                "Consulta general", 999.0, "eeeee3", 999.0, "Exitoso"));
        // inconsistente: esperado 750 pero recibido 500
        dao.insertar(t("AAA-2026-004", "2026-04-01", "Pendiente", 4,
                "Juan Carlos Pérez Soto", 10, "Raul Almada García",
                "Revisión dental", 750.0, "eeeee4", 500.0, "Exitoso"));
        dao.insertar(t("AAA-2026-005", "2026-04-05", "Pendiente", 5,
                "Ana Sofía Ruiz Castrejón", 13, "Nathalie Mendoza",
                "Consulta pediátrica", 1200.0, "eeeee5", 1200.0, "Rechazado"));
        // factura no emitida: sin referencia stripe
        dao.insertar(tSinPago("AAA-2026-007", "2026-04-15", "Pendiente", 6,
                "Roberto Guzman Jiménez", 12, "Karla Villegas",
                "Consulta general", 999.0));
        dao.insertar(t("AAA-2026-008", "2026-05-01", "Pendiente", 7,
                "Diana Torres Vega", 13, "Nathalie Mendoza",
                "Consulta especialidad", 1500.0, "eeeee8", 1500.0, "Exitoso"));
        dao.insertar(t("AAA-2026-010", "2026-05-14", "Pendiente", 8,
                "Carlos Alberto Ruiz", 11, "Francisco Beltrán",
                "Consulta general", 999.0, "eeeee10", 999.0, "Exitoso"));
        dao.insertar(tAuditada("AAA-2026-006", "2026-04-10", 1,
                "Kevin Adrian Mendoza Moreno", 11, "Francisco Beltrán",
                "Consulta general", 999.0, "eeeee6", 999.0));
        dao.insertar(tAuditada("AAA-2026-009", "2026-05-10", 2,
                "Kevin García Razo", 10, "Raul Almada García",
                "Revisión dental", 750.0, "eeeee9", 750.0));

        System.out.println("Datos insertados correctamente");
        System.exit(0);
    }

    private static LocalDateTime fecha(String f) {
        String[] p = f.split("-");
        return LocalDateTime.of(Integer.parseInt(p[0]),
                Integer.parseInt(p[1]), Integer.parseInt(p[2]), 0, 0);
    }

    private static Transaccion t(String id, String f, String estado,
            int idPac, String nomPac, int idMed, String nomMed,
            String tipo, double esperado, String refStripe,
            double recibido, String msgEstado) {
        Transaccion tr = new Transaccion();
        tr.setId(id);
        tr.setFecha(fecha(f));
        tr.setEstado(estado);
        tr.setIdPaciente(idPac);
        tr.setNombrePaciente(nomPac);
        tr.setIdMedico(idMed);
        tr.setNombreMedico(nomMed);
        tr.setTipoConsulta(tipo);
        tr.setMontoEsperado(esperado);
        tr.setReferenciaStripe(refStripe);
        tr.setMontoRecibido(recibido);
        tr.setMensajeEstado(msgEstado);
        return tr;
    }

    private static Transaccion tSinPago(String id, String f, String estado,
            int idPac, String nomPac, int idMed, String nomMed,
            String tipo, double esperado) {
        Transaccion tr = t(id, f, estado, idPac, nomPac, idMed, nomMed,
                tipo, esperado, null, 0.0, null);
        return tr;
    }

    private static Transaccion tAuditada(String id, String f,
            int idPac, String nomPac, int idMed, String nomMed,
            String tipo, double esperado, String refStripe, double recibido) {
        Transaccion tr = t(id, f, "Auditada", idPac, nomPac, idMed, nomMed,
                tipo, esperado, refStripe, recibido, "Exitoso");
        Auditoria a = new Auditoria(1, fecha(f), "Auditada", 1, "Administrador");
        tr.getAuditorias().add(a);
        return tr;
    }
}