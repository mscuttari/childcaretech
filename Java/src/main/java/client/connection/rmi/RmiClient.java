package main.java.client.connection.rmi;

import main.java.LogUtils;
import main.java.client.connection.BaseClient;
import main.java.client.connection.ClientInterface;
import main.java.models.*;
import main.java.server.rmi.RmiServerInterface;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class RmiClient extends BaseClient implements ClientInterface, RmiClientInterface {

    // Debug
    private static final String TAG = "RmiClient";

    // Configuration
    private String host;

    // Connection
    private RmiServerInterface server;


    /**
     * Constructor
     *
     * @param   host    server address
     */
    public RmiClient(String host) {
        this.host = host;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isConnected() {
        return server != null;
    }


    /** {@inheritDoc} */
    @Override
    public void start() {
        try {
            server = (RmiServerInterface)Naming.lookup(host);
        } catch(Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /** {@inheritDoc} */
    @Override
    public void close() {
        // No need to be implemented
    }


    /** {@inheritDoc} */
    @Override
    public boolean login(String username, String password) {
        setUsername(username);
        setPassword(password);

        try {
            return server.login(this);
        } catch (RemoteException e) {
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean create(BaseModel obj) {
        try {
            return server.create(this, obj);
        } catch (RemoteException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean update(BaseModel obj) {
        try {
            return server.update(this, obj);
        } catch (RemoteException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean delete(BaseModel obj) {
        try {
            return server.delete(this, obj);
        } catch (RemoteException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Get all elements of a specific class
     *
     * @param   modelClass      model class
     * @return  list of elements
     */
    private <M extends BaseModel> List<M> getAll(Class<M> modelClass) {
        try {
            return server.getAll(this, modelClass);
        } catch (RemoteException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    /** {@inheritDoc} */
    @Override
    public List<Person> getPeople() {
        return getAll(Person.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Child> getChildren() {
        return getAll(Child.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Contact> getContacts() {
        return getAll(Contact.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Dish> getDishes() {
        return getAll(Dish.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Ingredient> getIngredients() {
        return getAll(Ingredient.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Menu> getMenus() {
        return getAll(Menu.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<RegularMenu> getRegularMenus() {
        return getAll(RegularMenu.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Parent> getParents() {
        return getAll(Parent.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Provider> getProviders() {
        return getAll(Provider.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Pullman> getPullmans() {
        return getAll(Pullman.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Pediatrist> getPediatrists() {
        return getAll(Pediatrist.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Staff> getStaff() {
        return getAll(Staff.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Stop> getStops() {
        return getAll(Stop.class);
    }


    /** {@inheritDoc} */
    @Override
    public List<Trip> getTrips() {
        return getAll(Trip.class);
    }

}
