/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

/**
 * esta clase es para el aggregate de transacciones segun el estado.
 *
 * @author keppler
 */
public class ReporteEstado {

    private String estado;
    private int totalTransacciones;
    private double montoPromedio;

    public ReporteEstado() {
    }

    public ReporteEstado(String estado, int totalTransacciones, double montoPromedio) {
        this.estado = estado;
        this.totalTransacciones = totalTransacciones;
        this.montoPromedio = montoPromedio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getTotalTransacciones() {
        return totalTransacciones;
    }

    public void setTotalTransacciones(int totalTransacciones) {
        this.totalTransacciones = totalTransacciones;
    }

    public double getMontoPromedio() {
        return montoPromedio;
    }

    public void setMontoPromedio(double montoPromedio) {
        this.montoPromedio = montoPromedio;
    }

}
