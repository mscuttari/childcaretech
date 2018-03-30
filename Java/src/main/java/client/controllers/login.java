package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.java.client.connection.ClientInterface;
import main.java.client.connection.ConnectionManager;

import java.io.IOException;


public class login {

    private Stage actual;

    @FXML private Label txtLabel;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button login_b;
    @FXML private MenuButton connection_b;
    @FXML private MenuItem RMI_i;
    @FXML private MenuItem Socket_i;

    public void login(){
        if(txtUserName.getText().equals("user") && txtPassword.getText().equals(("pass"))) {
            txtLabel.setText("Login Success");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                actual = (Stage) login_b.getScene().getWindow();
                actual.setScene(new Scene(root, 1000, 630));
                actual.show();
            } catch (IOException e) {
                System.out.println("Errore nell'entrare nel 'login'");
            }
        }
        else txtLabel.setText("Login Failed");
    }

    /*
    public void login() {

        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String connection = connection_b.getText();
        ConnectionManager.ConnectionType ct;

        ConnectionManager connectionManager = ConnectionManager.getInstance();
        if (connection.equals("Tipo di connessione"))
            txtLabel.setText("Seleziona il tipo di connessione");
        else {
            if (connection.equals("Socket"))
                ct = ConnectionManager.ConnectionType.SOCKET;
            else
                ct = ConnectionManager.ConnectionType.RMI;

            connectionManager.setConnectionType(ct);
            ClientInterface client = connectionManager.getClient();
            boolean loginResult = client.login(username, password);

            if (loginResult) {
                txtLabel.setText("Login effettuato");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                    actual = (Stage) login_b.getScene().getWindow();
                    actual.setScene(new Scene(root, 1000, 630));
                    actual.show();
                } catch (IOException e) {
                    System.out.println("Errore nell'entrare nel 'login'");
                }
            } else txtLabel.setText("Login fallito");
        }
    }
    */

    public void setConnection_RMI(){

        connection_b.setText(RMI_i.getText());
    }

    public void setConnection_Socket(){

        connection_b.setText(Socket_i.getText());
    }

}
