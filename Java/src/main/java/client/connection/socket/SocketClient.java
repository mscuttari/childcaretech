package main.java.client.connection.socket;

import main.java.LogUtils;
import main.java.client.connection.ClientInterface;

import java.io.IOException;
import java.net.Socket;

public class SocketClient implements ClientInterface {

    // Debug
    private static final String TAG = "SocketClient";

    // Configuration
    private String host;
    private int port;

    // Connection
    private Socket socket;


    /**
     * Constructor
     *
     * @param   host    String      server address
     * @param   port    int         server port
     */
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    public void start() {
        try {
            socket = new Socket(host, port);
            LogUtils.d(TAG, "Connection established");
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    @Override
    public void close() {

    }

}
