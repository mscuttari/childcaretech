package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TripAdministrationController extends AbstractController implements Initializable {

    @FXML private Pane tripAdministrationPane;
    @FXML private Button buttonAddTrip;
    @FXML private Button buttonShowTrip;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add trip button
        buttonAddTrip.setOnAction(event -> addTrip());

        // Show trip button
        buttonShowTrip.setOnAction(event -> showTrip());

        // Go back button
        goBackImage.setOnMouseEntered(event -> tripAdministrationPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> tripAdministrationPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
    }


    /**
     * Show the add trip page
     */
    private void addTrip() {
        setCenterFXML((BorderPane)tripAdministrationPane.getParent(), "/views/addTrip.fxml");
    }


    /**
     * Show the trips list page
     */
    private void showTrip() {
        setCenterFXML((BorderPane)tripAdministrationPane.getParent(), "/views/showTrip.fxml");
    }


    /**
     * Go back to the main trips page
     */
    public void goBack() {
        setCenterFXML((BorderPane)tripAdministrationPane.getParent(), "/views/trip.fxml");
    }

}