/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 * Clase dominio que representa a un paciente asociado a una transacción. 
 * @author keppler
 */
    public class Paciente {

        private Integer id;
        private String nombre;

        public Paciente() {
        }

        public Paciente(Integer id, String nombre) {
            this.id = id;
            this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Paciente{" + "id=" + id + ", nombre=" + nombre + '}';
    }
        
        
    }

