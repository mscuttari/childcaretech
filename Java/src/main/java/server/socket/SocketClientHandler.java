package main.java.server.socket;

import main.java.LogUtils;
import main.java.client.connection.socket.SocketClient;
import main.java.models.*;
import main.java.server.Actions;
import main.java.server.utils.HibernateUtils;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketClientHandler implements Runnable {

    // Debug
    private static final String TAG = "SocketClientHandler";

    private Socket socket;

    public SocketClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Object commandObject;
            String command;

            try {
                while (true) {
                    commandObject = in.readObject();

                    if (!(commandObject instanceof String)) {
                        LogUtils.e(TAG, "Invalid command object");
                        break;
                    }

                    command = (String) commandObject;
                    parseCommand(command, in, out);
                }
            } catch (EOFException e) {
                LogUtils.d(TAG, "Connection closed");
            } catch (ClassNotFoundException e) {
                LogUtils.e(TAG, "Invalid command object: " + e.getMessage());
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Parse input command
     *
     * @param   command     String                  command
     * @param   in          ObjectInputStream       input stream
     * @param   out         ObjectOutputStream      output stream
     */
    private void parseCommand(String command, ObjectInputStream in, ObjectOutputStream out) {
        switch (command) {
            case "login":
                login(in, out);
                break;

            case "create":
                create(in, out);
                break;

            case "update":
                update(in, out);
                break;

            case "delete":
                delete(in, out);
                break;

            case "get_children":
                getChildren(in, out);
                break;

            case "get_contacts":
                getContacts(in, out);
                break;

            case "get_food":
                getFood(in, out);
                break;

            case "get_ingredients":
                getIngredients(in, out);
                break;

            case "get_menus":
                getMenus(in, out);
                break;

            case "get_parents":
                getParents(in, out);
                break;

            case "get_providers":
                getProviders(in, out);
                break;

            case "get_pullmans":
                getPullmans(in, out);
                break;

            case "get_pediatrists":
                getPediatrists(in, out);
                break;

            case "get_staff":
                getStaff(in, out);
                break;

            case "get_stops":
                getStops(in, out);
                break;

            case "get_trips":
                getTrips(in, out);
                break;
        }
    }


    /**
     * Login
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void login(ObjectInputStream in, ObjectOutputStream out) {
        try {
            boolean result = checkCredentials(in);
            out.writeObject(result);
            out.flush();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Check if credentials are valid
     *
     * @param   in      object input stream
     * @return  true if everything went fine; false otherwise
     */
    private boolean checkCredentials(ObjectInputStream in) {
        try {
            // Get client object
            Object clientObject = in.readObject();
            if (!(clientObject instanceof SocketClient)) return false;
            SocketClient client = (SocketClient)clientObject;

            // Login
            return Actions.login(client.getUsername(), client.getPassword());

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Create object in the database
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void create(ObjectInputStream in, ObjectOutputStream out) {
        boolean result = checkCredentials(in);

        try {
            Object obj = in.readObject();

            if (!result || !(obj instanceof BaseModel)) {
                out.writeObject(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().create((BaseModel)obj);
                out.writeObject(result);
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Update object in the database
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void update(ObjectInputStream in, ObjectOutputStream out) {
        boolean result = checkCredentials(in);

        try {
            Object obj = in.readObject();

            if (!result || !(obj instanceof BaseModel)) {
                out.writeObject(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().update((BaseModel)obj);
                out.writeObject(result);
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Delete object from the database
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void delete(ObjectInputStream in, ObjectOutputStream out) {
        boolean result = checkCredentials(in);

        try {
            Object obj = in.readObject();

            if (!result || !(obj instanceof BaseModel)) {
                out.writeObject(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().delete((BaseModel)obj);
                out.writeObject(result);
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Get all the elements of a specific class
     *
     * @param   in              object input stream
     * @param   out             object output stream
     * @param   modelClass      model class
     */
    private <M extends BaseModel> void getAll(ObjectInputStream in, ObjectOutputStream out, Class<M> modelClass) {
        boolean logged = checkCredentials(in);

        try {
            if (!logged) {
                out.writeObject(null);
                out.flush();
            } else {
                List<M> result = HibernateUtils.getInstance().getAll(modelClass);
                LogUtils.e(TAG, "Lista result: " + result);
                out.writeObject(result);
                out.flush();
            }

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Get children
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getChildren(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Child.class);
    }


    /**
     * Get contacts
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getContacts(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Contact.class);
    }


    /**
     * Get food
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getFood(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Food.class);
    }


    /**
     * Get ingredients
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getIngredients(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Ingredient.class);
    }


    /**
     * Get menus
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getMenus(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Menu.class);
    }


    /**
     * Get parents
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getParents(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Parent.class);
    }


    /**
     * Get providers
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getProviders(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Provider.class);
    }


    /**
     * Get pullmans
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getPullmans(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Pullman.class);
    }


    /**
     * Get pediatrists
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getPediatrists(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Pediatrist.class);
    }


    /**
     * Get staff
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getStaff(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Staff.class);
    }


    /**
     * Get stops
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getStops(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Stop.class);
    }


    /**
     * Get trips
     *
     * @param   in      object input stream
     * @param   out     object output stream
     */
    private void getTrips(ObjectInputStream in, ObjectOutputStream out) {
        getAll(in, out, Trip.class);
    }

}
