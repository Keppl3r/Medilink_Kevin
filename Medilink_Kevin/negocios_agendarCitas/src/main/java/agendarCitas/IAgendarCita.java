/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public interface IAgendarCita {

    List<DoctorDTO> obtenerEspecialistas() throws NegocioAgendarException;

    boolean verificarDisponibilidad(Integer idDoctor) throws NegocioAgendarException;

    CitaDTO registrarCita(CitaDTO cita) throws NegocioAgendarException;

    boolean procesarPago(String idCita, String datosPago) throws NegocioAgendarException;

    boolean enviarConfirmacion(String idCita, String correo) throws NegocioAgendarException;
}
