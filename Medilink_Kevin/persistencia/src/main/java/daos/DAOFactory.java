/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import interfaces.ITransaccionDAO;

/**
 * Fábrica singleton para obtener instancias de los DAOs.
 *
 * @author keppler
 */
public class DAOFactory {

    private static DAOFactory instancia;

    private DAOFactory() {
    }

    public static DAOFactory getInstancia() {
        if (instancia == null) {
            instancia = new DAOFactory();
        }
        return instancia;
    }

    public ITransaccionDAO getTransaccionDAO() {
        return new TransaccionDAO();
    }
}
