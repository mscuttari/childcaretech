package main.java.client.connection;

public enum ConnectionType {

    SOCKET("Socket"),
    RMI("RMI");

    private String name;

    ConnectionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
