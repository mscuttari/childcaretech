package main.java.server.socket;

import main.java.LogUtils;
import main.java.client.connection.socket.SocketClient;
import main.java.server.Actions;

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
                do {
                    commandObject = in.readObject();

                    if (!(commandObject instanceof String)) {
                        LogUtils.e(TAG, "Invalid command object");
                        break;
                    }

                    command = (String)commandObject;
                } while (parseCommand(command, in, out));

            } catch (ClassNotFoundException e) {
                LogUtils.e(TAG, "Invalid command object: " + e.getMessage());
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * Parse input command
     *
     * @param   command     String                  command
     * @param   in          ObjectInputStream       input stream
     * @param   out         ObjectOutputStream      output stream
     *
     * @return  boolean     true if the connection must be kept open; false if it must be closed
     */
    private boolean parseCommand(String command, ObjectInputStream in, ObjectOutputStream out) {
        switch (command) {
            case "LoginController":
                LogUtils.d(TAG, "User is logging in");
                login(in, out);
                return true;

            default:
                return false;
        }
    }


    /**
     * Login
     *
     * @param   in      ObjectInputStream       input stream
     * @param   out     ObjectOutputStream      output stream
     */
    private void login(ObjectInputStream in, ObjectOutputStream out) {
        try {
            // Get client object
            Object clientObject = in.readObject();
            if (!(clientObject instanceof  SocketClient)) return;
            SocketClient client = (SocketClient)clientObject;

            // Login
            boolean loginResult = Actions.login(client.getUsername(), client.getPassword());
            out.writeBoolean(loginResult);
            out.flush();
            LogUtils.d(TAG, client.getUsername() + " logged in");

        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}
