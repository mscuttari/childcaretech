package main.java.server.rmi;

import main.java.LogUtils;
import main.java.client.connection.rmi.RmiClientInterface;
import main.java.server.Actions;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer extends UnicastRemoteObject implements RmiServerInterface {

    // Debug
    private static final String TAG = "RmiServer";

    // Internal
    private String address;
    private int port;


    /**
     * Constructor
     *
     * @param   address     String      address where the server will be reached
     * @param   port        int         server port
     */
    public RmiServer(String address, int port) throws RemoteException {
        this.address = address;
        this.port = port;
    }


    /**
     * Start RMI server
     */
    public void start() {
        try {
            LocateRegistry.createRegistry(port);
            Naming.rebind(address, this);
            LogUtils.d(TAG, "Ready");
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * Login
     *
     * @param   client      RmiClientInterface      client
     * @return  true if credentials are valid, false otherwise
     * @throws  RemoteException     in case of connection error
     */
    @Override
    public boolean login(RmiClientInterface client) throws RemoteException {
        return Actions.login(client.getUsername(), client.getPassword());
    }

}
