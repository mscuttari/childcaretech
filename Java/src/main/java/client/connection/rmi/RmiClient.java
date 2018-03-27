package main.java.client.connection.rmi;

import main.java.LogUtils;
import main.java.client.connection.BaseClient;
import main.java.client.connection.ClientInterface;
import main.java.server.rmi.RmiServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RmiClient extends BaseClient implements ClientInterface, RmiClientInterface {

    // Debug
    private static final String TAG = "RmiClient";

    // Configuration
    private String host;

    // Connection
    private RmiServerInterface server;


    /**
     * Constructor
     *
     * @param   host    String      server address
     */
    public RmiClient(String host) {
        this.host = host;
    }


    /**
     * Check if the client is connected to the server
     *
     * @return  true if connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        return server != null;
    }


    /**
     * Connect to RMI server
     */
    @Override
    public void start() {
        try {
            server = (RmiServerInterface)Naming.lookup(host);
        } catch(Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * Close connection
     */
    @Override
    public void close() {
        // No need to be implemented
    }


    /**
     * Login
     *
     * @param   username    String      username
     * @param   password    String      password
     *
     * @return  true if credentials are valid, false otherwise
     */
    @Override
    public boolean login(String username, String password) {
        setUsername(username);
        setPassword(password);

        try {
            return server.login(this);
        } catch (RemoteException e) {
            return false;
        }
    }

}
