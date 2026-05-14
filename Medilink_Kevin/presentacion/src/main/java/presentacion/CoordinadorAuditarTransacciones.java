/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import BOs.AuditarTransaccionesBO;
import dtos.DetalleTransaccionDTO;
import dtos.FiltrosBusquedaDTO;
import dtos.PagoDTO;
import dtos.TransaccionDTO;
import excepciones.NegocioException;
import interfaces.IAuditarTransacciones;
import java.util.List;

/**
 *
 * @author keppler
 */
public class CoordinadorAuditarTransacciones {

    private final IAuditarTransacciones negocio;

    public CoordinadorAuditarTransacciones() {
        this.negocio = new AuditarTransaccionesBO();
    }

    public Integer contarPendientes() throws NegocioException {
        return negocio.contarPendientes();
    }

    public List<TransaccionDTO> buscarPorPeriodo(FiltrosBusquedaDTO filtros) throws NegocioException {
        return negocio.buscarPorPeriodo(filtros);
    }

    public List<TransaccionDTO> buscarPorPaciente(FiltrosBusquedaDTO filtros) throws NegocioException {
        return negocio.buscarPorPaciente(filtros);
    }

    public DetalleTransaccionDTO obtenerDetalle(String id) throws NegocioException {
        return negocio.obtenerDetalle(id);
    }

    public void auditarTransaccion(String id) throws NegocioException {
        negocio.auditarTransaccion(id);
    }

    public void marcarPendiente(String id) throws NegocioException {
        negocio.marcarPendiente(id);
    }

    public PagoDTO obtenerPago(String idTransaccion) throws NegocioException {
        return negocio.obtenerPago(idTransaccion);
    }
}
