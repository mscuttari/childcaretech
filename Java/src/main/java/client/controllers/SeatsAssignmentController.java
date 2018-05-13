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

public class SeatsAssignmentController implements Initializable{

    // Debug
    private static final String TAG = "SeatsAssignmentController";

    @FXML private Pane seatsAssignmentPane;
    @FXML private Button buttonShowSeats;
    @FXML private Button buttonInsertPresences;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonShowSeats.setOnAction(event -> showSeats());

        //Show trip button
        buttonInsertPresences.setOnAction(event -> insertPresences());
    }

    public void showSeats() {
        try {
            Pane showSeatsPane = FXMLLoader.load(getClass().getResource("/views/showSeats.fxml"));
            BorderPane homePane = (BorderPane) seatsAssignmentPane.getParent();
            homePane.setCenter(showSeatsPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void insertPresences() {
        
    }

}