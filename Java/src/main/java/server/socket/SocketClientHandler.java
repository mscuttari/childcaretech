package main.java.server.socket;

import main.java.LogUtils;
import main.java.client.connection.socket.SocketClient;
import main.java.models.BaseModel;
import main.java.server.Actions;
import main.java.utils.HibernateUtils;

import java.io.*;
import java.net.Socket;

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
                while(true) {
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
            out.writeBoolean(result);
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
            LogUtils.e(TAG, client.getUsername() + " : " + client.getPassword());
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
                out.writeBoolean(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().create(obj);
                out.writeBoolean(result);
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
                out.writeBoolean(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().update(obj);
                out.writeBoolean(result);
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
                out.writeBoolean(false);
                out.flush();
            } else {
                result = HibernateUtils.getInstance().delete(obj);
                out.writeBoolean(result);
                out.flush();
            }

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

}
