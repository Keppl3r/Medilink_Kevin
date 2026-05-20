package insertDatos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import conexion.MongoConection;
import daos.DoctorDAO;
import daos.PacienteDAO;
import objetosNegocio.Doctor;
import objetosNegocio.Paciente;

/**
 *
 * @author keppler
 */
public class InsertDatosAgendarCita {

    public static void main(String[] args) throws Exception {
        var db = MongoConection.obtenerBaseDatos();
        db.getCollection("doctores").drop();
        db.getCollection("pacientes").drop();

        DoctorDAO doctorDAO = new DoctorDAO();
        doctorDAO.guardar(new Doctor(10, "Dra. Althay Valle", "Cardiologia", 800.0, true));
        doctorDAO.guardar(new Doctor(11, "Dr. Carlos Ruiz", "Medicina General", 500.0, true));
        doctorDAO.guardar(new Doctor(12, "Dra. Nathalie Mendoza", "Pediatria", 650.0, true));
        doctorDAO.guardar(new Doctor(13, "Dr. No Disponible", "Neurologia", 900.0, false));

        PacienteDAO pacienteDAO = new PacienteDAO();
        pacienteDAO.guardar(new Paciente(1, "Kevin Mendoza",
                "kevin@gmail.com", "6441234567"));

        System.out.println("Datos de prueba insertados doctores y pacientes");
        System.exit(0);
    }
}