/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import excepciones.PersistenciaException;
import java.util.List;
import objetosNegocio.ReporteEstado;

/**
 *
 * @author keppler
 */
public interface IReporteTransaccionDAO {
    
    List<ReporteEstado> reportePorEstado() throws PersistenciaException;

}
