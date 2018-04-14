package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    // Debug
    private static final String TAG = "MenuController";

    @FXML private Pane menuPane;
    @FXML private Button buttonAddMenu;
    @FXML private Button buttonUpdateMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add menu button
        buttonAddMenu.setOnAction(event -> addMenu());

        //Update menu button
        buttonUpdateMenu.setOnAction(event -> updateMenu());
    }

    public void addMenu() {

    }

    public void updateMenu() {

    }

}