package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TripController extends AbstractController implements Initializable {

    @FXML private Pane pane;
    @FXML private Button buttonTripAdministration;
    @FXML private Button buttonSeatsAssignment;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Trips page button
        buttonTripAdministration.setOnAction(event -> tripAdministration());
        buttonTripAdministration.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonTripAdministration.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Seats assignment page
        buttonSeatsAssignment.setOnAction(event -> seatsAssignment());
        buttonSeatsAssignment.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonSeatsAssignment.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));
    }


    /**
     * Show trips administration page
     */
    private void tripAdministration() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/tripAdministration.fxml");
    }


    /**
     * Show seats assignment page
     */
    private void seatsAssignment() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/seatsAssignment.fxml");
    }

}