/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidadesMongo.CitaMongoEntidad;
import entidadesMongo.DoctorMongoEntidad;
import entidadesMongo.PacienteMongoEntidad;
import entidadesMongo.TransaccionMongoEntidad;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Conexion a MongoDB de la capa de persistencia.
 * Configura codecs para las entidades mongo
 * @author keppler
 */
public class MongoConection {

    private static final String URL = "mongodb://localhost:27017";
    private static final String BASE_DATOS = "medilink";

    private static MongoClient cliente;

    private MongoConection() {
    }

    public static MongoClient obtenerCliente() {
        if (cliente == null) {
            CodecProvider proveedorPojo = PojoCodecProvider.builder()
                    .automatic(true)
                    .build();

            CodecRegistry registro = fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(proveedorPojo));

            MongoClientSettings config = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(URL))
                    .codecRegistry(registro)
                    .build();

            cliente = MongoClients.create(config);
        }
        return cliente;
    }

    public static MongoDatabase obtenerBaseDatos() {
        return obtenerCliente().getDatabase(BASE_DATOS);
    }

    public static MongoCollection<CitaMongoEntidad> obtenerColeccionCitas() {
        return obtenerBaseDatos()
                .getCollection("citas", CitaMongoEntidad.class);
    }

    public static MongoCollection<DoctorMongoEntidad> obtenerColeccionDoctores() {
        return obtenerBaseDatos()
                .getCollection("doctores", DoctorMongoEntidad.class);
    }

    public static MongoCollection<PacienteMongoEntidad> obtenerColeccionPacientes() {
        return obtenerBaseDatos()
                .getCollection("pacientes", PacienteMongoEntidad.class);
    }

    public static MongoCollection<TransaccionMongoEntidad> obtenerColeccionTransacciones() {
        return obtenerBaseDatos()
                .getCollection("transacciones", TransaccionMongoEntidad.class);
    }
}