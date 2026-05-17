/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package insertDatos;

import com.mongodb.client.MongoDatabase;
import conexion.MongoConection;
import org.bson.Document;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author keppler
 */
public class insertDatos {

    public static void main(String[] args) {
        MongoDatabase db = MongoConection.obtenerBaseDatos();

        // Limpiar
        db.getCollection("transacciones").drop();
        db.getCollection("pacientes").drop();
        db.getCollection("medicos").drop();
        db.getCollection("administradores").drop();

        //transacciones
        db.getCollection("transacciones").insertMany(Arrays.asList(
                transaccion("AAA-2026-001", "2026-03-10", "Pendiente", 1, "Kevin Adrian Mendoza Moreno", 10, "Raul Almada García", "Consulta general", 999.0, "eeeee1", 999.0, "Exitoso"),
                transaccion("AAA-2026-002", "2026-03-11", "Pendiente", 2, "Kevin García Razo", 11, "Francisco Beltrán", "Consulta especialidad", 1500.0, "eeeee2", 1500.0, "Exitoso"),
                transaccion("AAA-2026-003", "2026-03-12", "Pendiente", 3, "María López Hernández", 12, "Karla Villegas", "Consulta general", 999.0, "eeeee3", 999.0, "Exitoso"),
                transaccion("AAA-2026-004", "2026-04-01", "Pendiente", 4, "Juan Carlos Pérez Soto", 10, "Raul Almada García", "Revisión dental", 750.0, "eeeee4", 500.0, "Exitoso"), //este es inconsistente
                transaccion("AAA-2026-005", "2026-04-05", "Pendiente", 5, "Ana Sofía Ruiz Castrejón", 13, "Nathalie Mendoza", "Consulta pediátrica", 1200.0, "eeeee5", 1200.0, "Rechazado"), 
                transaccionSinPago("AAA-2026-007", "2026-04-15", "Pendiente", 6, "Roberto Guzman Jiménez", 12, "Karla Villegas", "Consulta general", 999.0), // este es factura no emitida
                transaccion("AAA-2026-008", "2026-05-01", "Pendiente", 7, "Diana Torres Vega", 13, "Nathalie Mendoza", "Consulta especialidad", 1500.0, "eeeee8", 1500.0, "Exitoso"),
                transaccion("AAA-2026-010", "2026-05-14", "Pendiente", 8, "Carlos Alberto Ruiz", 11, "Francisco Beltrán", "Consulta general", 999.0, "eeeee10", 999.0, "Exitoso"),
                transaccionAuditada("AAA-2026-006", "2026-04-10", 1, "Kevin Adrian Mendoza Moreno", 11, "Francisco Beltrán", "Consulta general", 999.0, "eeeee6", 999.0),
                transaccionAuditada("AAA-2026-009", "2026-05-10", 2, "Kevin García Razo", 10, "Raul Almada García", "Revisión dental", 750.0, "eeeee9", 750.0)
        ));

        // pacientes
        db.getCollection("pacientes").insertMany(Arrays.asList(
                new Document("_id", 1).append("nombre", "Kevin Adrian Mendoza Moreno").append("correo", "kevin@mail.com").append("telefono", "6441234567"),
                new Document("_id", 2).append("nombre", "Kevin García Razo").append("correo", "kgarcia@mail.com").append("telefono", "6449876543"),
                new Document("_id", 3).append("nombre", "María López Hernández").append("correo", "maria@mail.com").append("telefono", "6441112233"),
                new Document("_id", 4).append("nombre", "Juan Carlos Pérez Soto").append("correo", "juanc@mail.com").append("telefono", "6444455566"),
                new Document("_id", 5).append("nombre", "Ana Sofía Ruiz Castrejón").append("correo", "ana@mail.com").append("telefono", "6447788990"),
                new Document("_id", 6).append("nombre", "Roberto Guzman Jiménez").append("correo", "roberto@mail.com").append("telefono", "6443344556"),
                new Document("_id", 7).append("nombre", "Diana Torres Vega").append("correo", "diana@mail.com").append("telefono", "6442233445"),
                new Document("_id", 8).append("nombre", "Carlos Alberto Ruiz").append("correo", "carlos@mail.com").append("telefono", "6446677889")
        ));

        //medicos
        db.getCollection("medicos").insertMany(Arrays.asList(
                new Document("_id", 10).append("nombre", "Raul Almada García").append("especialidad", "Medicina general").append("costo_consulta", 999.0),
                new Document("_id", 11).append("nombre", "Francisco Beltrán").append("especialidad", "Odontología").append("costo_consulta", 750.0),
                new Document("_id", 12).append("nombre", "Karla Villegas").append("especialidad", "Medicina general").append("costo_consulta", 999.0),
                new Document("_id", 13).append("nombre", "Nathalie Mendoza").append("especialidad", "Pediatría").append("costo_consulta", 1200.0)
        ));

        // admin
        db.getCollection("administradores").insertMany(Arrays.asList(
                new Document("_id", 1).append("nombre", "Administrador").append("correo", "admin@medilink.com")
        ));

        System.out.println("Datos insertados");
        System.out.println("Transacciones: " + db.getCollection("transacciones").countDocuments());
        System.out.println("Pacientes: " + db.getCollection("pacientes").countDocuments());
        System.out.println("Medicos: " + db.getCollection("medicos").countDocuments());
        System.out.println("Administradores: " + db.getCollection("administradores").countDocuments());
       
    }

