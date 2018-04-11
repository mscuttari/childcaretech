package main.java.client.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.connection.ConnectionType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    // Debug
    private static final String TAG = "LoginController";

    @FXML private AnchorPane rootPane;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<ConnectionType> cbConnectionType;
    @FXML private Button buttonLogin;
    @FXML private Label labelError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Remove default focus from the username field
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        txtUsername.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                rootPane.requestFocus();
                firstTime.setValue(false);
            }
        });

        // Connection type
        cbConnectionType.getItems().addAll(ConnectionType.values());
        cbConnectionType.getSelectionModel().selectFirst();

        // Login button
        buttonLogin.setOnAction(event -> login());

        // Submit form on enter key press
        EventHandler<KeyEvent> keyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                login();
        };

        txtUsername.setOnKeyPressed(keyPressEvent);
        txtPassword.setOnKeyPressed(keyPressEvent);
        buttonLogin.setOnKeyPressed(keyPressEvent);
    }


    /**
     * Login
     */
    public void login(){
        // Username
        String username = txtUsername.getText().trim();

        if (username.isEmpty()) {
            labelError.setText("Username non valido");
            return;
        }

        // Password
        String password = txtPassword.getText().trim();

        if (password.isEmpty()) {
            labelError.setText("Password non valida");
            return;
        }

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        ConnectionType connectionType = cbConnectionType.getValue();
        connectionManager.setConnectionType(connectionType);

        // Send request
        boolean loginResult = connectionManager.getClient().login(username, password);

        if (loginResult) {
            // Login successful
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                //root.getStylesheets().add(getClass().getResource("/ChildCareTech.css").toExternalForm());

                Stage stage = new Stage();
                stage.setTitle("Child Care Tech");
                stage.setScene(new Scene(root, 1000, 630));
                stage.setMinWidth(1000);
                stage.setMinHeight(630);
                stage.show();

                rootPane.getScene().getWindow().hide();
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }
        } else {
            // Login error
            labelError.setText("Credenziali non valide");
        }
    }

}
