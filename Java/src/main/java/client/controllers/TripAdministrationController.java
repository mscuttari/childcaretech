package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TripAdministrationController extends AbstractController implements Initializable {

    @FXML private Pane pane;
    @FXML private Button buttonAddTrip;
    @FXML private Button buttonShowTrip;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add trip button
        buttonAddTrip.setOnAction(event -> addTrip());
        buttonAddTrip.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonAddTrip.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Show trip button
        buttonShowTrip.setOnAction(event -> showTrip());
        buttonShowTrip.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonShowTrip.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Go back button
        goBackImage.setOnMouseClicked(event -> goBack());
        goBackImage.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));
    }


    /**
     * Show the add trip page
     */
    private void addTrip() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/addTrip.fxml");
    }


    /**
     * Show the trips list page
     */
    private void showTrip() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/showTrip.fxml");
    }


    /**
     * Go back to the main trips page
     */
    public void goBack() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/trip.fxml");
    }

}