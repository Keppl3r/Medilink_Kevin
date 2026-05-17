/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import objetosNegocio.Doctor;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author keppler
 */
public interface IDoctorDAO {

    List<Doctor> buscarDisponibles() throws PersistenciaException;

    Doctor buscarPorId(Integer id) throws PersistenciaException;
}
