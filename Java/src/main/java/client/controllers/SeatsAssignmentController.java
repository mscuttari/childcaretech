package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
    @FXML private ImageView goBackImage;
    @FXML private Button buttonShowSeats;
    @FXML private Button buttonInsertPresences;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonShowSeats.setOnAction(event -> showSeats());

        //Show trip button
        buttonInsertPresences.setOnAction(event -> insertPresences());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> seatsAssignmentPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> seatsAssignmentPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());
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
        try {
            Pane insertPresencesPane = FXMLLoader.load(getClass().getResource("/views/insertPresences.fxml"));
            BorderPane homePane = (BorderPane) seatsAssignmentPane.getParent();
            homePane.setCenter(insertPresencesPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void goBack() {
        try {
            Pane tripPane = FXMLLoader.load(getClass().getResource("/views/trip.fxml"));
            BorderPane homePane = (BorderPane) seatsAssignmentPane.getParent();
            homePane.setCenter(tripPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}