    @SuppressWarnings("deprecation")
    private static Date fecha(String f) {
        String[] p = f.split("-");
        return new Date(Integer.parseInt(p[0]) - 1900, Integer.parseInt(p[1]) - 1, Integer.parseInt(p[2]));
    }

    private static Document transaccion(String id, String f, String estado,
            int idPac, String nomPac, int idMed, String nomMed,
            String tipo, double esperado, String refStripe, double recibido, String msgEstado) {
        return new Document("_id", id)
                .append("fecha", fecha(f))
                .append("estado", estado)
                .append("paciente", new Document("id", idPac).append("nombre", nomPac))
                .append("medico", new Document("id", idMed).append("nombre", nomMed))
                .append("servicio", new Document("tipo_consulta", tipo).append("monto_esperado", esperado))
                .append("pago", new Document("referencia_stripe", refStripe).append("monto_recibido", recibido).append("mensaje_estado", msgEstado))
                .append("auditorias", new ArrayList<>());
    }

    private static Document transaccionSinPago(String id, String f, String estado,
            int idPac, String nomPac, int idMed, String nomMed, String tipo, double esperado) {
        return new Document("_id", id)
                .append("fecha", fecha(f))
                .append("estado", estado)
                .append("paciente", new Document("id", idPac).append("nombre", nomPac))
                .append("medico", new Document("id", idMed).append("nombre", nomMed))
                .append("servicio", new Document("tipo_consulta", tipo).append("monto_esperado", esperado))
                .append("pago", new Document("referencia_stripe", null).append("monto_recibido", null).append("mensaje_estado", null))
                .append("auditorias", new ArrayList<>());
    }

    private static Document transaccionAuditada(String id, String f,
            int idPac, String nomPac, int idMed, String nomMed,
            String tipo, double esperado, String refStripe, double recibido) {
        Document doc = transaccion(id, f, "Auditada", idPac, nomPac, idMed, nomMed, tipo, esperado, refStripe, recibido, "Exitoso");
        doc.put("auditorias", Arrays.asList(
                new Document("id_auditoria", 1)
                        .append("fecha_auditoria", fecha(f))
                        .append("resultado", "Auditada")
                        .append("administrador", new Document("id", 1).append("nombre", "Administrador"))
        ));
        return doc;
    }
}
