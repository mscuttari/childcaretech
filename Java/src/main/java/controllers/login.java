package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class login {

    private Stage actual;
    @FXML private Label txtLabel;
    @FXML private TextField txtUserName;
    @FXML private TextField txtPassword;
    @FXML private Button login_b;

    public void login(){
        if(txtUserName.getText().equals("user") && txtPassword.getText().equals(("pass"))) {
            txtLabel.setText("Login Success");
            //Stage primaryStage = new Stage();
            //Parent root = FXMLLoader.load(getClass().getResource("first.fxml"));
            //primaryStage.setScene(new Scene(root, 300, 275));
            //primaryStage.show();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                actual = (Stage) login_b.getScene().getWindow();
                actual.setScene(new Scene(root, 600, 400));
                actual.show();
            } catch (IOException e) {

            }
        }
        else txtLabel.setText("Login Failed");

    }


}
