/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * es una coleccion en Mongo
 * @author keppler
 */
public class Doctor {

    private Integer id;
    private String nombre;
    private String especialidad;
    private Double costoConsulta;

    public Doctor() {
    }

    public Doctor(Integer id, String nombre, String especialidad, Double costoConsulta) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.costoConsulta = costoConsulta;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getCostoConsulta() {
        return costoConsulta;
    }

    public void setCostoConsulta(Double costoConsulta) {
        this.costoConsulta = costoConsulta;
    }
}
