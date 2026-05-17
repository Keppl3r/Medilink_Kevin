/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ssNotificaciones;

import java.util.logging.Logger;

/**
 *
 * @author keppler
 */
public class SSNotificaciones implements ICorreos {

    private static final Logger LOG
            = Logger.getLogger(SSNotificaciones.class.getName());

    @Override
    public boolean enviarNotificacion(String destinatario, String asunto, String cuerpo) {
        LOG.info("mock correo a " + destinatario + ", " + asunto);
        return true;
    }
}
