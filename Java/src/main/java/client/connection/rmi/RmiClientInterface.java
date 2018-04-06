package main.java.client.connection.rmi;

import java.rmi.Remote;

public interface RmiClientInterface extends Remote {

    String getUsername();
    String getPassword();

}
