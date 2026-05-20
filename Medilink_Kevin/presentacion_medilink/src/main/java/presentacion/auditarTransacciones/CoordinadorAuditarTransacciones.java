/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.auditarTransacciones;

import dto.ReporteEstadoDTO;
import auditarTransacciones.AuditarTransaccionesBO;
import dto.DetalleTransaccionDTO;
import dto.FiltrosBusquedaDTO;
import dto.PagoDTO;
import dto.TransaccionDTO;
import auditarTransacciones.excepciones.NegocioException;
import auditarTransacciones.IAuditarTransacciones;
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
    
    public List<ReporteEstadoDTO> reportePorEstado() throws NegocioException {
    return negocio.reportePorEstado();
}
}
