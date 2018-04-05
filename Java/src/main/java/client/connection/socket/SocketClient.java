package main.java.client.connection.socket;

import main.java.LogUtils;
import main.java.client.connection.BaseClient;
import main.java.client.connection.ClientInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient extends BaseClient implements ClientInterface {

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


    /**
     * Check if the client is connected to the server
     *
     * @return  true if connected, false otherwise
     */
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }


    /**
     * Connect to socket server
     */
    @Override
    public void start() {
        try {
            socket = new Socket(host, port);
            LogUtils.d(TAG, "Connection established");
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * Close connection
     */
    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }
        }
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

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            // Open streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Send credentials
            out.writeObject("LoginController");
            out.writeObject(this);
            out.flush();

            // Get result
            return in.readBoolean();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            return false;

        } finally {
            closeStream(out);
            closeStream(in);
        }
    }


    /**
     * Check if the client is connected to the server and, if not connected, creates a new connection
     */
    private void checkConnection() {
        if (!isConnected()) {
            start();
        }
    }


    /**
     * Close stream
     *
     * @param   stream      Closeable       input or output stream
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                LogUtils.e(TAG, "Can't close stream: " + e.getMessage());
            }
        }
    }

}
