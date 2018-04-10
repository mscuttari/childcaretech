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


    /**
     * Create object in the database
     *
     * @param   obj     object to be created
     * @return  true if everything went fine; false otherwise
     */
    boolean create(Object obj);


    /**
     * Update object in the database
     *
     * @param   obj     object to be updated
     * @return  true if everything went fine; false otherwise
     */
    boolean update(Object obj);


    /**
     * Delete object from the database
     *
     * @param   obj     object to be deleted
     * @return  true if everything went fine; false otherwise
     */
    boolean delete(Object obj);

}
