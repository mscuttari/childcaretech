package main.java.client.connection.rmi;

import main.java.client.connection.ClientInterface;

public class RmiClient implements ClientInterface {

    // Debug
    private static final String TAG = "RmiClient";

    // Internal
    private String host;
    private int port;


    /**
     * Constructor
     *
     * @param   host    String      server address
     * @param   port    int         server port
     */
    public RmiClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public void start() {

    }


    @Override
    public void close() {

    }

}
