package main.java.server.rmi;

import main.java.client.connection.rmi.RmiClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {

    /**
     * Login
     *
     * @param   client      RMI client
     * @return  true if credentials are valid, false otherwise
     * @throws  RemoteException in case of connection error
     */
    boolean login(RmiClientInterface client) throws RemoteException;


    /**
     * Create object in the database
     *
     * @param   client      RMI client
     * @param   obj         object to be created
     *
     * @return  true if everything went fine; false otherwise
     *
     * @throws  RemoteException in case of connection error
     */
    boolean create(RmiClientInterface client, Object obj) throws RemoteException;


    /**
     * Update object in the database
     *
     * @param   client      RMI client
     * @param   obj         object to be updated
     *
     * @return  true if everything went fine; false otherwise
     *
     * @throws  RemoteException in case of connection error
     */
    boolean update(RmiClientInterface client, Object obj) throws RemoteException;


    /**
     * Delete object from the database
     *
     * @param   client      RMI client
     * @param   obj         oject to be deleted
     *
     * @return  true if everything went fine; false otherwise
     *
     * @throws  RemoteException in case of connection error
     */
    boolean delete(RmiClientInterface client, Object obj) throws RemoteException;

}
