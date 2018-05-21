package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TripController extends AbstractController implements Initializable {

    @FXML private Pane tripPane;
    @FXML private Button buttonTripAdministration;
    @FXML private Button buttonSeatsAssignment;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonTripAdministration.setOnAction(event -> tripAdministration());    // Add trip
        buttonSeatsAssignment.setOnAction(event -> seatsAssignment());          // Update trip
    }


    /**
     * Show trips administration page
     */
    private void tripAdministration() {
        setCenterFXML((BorderPane)tripPane.getParent(), "/views/tripAdministration.fxml");
    }


    /**
     * Show seats assignment page
     */
    private void seatsAssignment() {
        setCenterFXML((BorderPane)tripPane.getParent(), "/views/seatsAssignment.fxml");
    }

}