package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CanteenController extends AbstractController implements Initializable {

    @FXML private Pane pane;
    @FXML private Button buttonMenu;
    @FXML private Button buttonDish;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Menu page button
        buttonMenu.setOnAction(event -> showMenusPage());
        buttonMenu.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonMenu.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));

        // Dishes page button
        buttonDish.setOnAction(event -> showDishesPage());
        buttonDish.setOnMouseEntered(event -> pane.getScene().setCursor(Cursor.HAND));
        buttonDish.setOnMouseExited(event -> pane.getScene().setCursor(Cursor.DEFAULT));
    }


    /**
     * Show the menus page
     */
    private void showMenusPage() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/menu.fxml");
    }


    /**
     * Show the dishes page
     */
    private void showDishesPage() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/dish.fxml");
    }

}