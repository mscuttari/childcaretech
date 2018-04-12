package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CanteenController implements Initializable{

    // Debug
    private static final String TAG = "CanteenController";

    @FXML private Pane canteenPane;
    @FXML private Button buttonMenu;
    @FXML private Button buttonFood;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Menu button
        buttonMenu.setOnAction(event -> menu());

        //Food button
        buttonFood.setOnAction(event -> food());
    }

    public void menu() {

    }

    public void food() {

    }

}