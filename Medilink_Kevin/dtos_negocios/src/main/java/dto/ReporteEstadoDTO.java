/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 * aqui se agrupan transacciones por el estado que tienen
 * @author keppler
 */
public class ReporteEstadoDTO {
 private String estado;
    private int totalTransacciones;
    private double montoPromedio;

    public ReporteEstadoDTO() {
    }

    public ReporteEstadoDTO(String estado, int totalTransacciones, double montoPromedio) {
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

    @Override
    public String toString() {
        return "ReporteEstado{"
                + "estado='" + estado + '\''
                + ", totalTransacciones=" + totalTransacciones
                + ", montoPromedio=" + montoPromedio
                + '}';
    }
}