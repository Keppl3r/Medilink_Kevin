/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author keppler
 */
public class Servicio {

    private Integer id;
    private String nombre;
    private Double montoEsperado;
    private String descripcion;

    public Servicio() {
    }

    public Servicio(Integer id, String nombre, Double montoEsperado, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.montoEsperado = montoEsperado;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getMontoEsperado() {
        return montoEsperado;
    }

    public void setMontoEsperado(Double montoEsperado) {
        this.montoEsperado = montoEsperado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Servicio{" + "id=" + id + ", nombre=" + nombre + ", montoEsperado=" + montoEsperado + ", descripcion=" + descripcion + '}';
    }
    
    
}
