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
        return socket != null && !socket.isClosed() && socket.isConnected();
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
        setUsername(username);
        setPassword(password);

        return sendData("login", this);
    }


    /** {@inheritDoc} */
    @Override
    public boolean create(Object obj) {
        return sendData("create", this, obj);
    }


    /** {@inheritDoc} */
    @Override
    public boolean update(Object obj) {
        return sendData("update", this, obj);
    }


    /** {@inheritDoc} */
    @Override
    public boolean delete(Object obj) {
        return sendData("delete", this, obj);
    }


    /** {@inheritDoc} */
    private void checkConnection() {
        if (!isConnected()) {
            start();
        }
    }


    /**
     * Send data to socket server and get boolean result
     *
     * @param   data    data to be sent
     * @return  boolean result value
     */
    private boolean sendData(Object... data) {
        checkConnection();

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            // Open streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Send credentials
            for (Object obj : data)
                out.writeObject(obj);

            out.flush();

            // Get result
            return in.readBoolean();

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
