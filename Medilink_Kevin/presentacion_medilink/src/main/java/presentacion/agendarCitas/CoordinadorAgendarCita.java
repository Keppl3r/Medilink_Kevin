/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.agendarCitas;

import agendarCitas.AgendarCitaBO;
import agendarCitas.IAgendarCita;
import agendarCitas.excepciones.NegocioAgendarException;
import dto.CitaDTO;
import dto.DoctorDTO;
import java.util.List;

/**
 *
 * @author keppler
 */
public class CoordinadorAgendarCita {

    private final IAgendarCita negocio;

    public CoordinadorAgendarCita() {
        this.negocio = new AgendarCitaBO();
    }

    public List<DoctorDTO> obtenerEspecialistas() throws NegocioAgendarException {
        return negocio.obtenerEspecialistas();
    }

    public boolean verificarDisponibilidad(Integer idDoctor)
            throws NegocioAgendarException {
        return negocio.verificarDisponibilidad(idDoctor);
    }

    public CitaDTO registrarCita(CitaDTO cita) throws NegocioAgendarException {
        return negocio.registrarCita(cita);
    }

    public String procesarPago(String idCita, String datosPago)
            throws NegocioAgendarException {
        return negocio.procesarPago(idCita, datosPago);
    }

    public boolean enviarConfirmacion(String idCita, String correo)
            throws NegocioAgendarException {
        return negocio.enviarConfirmacion(idCita, correo);
    }
}
