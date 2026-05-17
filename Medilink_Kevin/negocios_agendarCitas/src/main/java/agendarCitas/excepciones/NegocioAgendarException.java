/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agendarCitas.excepciones;

/**
 *
 * @author keppler
 */
public class NegocioAgendarException extends Exception {

    public NegocioAgendarException(String mensaje) {
        super(mensaje);
    }

    public NegocioAgendarException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
