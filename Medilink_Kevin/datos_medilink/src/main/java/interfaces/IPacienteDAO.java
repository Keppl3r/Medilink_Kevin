/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objetosNegocio.Paciente;
import excepciones.PersistenciaException;

/**
 *
 * @author keppler
 */
public interface IPacienteDAO {

    Paciente buscarPorId(Integer id) throws PersistenciaException;

}
