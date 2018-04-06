package main.java.client.connection;

public interface ClientInterface {

    /**
     * Check if the client is connected to the server
     *
     * @return  true if connected, false otherwise
     */
    boolean isConnected();


    /** Connect to server */
    void start();


    /** Close connection */
    void close();


    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);


    /**
     * Login
     *
     * @param   username    username
     * @param   password    password
     *
     * @return  true if credentials are valid, false otherwise
     */
    boolean login(String username, String password);

}
