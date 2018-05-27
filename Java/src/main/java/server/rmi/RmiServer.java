package main.java.server.rmi;

import main.java.LogUtils;
import main.java.client.connection.rmi.RmiClientInterface;
import main.java.models.BaseModel;
import main.java.server.Actions;
import main.java.server.utils.HibernateUtils;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiServer extends UnicastRemoteObject implements RmiServerInterface {

    // Serialization
    private static final long serialVersionUID = -5584914910604367418L;

    // Debug
    private static final String TAG = "RmiServer";

    // Internal
    private String address;
    private int port;


    /**
     * Constructor
     *
     * @param   address     String      address where the server will be reached
     * @param   port        int         server port
     */
    public RmiServer(String address, int port) throws RemoteException {
        this.address = address;
        this.port = port;
    }


    /**
     * Start RMI server
     */
    public void start() {
        try {
            LocateRegistry.createRegistry(port);
            Naming.rebind(address, this);
            LogUtils.d(TAG, "Ready");
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /** {@inheritDoc} */
    @Override
    public boolean login(RmiClientInterface client) throws RemoteException {
        return Actions.login(client.getUsername(), client.getPassword());
    }


    /** {@inheritDoc} */
    @Override
    public String createChildId(RmiClientInterface client) throws RemoteException {
        if (!login(client)) return null;
        return Actions.createChildId();
    }


    /** {@inheritDoc} */
    @Override
    public <M extends BaseModel> List<M> getAll(RmiClientInterface client, Class<M> modelClass) throws RemoteException {
        if (!login(client)) return null;
        return HibernateUtils.getInstance().getAll(modelClass);
    }


    /** {@inheritDoc} */
    @Override
    public boolean create(RmiClientInterface client, BaseModel obj) throws RemoteException {
        return login(client) && HibernateUtils.getInstance().create(obj);
    }


    /** {@inheritDoc} */
    @Override
    public boolean update(RmiClientInterface client, BaseModel obj) throws RemoteException {
        return login(client) && HibernateUtils.getInstance().update(obj);
    }


    /** {@inheritDoc} */
    @Override
    public boolean delete(RmiClientInterface client, BaseModel obj) throws RemoteException {
        return login(client) && HibernateUtils.getInstance().delete(obj);
    }

}
