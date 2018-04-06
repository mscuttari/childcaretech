package main.java.server.rmi;

import main.java.client.connection.rmi.RmiClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {

    boolean login(RmiClientInterface client) throws RemoteException;

}
