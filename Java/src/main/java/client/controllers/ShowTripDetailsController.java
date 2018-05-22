package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.models.*;

import java.net.URL;
import java.util.*;

public class ShowTripDetailsController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowTripDetailsController";

    private Trip trip;

    @FXML private Pane showTripDetailsPane;
    @FXML private ImageView goBackImage;

    @FXML private Label labelTitle;
    @FXML private Label labelDate;
    @FXML private Label labelStaff;

    @FXML private Label labelPullman;
    @FXML private Label labelStops;

    @FXML private TextArea textAreaChildren;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showTripDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showTripDetailsPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

    }

    /**
     * Load the trip data into the corresponding fields
     */
    private void loadData() {
        labelTitle.setText(trip.getTitle());                                            // Title
        labelDate.setText(new java.sql.Date(trip.getDate().getTime()).toString());      // Date

        // Staff
        for (Staff current : trip.getStaff()) {
            labelStaff.setText(labelStaff.getText() + current.toString() + "\n");
        }

        // Children
        for(Child current: trip.getChildren()){
            textAreaChildren.appendText(current.toString());
            textAreaChildren.appendText("\n");
        }

        // Pullman
        for (Pullman current : trip.getTransports()) {
            labelPullman.setText(labelPullman.getText() + current.toString() + "\n");
        }

        // Stops
        for (Stop current : trip.getStops()) {
            labelStops.setText(labelStops.getText() + current.toString() + "\n");
        }

    }

    /**
     * Set the trip that is going to be shown
     *
     * @param   trip      trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        loadData();
    }

    /**
     * Go back to the main anagraphic page
     */
    public void goBack() {
        setCenterFXML((BorderPane)showTripDetailsPane.getParent(), "/views/showTrip.fxml");
    }

}