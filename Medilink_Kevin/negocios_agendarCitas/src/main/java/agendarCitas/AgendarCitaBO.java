/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agendarCitas;

import agendarCitas.excepciones.NegocioAgendarException;
import dto.CitaDTO;
import dto.DoctorDTO;
import java.util.List;

/**
 *
 * @author keppler
 */
public class AgendarCitaBO implements IAgendarCita {

    private final ControlAgendarCita control;

    public AgendarCitaBO() {
        this.control = new ControlAgendarCita();
    }

    @Override
    public List<DoctorDTO> obtenerEspecialistas() throws NegocioAgendarException {
        return control.obtenerEspecialistas();
    }

    @Override
    public boolean verificarDisponibilidad(Integer idDoctor) throws NegocioAgendarException {
        return control.verificarDisponibilidad(idDoctor);
    }

    @Override
    public CitaDTO registrarCita(CitaDTO cita) throws NegocioAgendarException {
        return control.registrarCita(cita);
    }

    @Override
    public boolean procesarPago(String idCita, String datosPago) throws NegocioAgendarException {
        return control.procesarPago(idCita, datosPago);
    }

    @Override
    public boolean enviarConfirmacion(String idCita, String correo) throws NegocioAgendarException {
        return control.enviarConfirmacion(idCita, correo);
    }
}
