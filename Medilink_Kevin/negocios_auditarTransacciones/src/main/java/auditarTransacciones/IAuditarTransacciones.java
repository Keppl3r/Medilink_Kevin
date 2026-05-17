/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package auditarTransacciones;

import dto.DetalleTransaccionDTO;
import dto.FiltrosBusquedaDTO;
import dto.PagoDTO;
import dto.TransaccionDTO;
import auditarTransacciones.excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz del subsistema auditarTransacciones.
 *
 * @author keppler
 */
public interface IAuditarTransacciones {

    public Integer contarPendientes() throws NegocioException;

    public List<TransaccionDTO> buscarPorPeriodo(FiltrosBusquedaDTO filtros) throws NegocioException;

    public List<TransaccionDTO> buscarPorPaciente(FiltrosBusquedaDTO filtros) throws NegocioException;

    public DetalleTransaccionDTO obtenerDetalle(String id) throws NegocioException;

    public void auditarTransaccion(String id) throws NegocioException;

    public void marcarPendiente(String id) throws NegocioException;

    public PagoDTO obtenerPago(String idTransaccion) throws NegocioException;
}
