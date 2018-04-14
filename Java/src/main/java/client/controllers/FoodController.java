package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class FoodController implements Initializable{

    // Debug
    private static final String TAG = "FoodController";

    @FXML private Pane foodPane;
    @FXML private Button buttonAddFood;
    @FXML private Button buttonUpdateFood;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add food button
        buttonAddFood.setOnAction(event -> addFood());

        //Update food button
        buttonUpdateFood.setOnAction(event -> updateFood());
    }

    public void addFood() {

    }

    public void updateFood() {

    }

}