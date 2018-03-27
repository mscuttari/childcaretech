package main.java.server.rmi;

import main.java.LogUtils;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServer implements RmiServerInterface {

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
    public RmiServer(String address, int port) {
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

}
