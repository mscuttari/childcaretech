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

public class DishController extends AbstractController implements Initializable {

    @FXML private Pane pane;
    @FXML private Button buttonAddDish;
    @FXML private Button buttonShowDish;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add dish button
        buttonAddDish.setOnAction(event -> addDish());
        goBackImage.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Update dish button
        buttonShowDish.setOnAction(event -> showDish());
        goBackImage.setOnMouseClicked(event -> goBack());

    }


    /**
     * Show the add dish page
     */
    private void addDish() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/addDish.fxml");
    }


    /**
     * Show the dishes list page
     */
    private void showDish() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/showDish.fxml");
    }


    /**
     * Go back to the main canteen page
     */
    private void goBack() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/canteen.fxml");
    }

}