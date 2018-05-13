package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TripAdministrationController implements Initializable{

    // Debug
    private static final String TAG = "TripAdministrationController";

    @FXML private Pane tripAdministrationPane;
    @FXML private Button buttonAddTrip;
    @FXML private Button buttonShowTrip;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonAddTrip.setOnAction(event -> addTrip());

        //Show trip button
        buttonShowTrip.setOnAction(event -> showTrip());
    }

    public void addTrip() {
        try {
            Pane addTripPane = FXMLLoader.load(getClass().getResource("/views/addTrip.fxml"));
            BorderPane homePane = (BorderPane) tripAdministrationPane.getParent();
            homePane.setCenter(addTripPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void showTrip() {
        try {
            TableView tableTrips = FXMLLoader.load(getClass().getResource("/views/showTrip.fxml"));
            BorderPane homePane = (BorderPane) tripAdministrationPane.getParent();
            homePane.setCenter(tableTrips);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}