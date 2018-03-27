package main.java.client.connection;

import main.java.client.connection.rmi.RmiClient;
import main.java.client.connection.socket.SocketClient;

public final class ConnectionManager {

    // Socket
    private static final String socketHost = "localhost";
    private static final int socketPort = 1337;

    // RMI
    private static final String rmiHost = "rmi://localhost/childcaretech";
    private static final int rmiPort = 1099;

    // Connection type
    public enum ConnectionType {SOCKET, RMI}
    private ConnectionType connectionType;
    private ClientInterface client;

    // Singleton
    private static ConnectionManager instance = null;

    private ConnectionManager() {

    }


    /**
     * Get instance
     *
     * @return  ConnectionManager       singleton instance
     */
    public ConnectionManager getInstance(ConnectionType connectionType) {
        if (instance == null)
            instance = new ConnectionManager();

        return instance;
    }


    /**
     * Set connection type
     *
     * @param   connectionType      ConnectionType      connection type (socket or RMI)
     */
    public void setConnectionType(ConnectionType connectionType) {
        if (this.connectionType != connectionType && client != null) {
            client.close();
            client = null;
        }

        this.connectionType = connectionType;

        if (client == null)
            clientSetup();
    }


    /**
     * Create client according to the connection type set
     */
    private void clientSetup() {
        switch (connectionType) {
            case SOCKET:
                client = new SocketClient(socketHost, socketPort);
                break;

            case RMI:
                client = new RmiClient(rmiHost, rmiPort);
                break;
        }

        client.start();
    }


    /**
     * Get client
     *
     * @return  ClientInterface     client (socket or RMI client)
     */
    public ClientInterface getClient() {
        return client;
    }

}