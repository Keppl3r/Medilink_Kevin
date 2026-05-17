/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ssPagos;

/**
 *
 * @author keppler
 */
public interface IPagos {

    boolean procesarPago(Double monto, String datosTarjeta);

    String consultarEstadoPago(String idTransaccion);
}
