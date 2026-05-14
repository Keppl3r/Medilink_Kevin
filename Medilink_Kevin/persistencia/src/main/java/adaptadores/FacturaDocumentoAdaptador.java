/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import entidades.Factura;
import org.bson.Document;

/**
 * Convierte entre Factura y Document de Mongo
 *
 * @author keppler
 */
public class FacturaDocumentoAdaptador {

    public Document convertirADocumento(Factura factura) {
        Document doc = new Document();
        doc.append("folio", factura.getFolio())
                .append("fechaEmision", factura.getFechaEmision())
                .append("monto", factura.getMonto())
                .append("iva", factura.getIva())
                .append("estado", factura.getEstado())
                .append("idTransaccion", factura.getIdTransaccion());
        return doc;
    }

    public Factura convertirAEntidad(Document doc) {
        Factura factura = new Factura();
        factura.setFolio(doc.getString("folio"));
        factura.setFechaEmision(doc.getDate("fechaEmision"));
        factura.setMonto(doc.getDouble("monto"));
        factura.setIva(doc.getDouble("iva"));
        factura.setEstado(doc.getString("estado"));
        factura.setIdTransaccion(doc.getInteger("idTransaccion"));
        return factura;
    }
}
