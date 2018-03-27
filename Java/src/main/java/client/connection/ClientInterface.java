package main.java.client.connection;

public interface ClientInterface {

    boolean isConnected();
    void start();
    void close();
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    boolean login(String username, String password);

}
