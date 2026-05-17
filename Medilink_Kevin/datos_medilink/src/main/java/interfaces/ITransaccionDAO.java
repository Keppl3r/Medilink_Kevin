/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Gracias a que desnormalicé la bd, (que no lo había hecho malamente) ahora
 * todas las operaciones se trabajan sobre la colección de transacciones
 *
 * @author keppler
 */
public interface ITransaccionDAO {

    public Transaccion buscarPorId(String id) throws PersistenciaException;

    public List<Transaccion> buscarPorRango(LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException;

    public List<Transaccion> buscarPorPaciente(String nombre) throws PersistenciaException;

    Integer contarPendientes() throws PersistenciaException;

    public void actualizarEstado(String id, String estado) throws PersistenciaException;

    public void agregarAuditoria(String idTransaccion, Auditoria auditoria) throws PersistenciaException;

    public Transaccion insertar(Transaccion transaccion) throws PersistenciaException;
}
