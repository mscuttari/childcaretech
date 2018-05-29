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

public class SeatsAssignmentController extends AbstractController implements Initializable{

    @FXML private Pane pane;
    @FXML private ImageView goBackImage;
    @FXML private Button buttonShowSeats;
    @FXML private Button buttonInsertPresences;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add trip button
        buttonShowSeats.setOnAction(event -> showSeats());
        buttonShowSeats.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonShowSeats.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Show trip button
        buttonInsertPresences.setOnAction(event -> insertPresences());
        buttonInsertPresences.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonInsertPresences.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Go back button
        goBackImage.setOnMouseClicked(event -> goBack());
        goBackImage.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));
    }


    /**
     * Show the seats page
     */
    private void showSeats() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/showSeats.fxml");
    }


    /**
     * Show the insert presences page
     */
    private void insertPresences() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/insertPresences.fxml");
    }


    /**
     * Go back to the main trips page
     */
    public void goBack() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/trip.fxml");
    }

}