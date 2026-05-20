/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import objetosNegocio.ReporteEstado;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import conexion.MongoConection;
import excepciones.PersistenciaException;
import interfaces.IReporteTransaccionDAO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Sorts.descending;


/**
 * este DAO genera un reporte de transacciones con aggregate
 *
 * @author keppler
 */
public class ReporteTransaccionDAO implements IReporteTransaccionDAO {

    private static final Logger LOG
            = Logger.getLogger(ReporteTransaccionDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public ReporteTransaccionDAO() {
        // el aggregate trabaja con Document, pido la coleccion sin tipar
        this.coleccion = MongoConection.obtenerBaseDatos()
                .getCollection("transacciones");
    }

    @Override
public List<ReporteEstado> reportePorEstado() throws PersistenciaException {
    LOG.log(Level.INFO, "Generando reporte de transacciones por estado");
    try {
        List<Bson> pipeline = Arrays.asList(
                group("$estado",
                        sum("total", 1),
                        avg("promedio", "$montoEsperado")),
                sort(descending("total"))
        );

        List<Document> documentos = coleccion
                .aggregate(pipeline, Document.class)
                .into(new ArrayList<>());

        List<ReporteEstado> reportes = new ArrayList<>();
        for (Document doc : documentos) {
            String estado = doc.getString("_id");
            Number total = doc.get("total", Number.class);
            Number promedio = doc.get("promedio", Number.class);

            reportes.add(new ReporteEstado(
                    estado,
                    total == null ? 0 : total.intValue(),
                    promedio == null ? 0 : promedio.doubleValue()));
        }
        return reportes;

    } catch (MongoException ex) {
        throw new PersistenciaException(
                "No se pudo generar el reporte por estado", ex);
    }
}
}