package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TripController implements Initializable{

    // Debug
    private static final String TAG = "TripController";

    @FXML private Pane tripPane;
    @FXML private Button buttonTripAdministration;
    @FXML private Button buttonSeatsAssignment;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonTripAdministration.setOnAction(event -> tripAdministration());

        //Update trip button
        buttonSeatsAssignment.setOnAction(event -> seatsAssignment());
    }

    public void tripAdministration() {
        try {
            Pane tripAdministrationPane = FXMLLoader.load(getClass().getResource("/views/tripAdministration.fxml"));
            BorderPane homePane = (BorderPane) tripPane.getParent();
            homePane.setCenter(tripAdministrationPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

    }

    public void seatsAssignment() {
        try {
            Pane seatsAssignmentPane = FXMLLoader.load(getClass().getResource("/views/seatsAssignment.fxml"));
            BorderPane homePane = (BorderPane) tripPane.getParent();
            homePane.setCenter(seatsAssignmentPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}