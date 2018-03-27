package main.java.server.socket;

import main.java.LogUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    // Debug
    private static final String TAG = "SocketServer";

    // Internal
    private int port;


    /**
     * Constructor
     *
     * @param   port    int     server port
     */
    public SocketServer(int port) {
        this.port = port;
    }


    /**
     * Start multi-thread socket server
     */
    public void startServer() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        LogUtils.d(TAG, "Ready");

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executorService.submit(new ClientHandler(socket));
                LogUtils.d(TAG, "Connection opened");
            } catch (IOException e) {
                LogUtils.d(TAG, "Connection closed");
                break;
            }
        }
    }

}
