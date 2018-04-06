package main.java.client.connection.socket;

import main.java.LogUtils;
import main.java.client.connection.BaseClient;
import main.java.client.connection.ClientInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient extends BaseClient implements ClientInterface {

    // Debug
    private transient static final String TAG = "SocketClient";

    // Configuration
    private transient String host;
    private transient int port;

    // Connection
    private transient Socket socket;


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


    /** {@inheritDoc} */
    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }


    /** {@inheritDoc} */
    @Override
    public void start() {
        try {
            socket = new Socket(host, port);
            LogUtils.d(TAG, "Connection established");
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /** {@inheritDoc} */
    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean login(String username, String password) {
        checkConnection();

        setUsername(username);
        setPassword(password);

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            // Open streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Send credentials
            out.writeObject("login");
            out.writeObject(this);
            out.flush();

            // Get result
            boolean result = in.readBoolean();
            LogUtils.d(TAG, "Login result: " + result);
            return result;

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            closeStream(out);
            closeStream(in);
            close();
        }
    }


    /** {@inheritDoc} */
    private void checkConnection() {
        if (!isConnected()) {
            start();
        }
    }


    /**
     * Close stream
     *
     * @param   stream      input or output stream
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
