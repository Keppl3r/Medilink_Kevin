/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Factura;
import excepciones.PersistenciaException;

/**
 *
 * @author keppler
 */
public interface IFacturaDAO {

    public Factura buscarPorTransaccion(Integer idTransaccion) throws PersistenciaException;
}
