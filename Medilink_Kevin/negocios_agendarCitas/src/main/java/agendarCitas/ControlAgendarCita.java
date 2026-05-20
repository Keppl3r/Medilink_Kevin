/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agendarCitas;

import agendarCitas.excepciones.NegocioAgendarException;
import daos.DAOFactory;
import dto.CitaDTO;
import dto.DoctorDTO;
import excepciones.PersistenciaException;
import interfaces.ICitaDAO;
import interfaces.IDoctorDAO;
import interfaces.IPacienteDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Cita;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;
import ssNotificaciones.ICorreos;
import ssNotificaciones.SSNotificaciones;
import ssPagos.IPagos;
import ssPagos.SSPagos;
import interfaces.ITransaccionDAO;
import objetosNegocio.Transaccion;

/**
 *
 * @author keppler
 */
class ControlAgendarCita {

    private static final Logger LOG = Logger.getLogger(ControlAgendarCita.class.getName());
// contador para el id de la cita 
    private static int contador = 1;
    private final ICitaDAO citaDAO;
    private final IDoctorDAO doctorDAO;
    private final IPacienteDAO pacienteDAO;
    private final IPagos pagos;
    private final ICorreos correos;
    //este dao me ayuda a conectar los dos CUs
    private final ITransaccionDAO transaccionDAO;

    ControlAgendarCita() {
        DAOFactory factory = DAOFactory.getInstancia();
        this.citaDAO = factory.getCitaDAO();
        this.doctorDAO = factory.getDoctorDAO();
        this.pacienteDAO = factory.getPacienteDAO();
        this.pagos = new SSPagos();
        this.correos = new SSNotificaciones();
        this.transaccionDAO = factory.getTransaccionDAO();
    }

    List<DoctorDTO> obtenerEspecialistas() throws NegocioAgendarException {
        try {
            List<Doctor> doctores = doctorDAO.buscarDisponibles();
            List<DoctorDTO> lista = new ArrayList<>();
            for (Doctor d : doctores) {
                lista.add(mapearDoctorADTO(d));
            }
            return lista;
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al obtener especialistas", e);
            throw new NegocioAgendarException(
                    "Error al obtener especialistas: " + e.getMessage(), e);
        }
    }

    boolean verificarDisponibilidad(Integer idDoctor)
            throws NegocioAgendarException {
        Doctor doctor = obtenerDoctorValido(idDoctor);
        return Boolean.TRUE.equals(doctor.getDisponible());
    }

    CitaDTO registrarCita(CitaDTO citaDTO) throws NegocioAgendarException {
        // validacion de vacios 
        if (citaDTO == null
                || citaDTO.getMotivo() == null || citaDTO.getMotivo().isBlank()
                || citaDTO.getIdMedico() == null
                || citaDTO.getIdPaciente() == null) {
            throw new NegocioAgendarException("Datos de la cita incompletos");
        }
        try {
            Doctor doctor = obtenerDoctorValido(citaDTO.getIdMedico());
            // si el doctor ya no esta disponible no agenda
            if (!Boolean.TRUE.equals(doctor.getDisponible())) {
                throw new NegocioAgendarException(
                        "El especialista no está disponible");
            }

            // el paciente tiene que existir
            Paciente paciente = pacienteDAO.buscarPorId(citaDTO.getIdPaciente());
            if (paciente == null) {
                throw new NegocioAgendarException(
                        "El paciente no está registrado");
            }
            if (citaDTO.getNombrePaciente() != null && !citaDTO.getNombrePaciente().isBlank()) {
                paciente.setNombre(citaDTO.getNombrePaciente());
            }

            // se arma la cita
            Cita cita = new Cita();
            cita.setId("CITA-" + contador++);
            cita.setFecha(LocalDateTime.now());
            cita.setHora(citaDTO.getHora() != null
                    ? citaDTO.getHora() : "10:00");
            cita.setUbicacion("Centro Médico Medilink");
            cita.setMotivo(citaDTO.getMotivo());
            cita.setSintomas(citaDTO.getSintomas());
            cita.setEstado("PENDIENTE_PAGO");
            cita.setMonto(doctor.getCostoConsulta());
            cita.setPaciente(paciente);
            cita.setMedico(doctor);

            Cita guardada = citaDAO.guardar(cita);
            return mapearCitaADTO(guardada);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al registrar la cita", e);
            throw new NegocioAgendarException(
                    "Error al registrar la cita: " + e.getMessage(), e);
        }
    }
// segun lo que responda el mock de pagos se pone el estado de la cita

    String procesarPago(String idCita, String datosPago)
            throws NegocioAgendarException {
        Cita cita = buscarCita(idCita);
        if ("PAGADA".equals(cita.getEstado())) {
            throw new NegocioAgendarException("La cita ya fue pagada");
        }
        if (datosPago == null || datosPago.isBlank()) {
            return "DATOS_ERRONEOS";
        }

        boolean aprobado = pagos.procesarPago(cita.getMonto(), datosPago);

        if (aprobado) {
            cita.setEstado("PAGADA");
            cita.setReferenciaStripe(pagos.consultarEstadoPago(idCita));
            cita.setMontoPagado(cita.getMonto());
            cita.setMensajeEstadoPago("Exitoso");
            try {
                citaDAO.guardar(cita);
                //aqui lo mando al auditar
                registrarTransaccionDeAuditoria(cita);
            } catch (PersistenciaException e) {
                LOG.log(Level.SEVERE, "Error al guardar pago", e);
                throw new NegocioAgendarException(
                        "Error al guardar el pago: " + e.getMessage(), e);
            }
            return "EXITOSO";
        }

        // si no se aprobo, guardo el rechazo y veo de que tipo fue
        cita.setEstado("PAGO_RECHAZADO");
        try {
            citaDAO.guardar(cita);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al guardar rechazo", e);
        }
        if (datosPago.toUpperCase().contains("FONDOS")) {
            cita.setMensajeEstadoPago("Fondos insuficientes");
            return "FONDOS_INSUFICIENTES";
        }
        cita.setMensajeEstadoPago("Datos erróneos");
        return "DATOS_ERRONEOS";
    }

