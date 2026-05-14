/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Transaccion;
import excepciones.PersistenciaException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author keppler
 */
public interface ITransaccionDAO {

    public Transaccion buscarPorId(Integer id) throws PersistenciaException;

    public List<Transaccion> buscarPorRango(Date inicio, Date fin) throws PersistenciaException;

    public List<Transaccion> buscarPorPaciente(String nombre) throws PersistenciaException;

    public Integer contarPendientes() throws PersistenciaException;

    public void actualizarEstado(Integer id, String estado) throws PersistenciaException;
}
