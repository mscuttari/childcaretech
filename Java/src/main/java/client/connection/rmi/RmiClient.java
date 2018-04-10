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
     * @param   host    server address
     */
    public RmiClient(String host) {
        this.host = host;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isConnected() {
        return server != null;
    }


    /** {@inheritDoc} */
    @Override
    public void start() {
        try {
            server = (RmiServerInterface)Naming.lookup(host);
        } catch(Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /** {@inheritDoc} */
    @Override
    public void close() {
        // No need to be implemented
    }


    /** {@inheritDoc} */
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


    /** {@inheritDoc} */
    @Override
    public boolean create(Object obj) {
        try {
            server.create(this, obj);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean update(Object obj) {
        try {
            server.update(this, obj);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean delete(Object obj) {
        try {
            server.delete(this, obj);
            return true;
        } catch (RemoteException e) {
            return false;
        }
    }

}
