/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Conexión singleton a MongoDB. Usa patrón adapter en vez de codecs.
 * @author keppler
 */
public class MongoConection {
    private static final String URL = "mongodb://localhost:27017";
    private static final String BASE_DATOS = "medilink";
    private static MongoClient cliente;

    public MongoConection() {}

    private static MongoClient obtenerCliente() {
        if (cliente == null) {
            cliente = MongoClients.create(URL);
        }
        return cliente;
    }

    public static MongoDatabase obtenerBaseDatos() {
        return obtenerCliente().getDatabase(BASE_DATOS);
    }
}
