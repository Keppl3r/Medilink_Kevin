/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ssPagos;

/**
 *
 * @author keppler
 */
public class SSPagos implements IPagos {
    @Override
    public boolean procesarPago(Double monto, String datosTarjeta) {
        if (datosTarjeta == null || datosTarjeta.isBlank()) return false;
        if (datosTarjeta.contains("FONDOS")) return false; 
        if (datosTarjeta.contains("ERROR")) return false;   
        return true; 
    }
    @Override
    public String consultarEstadoPago(String idTransaccion) {
        return "Exitoso";
    }
}
