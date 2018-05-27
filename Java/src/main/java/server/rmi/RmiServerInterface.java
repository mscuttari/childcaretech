package main.java.server.rmi;

import main.java.client.connection.rmi.RmiClientInterface;
import main.java.models.BaseModel;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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
     * Create child ID
     *
     * @param   client      RMI client
     * @return  child ID
     * @throws  RemoteException in case of connection error
     */
    String createChildId(RmiClientInterface client) throws RemoteException;


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
    boolean create(RmiClientInterface client, BaseModel obj) throws RemoteException;


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
    boolean update(RmiClientInterface client, BaseModel obj) throws RemoteException;


    /**
     * Delete object from the database
     *
     * @param   client      RMI client
     * @param   obj         object to be deleted
     *
     * @return  true if everything went fine; false otherwise
     *
     * @throws  RemoteException in case of connection error
     */
    boolean delete(RmiClientInterface client, BaseModel obj) throws RemoteException;


    /**
     * Get all the elements of a specific class
     *
     * @param   client          RMI client
     * @param   modelClass      model class (Child, Staff, Trip, etc.)
     *
     * @return  list of elements
     *
     * @throws  RemoteException in case of connection error
     */
    <M extends BaseModel> List<M> getAll(RmiClientInterface client, Class<M> modelClass) throws RemoteException;

}
