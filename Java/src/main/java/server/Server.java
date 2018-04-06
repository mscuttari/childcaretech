package main.java.server;

import main.java.LogUtils;
import main.java.client.Client;
import main.java.server.rmi.RmiServer;
import main.java.server.socket.SocketServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        try {
            // Socket server
            executorService.execute(() -> {
                try {
                    SocketServer socketServer = new SocketServer(1337);
                    socketServer.start();
                } catch (Exception e) {
                    LogUtils.e("ServerMain", "Failed to start socket server: " + e.getMessage());
                }
            });

            // RMI server
            executorService.execute(() -> {
                // RMI server
                try {
                    RmiServer rmiServer = new RmiServer("rmi://127.0.0.1/childcaretech", 1099);
                    rmiServer.start();
                } catch (Exception e) {
                    LogUtils.e("ServerMain", "Failed to start RMI server: " + e.getMessage());
                }
            });
        } finally {
            executorService.shutdown();
        }
    }

}
