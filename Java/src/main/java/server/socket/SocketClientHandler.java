package main.java.server.socket;

import main.java.client.connection.socket.SocketClient;
import main.java.server.Actions;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String command;

            do {
                command = in.readUTF();
            } while (parseCommand(command, in, out));

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
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
            case "login":
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
            Object clientObject = in.readObject();
            if (!(clientObject instanceof  SocketClient)) return;
            SocketClient client = (SocketClient)clientObject;
            boolean loginResult = Actions.login(client.getUsername(), client.getPassword());
            out.writeBoolean(loginResult);
            out.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
