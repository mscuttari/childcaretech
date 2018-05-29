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

public class MenuController extends AbstractController implements Initializable{

    @FXML private Pane pane;
    @FXML private Button buttonAddMenu;
    @FXML private Button buttonShowMenu;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add menu button
        buttonAddMenu.setOnAction(event -> addMenu());
        buttonAddMenu.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonAddMenu.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Show menu button
        buttonShowMenu.setOnAction(event -> showMenu());
        buttonShowMenu.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonShowMenu.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Go back button
        goBackImage.setOnMouseClicked(event -> goBack());
        goBackImage.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));
    }


    /**
     * Show the add menu page
     */
    private void addMenu() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/addMenu.fxml");
    }


    /**
     * Show the menus list page
     */
    private void showMenu() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/showMenu.fxml");
    }


    /**
     * Go back to the main canteen page
     */
    public void goBack() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/canteen.fxml");
    }

}