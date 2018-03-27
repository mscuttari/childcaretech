package main.java.server;

import main.java.server.rmi.RmiServer;
import main.java.server.socket.SocketServer;

public class Server {

    public static void main(String[] args) {
        // socket server
        SocketServer socketServer = new SocketServer(1337);
        socketServer.startServer();

        // RMI server
        RmiServer rmiServer = new RmiServer("rmi://127.0.0.1/childcaretech", 1099);
        rmiServer.start();
    }

}
