package main.java.client.connection;

import main.java.models.*;

import java.util.List;

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


    /**
     * Get username used for login
     *
     * @return  username
     */
    String getUsername();


    /**
     * Set the username to be used for login
     *
     * @param   username    username
     */
    void setUsername(String username);


    /**
     * Get password used for login
     *
     * @return  password
     */
    String getPassword();


    /**
     * Set the password to be used for login
     *
     * @param   password    password
     */
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
     * Create child ID
     *
     * @return  child ID
     */
    Long createChildId();


    /**
     * Create object in the database
     *
     * @param   obj     object to be created
     * @return  true if everything went fine; false otherwise
     */
    boolean create(BaseModel obj);


    /**
     * Update object in the database
     *
     * @param   obj     object to be updated
     * @return  true if everything went fine; false otherwise
     */
    boolean update(BaseModel obj);


    /**
     * Delete object from the database
     *
     * @param   obj     object to be deleted
     * @return  true if everything went fine; false otherwise
     */
    boolean delete(BaseModel obj);


    /**
     * Get people
     *
     * @return  list of people (null in case of error)
     */
    List<Person> getPeople();


    /**
     * Get children
     *
     * @return  list of children (null in case of error)
     */
    List<Child> getChildren();


    /**
     * Get contacts
     *
     * @return  list of contacts (null in case of error)
     */
    List<Contact> getContacts();


    /**
     * Get dish
     *
     * @return  list of dishes (null in case of error)
     */
    List<Dish> getDishes();


    /**
     * Get ingredients
     *
     * @return  list of ingredients (null in case of error)
     */
    List<Ingredient> getIngredients();


    /**
     * Get menus
     *
     * @return  list of menus (null in case of error)
     */
    List<Menu> getMenus();


    /**
     * Get parents
     *
     * @return  list of parents (null in case of error)
     */
    List<Parent> getParents();


    /**
     * Get providers
     *
     * @return  list of providers (null in case of error)
     */
    List<Provider> getProviders();


    /**
     * Get pullmans
     *
     * @return  list of pullmans (null in case of error)
     */
    List<Pullman> getPullmans();


    /**
     * Get pediatrists
     *
     * @return  list of pediatrists (null in case of error)
     */
    List<Pediatrist> getPediatrists();


    /**
     * Get staff
     *
     * @return  list of staff (null in case of error)
     */
    List<Staff> getStaff();


    /**
     * Get stops
     *
     * @return  list of stops (null in case of error)
     */
    List<Stop> getStops();


    /**
     * Get trips
     *
     * @return  list of trips (null in case of error)
     */
    List<Trip> getTrips();

}
