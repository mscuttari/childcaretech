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
    @FXML private Label labelChildren;
    @FXML private Label labelPullmans;
    @FXML private Label labelStops;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showTripDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showTripDetailsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
    }


    /**
     * Load the trip data into the corresponding fields
     */
    private void loadData() {
        String separator;

        labelTitle.setText(trip.getTitle());                                            // Title
        labelDate.setText(new java.sql.Date(trip.getDate().getTime()).toString());      // Date

        // Staff
        if (trip.getStaff().isEmpty()) {
            labelStaff.setText("-");
        } else {
            separator = "";
            for (Staff staff : trip.getStaff()) {
                labelStaff.setText(labelStaff.getText() + separator + staff.toString());
                separator = "\n";
            }
        }

        // Children
        if (trip.getStaff().isEmpty()) {
            labelChildren.setText("-");
        } else {
            separator = "";
            for (Child child : trip.getChildren()) {
                labelChildren.setText(labelChildren.getText() + separator + child);
                separator = "\n";
            }
        }

        // Pullman
        if (trip.getPullmans().isEmpty()) {
            labelPullmans.setText("-");
        } else {
            separator = "";
            for (Pullman pullman : trip.getPullmans()) {
                labelPullmans.setText(labelPullmans.getText() + separator + pullman.toString());
                separator = "\n";
            }
        }

        // Stops
        if (trip.getStops().isEmpty()) {
            labelStops.setText("-");
        } else {
            List<Stop> stops = new ArrayList<>(trip.getStops());
            stops.sort(Comparator.comparing(Stop::getNumber));

            separator = "";
            for (Stop stop : stops) {
                labelStops.setText(labelStops.getText() + separator + stop.toString());
                separator = "\n";
            }
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