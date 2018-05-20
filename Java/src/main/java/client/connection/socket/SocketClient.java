package main.java.client.connection.socket;

import main.java.LogUtils;
import main.java.client.connection.BaseClient;
import main.java.client.connection.ClientInterface;
import main.java.models.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketClient extends BaseClient implements ClientInterface {

    // Debug
    private transient static final String TAG = "SocketClient";

    // Configuration
    private transient String host;
    private transient int port;

    // Connection
    private transient Socket socket;


    /**
     * Constructor
     *
     * @param   host    String      server address
     * @param   port    int         server port
     */
    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isConnected() {
        return socket != null && !socket.isClosed() && socket.isConnected();
    }


    /** {@inheritDoc} */
    @Override
    public void start() {
        try {
            socket = new Socket(host, port);
            LogUtils.d(TAG, "Connection established");
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /** {@inheritDoc} */
    @Override
    public void close() {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean login(String username, String password) {
        setUsername(username);
        setPassword(password);

        List<Child> children = getChildren();
        LogUtils.e(TAG, "Children: " + children);

        Object result = sendData("login", this);
        return result instanceof Boolean && (boolean)result;
    }


    /** {@inheritDoc} */
    @Override
    public boolean create(BaseModel obj) {
        Object result = sendData("create", this, obj);
        return result instanceof Boolean && (boolean)result;
    }


    /** {@inheritDoc} */
    @Override
    public boolean update(BaseModel obj) {
        Object result = sendData("update", this, obj);
        return result instanceof Boolean && (boolean)result;
    }


    /** {@inheritDoc} */
    @Override
    public boolean delete(BaseModel obj) {
        Object result = sendData("delete", this, obj);
        return result instanceof Boolean && (boolean)result;
    }


    /** {@inheritDoc} */
    @Override
    public List<Person> getPeople() {
        Object result = sendData("get_people", this);
        //noinspection unchecked
        return result instanceof List ? (List<Person>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Child> getChildren() {
        Object result = sendData("get_children", this);
        //noinspection unchecked
        return result instanceof List ? (List<Child>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Contact> getContacts() {
        Object result = sendData("get_contacts", this);
        //noinspection unchecked
        return result instanceof List ? (List<Contact>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Dish> getDishes() {
        Object result = sendData("get_dishes", this);
        //noinspection unchecked
        return result instanceof List ? (List<Dish>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Ingredient> getIngredients() {
        Object result = sendData("get_ingredients", this);
        //noinspection unchecked
        return result instanceof List ? (List<Ingredient>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Menu> getMenus() {
        Object result = sendData("get_menus", this);
        //noinspection unchecked
        return result instanceof List ? (List<Menu>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<RegularMenu> getRegularMenus() {
        Object result = sendData("get_regular_menus", this);
        //noinspection unchecked
        return result instanceof List ? (List<RegularMenu>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Parent> getParents() {
        Object result = sendData("get_parents", this);
        //noinspection unchecked
        return result instanceof List ? (List<Parent>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Provider> getProviders() {
        Object result = sendData("get_providers", this);
        //noinspection unchecked
        return result instanceof List ? (List<Provider>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Pullman> getPullmans() {
        Object result = sendData("get_pullmans", this);
        //noinspection unchecked
        return result instanceof List ? (List<Pullman>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Pediatrist> getPediatrists() {
        Object result = sendData("get_pediatrists", this);
        //noinspection unchecked
        return result instanceof List ? (List<Pediatrist>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Staff> getStaff() {
        Object result = sendData("get_staff", this);
        //noinspection unchecked
        return result instanceof List ? (List<Staff>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Stop> getStops() {
        Object result = sendData("get_stops", this);
        //noinspection unchecked
        return result instanceof List ? (List<Stop>)result : null;
    }


    /** {@inheritDoc} */
    @Override
    public List<Trip> getTrips() {
        Object result = sendData("get_trips", this);
        //noinspection unchecked
        return result instanceof List ? (List<Trip>)result : null;
    }


    /**
     * Check if the connection is established
     */
    private void checkConnection() {
        if (!isConnected()) {
            start();
        }
    }


    /**
     * Send data to socket server and get result
     *
     * @param   data    data to be sent
     * @return  object result value
     */
    private Object sendData(Object... data) {
        checkConnection();

        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            // Open streams
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Send credentials
            for (Object obj : data)
                out.writeObject(obj);

            out.flush();

            // Get result
            return in.readObject();

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            closeStream(out);
            closeStream(in);
            close();
        }
    }


    /**
     * Close stream
     *
     * @param   stream      input or output stream
     */
    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                LogUtils.e(TAG, "Can't close stream: " + e.getMessage());
            }
        }
    }

}
