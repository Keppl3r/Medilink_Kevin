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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Cita;
import objetosNegocio.Doctor;
import ssNotificaciones.ICorreos;
import ssNotificaciones.SSNotificaciones;
import ssPagos.IPagos;
import ssPagos.SSPagos;

/**
 *
 * @author keppler
 */
class ControlAgendarCita {

    private static final Logger LOG
            = Logger.getLogger(ControlAgendarCita.class.getName());

    private final ICitaDAO citaDAO;
    private final IDoctorDAO doctorDAO;
    private final IPacienteDAO pacienteDAO;
    private final IPagos pagos;
    private final ICorreos correos;

    ControlAgendarCita() {
        DAOFactory factory = DAOFactory.getInstancia();
        this.citaDAO = factory.getCitaDAO();
        this.doctorDAO = factory.getDoctorDAO();
        this.pacienteDAO = factory.getPacienteDAO();
        this.pagos = new SSPagos();
        this.correos = new SSNotificaciones();
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

    boolean verificarDisponibilidad(Integer idDoctor) throws NegocioAgendarException {
        if (idDoctor == null) {
            throw new NegocioAgendarException("El id del doctor es requerido");
        }
        try {
            Doctor doctor = doctorDAO.buscarPorId(idDoctor);
            if (doctor == null) {
                throw new NegocioAgendarException("El doctor no existe");
            }
            return Boolean.TRUE.equals(doctor.getDisponible());
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al verificar disponibilidad", e);
            throw new NegocioAgendarException(
                    "Error al verificar disponibilidad: " + e.getMessage(), e);
        }
    }

    CitaDTO registrarCita(CitaDTO citaDTO) throws NegocioAgendarException {
        if (!validarDatosCompletos(citaDTO)) {
            throw new NegocioAgendarException("Datos de la cita incompletos");
        }
        try {
            Cita cita = new Cita();
            cita.setId("CITA-" + UUID.randomUUID().toString().substring(0, 8));
            cita.setFecha(citaDTO.getFecha() != null
                    ? citaDTO.getFecha() : LocalDateTime.now());
            cita.setHora(citaDTO.getHora());
            cita.setUbicacion(citaDTO.getUbicacion());
            cita.setMotivo(citaDTO.getMotivo());
            cita.setSintomas(citaDTO.getSintomas());
            cita.setEstado("AGENDADA");
            cita.setMonto(citaDTO.getMonto());
            objetosNegocio.Paciente p = new objetosNegocio.Paciente();
            p.setNombre(citaDTO.getNombrePaciente());
            cita.setPaciente(p);
            objetosNegocio.Doctor d = new objetosNegocio.Doctor();
            d.setNombre(citaDTO.getNombreMedico());
            d.setEspecialidad(citaDTO.getEspecialidadMedico());
            cita.setMedico(d);

            Cita guardada = citaDAO.guardar(cita);
            return mapearCitaADTO(guardada);
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al registrar la cita", e);
            throw new NegocioAgendarException(
                    "Error al registrar la cita: " + e.getMessage(), e);
        }
    }

    boolean procesarPago(String idCita, String datosPago)
            throws NegocioAgendarException {
        Cita cita = buscarCita(idCita);
        boolean aprobado = pagos.procesarPago(cita.getMonto(), datosPago);
        if (aprobado) {
            cita.setEstado("PAGADA");
            cita.setReferenciaStripe(pagos.consultarEstadoPago(idCita));
            cita.setMontoPagado(cita.getMonto());
            cita.setMensajeEstadoPago("Exitoso");
            try {
                citaDAO.guardar(cita);
            } catch (PersistenciaException e) {
                LOG.log(Level.SEVERE, "Error al actualizar cita pagada", e);
                throw new NegocioAgendarException(
                        "Error al guardar el pago: " + e.getMessage(), e);
            }
        }
        return aprobado;
    }

    boolean enviarConfirmacion(String idCita, String correo)
            throws NegocioAgendarException {
        Cita cita = buscarCita(idCita);
        String asunto = "Confirmación de cita " + cita.getId();
        String cuerpo = "Su cita quedó registrada para el "
                + cita.getFecha() + ". Estado: " + cita.getEstado();
        return correos.enviarNotificacion(correo, asunto, cuerpo);
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
            dto.setNombrePaciente(c.getPaciente().getNombre());
        }
        if (c.getMedico() != null) {
            dto.setNombreMedico(c.getMedico().getNombre());
            dto.setEspecialidadMedico(c.getMedico().getEspecialidad());
        }
        return dto;
    }

    private boolean validarDatosCompletos(CitaDTO c) {
        return c != null
                && c.getMotivo() != null && !c.getMotivo().isBlank()
                && c.getNombrePaciente() != null && !c.getNombrePaciente().isBlank()
                && c.getNombreMedico() != null && !c.getNombreMedico().isBlank()
                && c.getMonto() != null;
    }

    private Cita buscarCita(String idCita) throws NegocioAgendarException {
        if (idCita == null || idCita.isBlank()) {
            throw new NegocioAgendarException("El id de la cita es requerido");
        }
        try {
            Cita cita = citaDAO.buscarPorId(idCita);
            if (cita == null) {
                throw new NegocioAgendarException("La cita no existe: " + idCita);
            }
            return cita;
        } catch (PersistenciaException e) {
            LOG.log(Level.SEVERE, "Error al buscar la cita", e);
            throw new NegocioAgendarException(
                    "Error al buscar la cita: " + e.getMessage(), e);
        }
    }
}
