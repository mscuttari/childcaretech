package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TripController implements Initializable{

    // Debug
    private static final String TAG = "TripController";

    @FXML private Pane tripPane;
    @FXML private Button buttonAddTrip;
    @FXML private Button buttonUpdateTrip;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonAddTrip.setOnAction(event -> addTrip());

        //Update trip button
        buttonUpdateTrip.setOnAction(event -> updateTrip());
    }

    public void addTrip() {

    }

    public void updateTrip() {

    }

}