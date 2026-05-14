/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import control.ControlAuditarTransacciones;
import daos.DAOFactory;
import dtos.DetalleTransaccionDTO;
import dtos.FiltrosBusquedaDTO;
import dtos.PagoDTO;
import dtos.TransaccionDTO;
import excepciones.NegocioException;
import interfaces.IAuditarTransacciones;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fachada del subsistema auditarTransacciones.
 * Delega al ControlAuditarTransacciones
 * @author keppler
 */
public class AuditarTransaccionesBO implements IAuditarTransacciones {

    private static final Logger LOG = Logger.getLogger(AuditarTransaccionesBO.class.getName());
    private final ControlAuditarTransacciones control;

    public AuditarTransaccionesBO() {
        this.control = new ControlAuditarTransacciones(
                DAOFactory.getInstancia().getTransaccionDAO()
        );
    }

    @Override
    public Integer contarPendientes() throws NegocioException {
        Integer pendientes = control.contarPendientes();
        LOG.log(Level.INFO, "Pendientes encontrados: {0}", pendientes);
        return pendientes;
    }

    @Override
    public List<TransaccionDTO> buscarPorPeriodo(FiltrosBusquedaDTO filtros) throws NegocioException {
        List<TransaccionDTO> lista = control.buscarPorPeriodo(filtros);
        LOG.log(Level.INFO, "Transacciones encontradas por periodo: {0}", lista.size());
        return lista;
    }

    @Override
    public List<TransaccionDTO> buscarPorPaciente(FiltrosBusquedaDTO filtros) throws NegocioException {
        List<TransaccionDTO> lista = control.buscarPorPaciente(filtros);
        LOG.log(Level.INFO, "Transacciones encontradas por paciente: {0}", lista.size());
        return lista;
    }

    @Override
    public DetalleTransaccionDTO obtenerDetalle(String id) throws NegocioException {
        DetalleTransaccionDTO detalle = control.obtenerDetalle(id);
        LOG.log(Level.INFO, "Detalle obtenido para transaccion: {0}", id);
        return detalle;
    }

    @Override
    public void auditarTransaccion(String id) throws NegocioException {
        control.auditarTransaccion(id);
        LOG.log(Level.INFO, "Transaccion auditada: {0}", id);
    }

    @Override
    public void marcarPendiente(String id) throws NegocioException {
        control.marcarPendiente(id);
        LOG.log(Level.INFO, "Transaccion marcada como pendiente: {0}", id);
    }

    @Override
    public PagoDTO obtenerPago(String idTransaccion) throws NegocioException {
        PagoDTO pago = control.obtenerPago(idTransaccion);
        LOG.log(Level.INFO, "Pago obtenido para transaccion: {0}", idTransaccion);
        return pago;
    }
}