/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objetosNegocio.Cita;
import excepciones.PersistenciaException;

/**
 *
 * @author keppler
 */
public interface ICitaDAO {

    Cita guardar(Cita cita) throws PersistenciaException;

    Cita buscarPorId(String id) throws PersistenciaException;
}