    boolean enviarConfirmacion(String idCita, String correo)
            throws NegocioAgendarException {
        Cita cita = buscarCita(idCita);
        if (!"PAGADA".equals(cita.getEstado())) {
            throw new NegocioAgendarException(
                    "Solo se confirma una cita pagada");
        }
        if (correo == null || !correo.contains("@")) {
            throw new NegocioAgendarException("Correo inválido");
        }
        String asunto = "Confirmación de cita " + cita.getId();
        String cuerpo = "Estimado " + cita.getPaciente().getNombre()
                + ", su cita con " + cita.getMedico().getNombre()
                + " (" + cita.getMedico().getEspecialidad() + ") quedó"
                + " confirmada. Monto pagado: $" + cita.getMontoPagado();
        return correos.enviarNotificacion(correo, asunto, cuerpo);
    }

    private Doctor obtenerDoctorValido(Integer idDoctor)
            throws NegocioAgendarException {
        if (idDoctor == null) {
            throw new NegocioAgendarException("Falta el id del doctor");
        }
        try {
            Doctor doctor = doctorDAO.buscarPorId(idDoctor);
            if (doctor == null) {
                throw new NegocioAgendarException(
                        "El doctor no existe: " + idDoctor);
            }
            return doctor;
        } catch (PersistenciaException e) {
            throw new NegocioAgendarException(
                    "No se pudo buscar el doctor", e);
        }
    }

    private DoctorDTO mapearDoctorADTO(Doctor d) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(d.getId());
        dto.setNombre(d.getNombre());
        dto.setEspecialidad(d.getEspecialidad());
        dto.setCostoConsulta(d.getCostoConsulta());
        dto.setDisponible(d.getDisponible());
        return dto;
    }

    private CitaDTO mapearCitaADTO(Cita c) {
        CitaDTO dto = new CitaDTO();
        dto.setId(c.getId());
        dto.setFecha(c.getFecha());
        dto.setHora(c.getHora());
        dto.setUbicacion(c.getUbicacion());
        dto.setMotivo(c.getMotivo());
        dto.setSintomas(c.getSintomas());
        dto.setEstado(c.getEstado());
        dto.setMonto(c.getMonto());
        if (c.getPaciente() != null) {
            dto.setIdPaciente(c.getPaciente().getId());
            dto.setNombrePaciente(c.getPaciente().getNombre());
        }
        if (c.getMedico() != null) {
            dto.setIdMedico(c.getMedico().getId());
            dto.setNombreMedico(c.getMedico().getNombre());
            dto.setEspecialidadMedico(c.getMedico().getEspecialidad());
        }
        return dto;
    }

    private Cita buscarCita(String idCita) throws NegocioAgendarException {
        if (idCita == null || idCita.isBlank()) {
            throw new NegocioAgendarException("El id de la cita es requerido");
        }
        try {
            Cita cita = citaDAO.buscarPorId(idCita);
            if (cita == null) {
                throw new NegocioAgendarException(
                        "La cita no existe: " + idCita);
            }
            return cita;
        } catch (PersistenciaException e) {
            throw new NegocioAgendarException(
                    "No se pudo buscar la cita", e);
        }
    }

    private void registrarTransaccionDeAuditoria(Cita cita) throws NegocioAgendarException {
        try {
            LOG.info("Iniciando auditoría para la cita: " + cita.getId());

            Transaccion t = new Transaccion();
            t.setId(cita.getId());
            t.setFecha(java.time.LocalDateTime.now());
            t.setEstado("Pendiente");

            if (cita.getPaciente() != null) {
                t.setIdPaciente(cita.getPaciente().getId());
                t.setNombrePaciente(cita.getPaciente().getNombre());
            } else {
                LOG.warning("El paciente está nulo desde la bd ");
                t.setNombrePaciente("Paciente Desconocido");
            }

            if (cita.getMedico() != null) {
                t.setIdMedico(cita.getMedico().getId());
                t.setNombreMedico(cita.getMedico().getNombre());
            } else {
                LOG.warning("ADVERTENCIA: El médico vino nulo desde MongoDB (Revisar CitaDocumentoAdaptador)");
                t.setNombreMedico("Médico Desconocido");
            }

            t.setTipoConsulta(cita.getMotivo());
            t.setMontoEsperado(cita.getMonto());
            t.setReferenciaStripe(cita.getReferenciaStripe());
            t.setMontoRecibido(cita.getMontoPagado());
            t.setMensajeEstado("Exitoso");

            transaccionDAO.insertar(t);
            LOG.info("Transacción de auditoría registrada exitosamente.");

        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al registrar la transaccion de auditoria", e);
            throw new NegocioAgendarException("Error al registrar la transacción: " + e.getMessage(), e);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error al procesar la auditoría", e);
        }
    }
}
