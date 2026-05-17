/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auditarTransacciones;

import dto.DetalleTransaccionDTO;
import dto.FiltrosBusquedaDTO;
import dto.PagoDTO;
import dto.TransaccionDTO;
import objetosNegocio.Auditoria;
import objetosNegocio.Transaccion;
import auditarTransacciones.excepciones.NegocioException;
import excepciones.PersistenciaException;
import interfaces.ITransaccionDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Control del subsistema mapeo, validación y delegación al DAO.
 *
 * @author keppler
 */
class ControlAuditarTransacciones {

    private static final Logger LOG = Logger.getLogger(ControlAuditarTransacciones.class.getName());

    private final ITransaccionDAO transaccionDAO;

    public ControlAuditarTransacciones(ITransaccionDAO transaccionDAO) {
        this.transaccionDAO = transaccionDAO;
    }

    public Integer contarPendientes() throws NegocioException {
        try {
            return transaccionDAO.contarPendientes();
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en bd al contar pendientes", e);
            throw new NegocioException("Error al contar pendientes: " + e.getMessage(), e);
        }
    }

    public List<TransaccionDTO> buscarPorPeriodo(FiltrosBusquedaDTO filtros) throws NegocioException {
        validarRangoFechas(filtros.getInicio(), filtros.getFin());
        try {
            List<Transaccion> transacciones = transaccionDAO.buscarPorRango(
                    filtros.getInicio(), filtros.getFin());
            return mapearListaADTO(transacciones);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en la bd al buscar por periodo", e);
            throw new NegocioException("Error al buscar por periodo: " + e.getMessage(), e);
        }
    }

    public List<TransaccionDTO> buscarPorPaciente(FiltrosBusquedaDTO filtros) throws NegocioException {
        if (filtros.getNombrePaciente() == null || filtros.getNombrePaciente().isBlank()) {
            throw new NegocioException("El nombre del paciente es requerido");
        }
        try {
            List<Transaccion> transacciones = transaccionDAO.buscarPorPaciente(
                    filtros.getNombrePaciente());
            return mapearListaADTO(transacciones);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en la bd al buscar por paciente: " + filtros.getNombrePaciente(), e);
            throw new NegocioException("Error al buscar por paciente: " + e.getMessage(), e);
        }
    }

    public DetalleTransaccionDTO obtenerDetalle(String id) throws NegocioException {
        Transaccion transaccion = buscarTransaccion(id);
        return mapearDetalleADTO(transaccion);
    }

    public void auditarTransaccion(String id) throws NegocioException {
        Transaccion transaccion = buscarTransaccion(id);
        validarConsistencia(transaccion);
        try {
            Auditoria auditoria = new Auditoria(
                    null,
                    LocalDateTime.now(),
                    "Auditada",
                    1,
                    "Administrador"
            );
            transaccionDAO.agregarAuditoria(id, auditoria);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en la bd al auditar transaccion: ", e);
            throw new NegocioException("Error al auditar la transacción: " + e.getMessage(), e);
        }
    }

    public void marcarPendiente(String id) throws NegocioException {
        buscarTransaccion(id);
        try {
            transaccionDAO.actualizarEstado(id, "Pendiente");
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en la bd al marcar pendiente: ", e);
            throw new NegocioException("Error al marcar pendiente: " + e.getMessage(), e);
        }
    }

    public PagoDTO obtenerPago(String idTransaccion) throws NegocioException {
        Transaccion transaccion = buscarTransaccion(idTransaccion);
        return mapearPagoADTO(transaccion);
    }

    //validaciones
    private void validarRangoFechas(LocalDateTime inicio, LocalDateTime fin) throws NegocioException {
        if (inicio == null || fin == null) {
            throw new NegocioException("Las fechas de inicio y fin son requeridas");
        }
        if (inicio.isAfter(fin)) {
            throw new NegocioException("La fecha de inicio no puede ser posterior a la fecha fin");
        }
    }

    private void validarConsistencia(Transaccion transaccion) throws NegocioException {
        if (transaccion.getMontoRecibido() == null || transaccion.getMontoEsperado() == null) {
            throw new NegocioException("Los montos no pueden ser nulos");
        }
        if (!transaccion.getMontoRecibido().equals(transaccion.getMontoEsperado())) {
            throw new NegocioException("Inconsistencia: monto recibido ("
                    + transaccion.getMontoRecibido() + ") no coincide con monto esperado ("
                    + transaccion.getMontoEsperado() + ")");
        }
        if (!"Exitoso".equals(transaccion.getMensajeEstado())) {
            throw new NegocioException("El pago de Stripe no fue exitoso: " + transaccion.getMensajeEstado());
        }
    }

    //mapeo 
    private Transaccion buscarTransaccion(String id) throws NegocioException {
        if (id == null || id.isBlank()) {
            throw new NegocioException("El ID de transacción es requerido");
        }
        try {
            Transaccion transaccion = transaccionDAO.buscarPorId(id);
            if (transaccion == null) {
                throw new NegocioException("No se encontró la transacción con ID: " + id);
            }
            return transaccion;
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error en la bd al buscar transacción: ", e);
            throw new NegocioException("Error al buscar transacción: " + e.getMessage(), e);
        }
    }

    private TransaccionDTO mapearADTO(Transaccion transaccion) {
        TransaccionDTO dto = new TransaccionDTO();
        dto.setId(transaccion.getId());
        dto.setFecha(transaccion.getFecha());
        dto.setMontoRecibido(transaccion.getMontoRecibido());
        dto.setEstado(transaccion.getEstado());
        dto.setNombrePaciente(transaccion.getNombrePaciente());
        dto.setNombreMedico(transaccion.getNombreMedico());
        return dto;
    }

    private List<TransaccionDTO> mapearListaADTO(List<Transaccion> transacciones) {
        List<TransaccionDTO> lista = new ArrayList<>();
        for (Transaccion t : transacciones) {
            lista.add(mapearADTO(t));
        }
        return lista;
    }

    private DetalleTransaccionDTO mapearDetalleADTO(Transaccion transaccion) {
        DetalleTransaccionDTO dto = new DetalleTransaccionDTO();
        dto.setId(transaccion.getId());
        dto.setFecha(transaccion.getFecha());
        dto.setEstado(transaccion.getEstado());
        dto.setNombrePaciente(transaccion.getNombrePaciente());
        dto.setNombreMedico(transaccion.getNombreMedico());
        dto.setTipoConsulta(transaccion.getTipoConsulta());
        dto.setMontoEsperado(transaccion.getMontoEsperado());
        dto.setMontoRecibido(transaccion.getMontoRecibido());
        dto.setReferenciaStripe(transaccion.getReferenciaStripe());
        dto.setMensajeEstado(transaccion.getMensajeEstado());
        return dto;
    }

    private PagoDTO mapearPagoADTO(Transaccion transaccion) {
        PagoDTO dto = new PagoDTO();
        dto.setReferenciaStripe(transaccion.getReferenciaStripe());
        dto.setMontoRecibido(transaccion.getMontoRecibido());
        dto.setMensajeEstado(transaccion.getMensajeEstado());
        dto.setIdTransaccion(transaccion.getId());
        return dto;
    }
}
