package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CanteenController implements Initializable{

    // Debug
    private static final String TAG = "CanteenController";

    @FXML private Pane canteenPane;
    @FXML private Button buttonMenu;
    @FXML private Button buttonDish;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Menu button
        buttonMenu.setOnAction(event -> menu());

        //Dish button
        buttonDish.setOnAction(event -> dish());
    }

    public void menu() {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
            BorderPane homePane = (BorderPane) canteenPane.getParent();
            homePane.setCenter(menuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void dish() {
        try {
            Pane dishPane = FXMLLoader.load(getClass().getResource("/views/dish.fxml"));
            BorderPane homePane = (BorderPane) canteenPane.getParent();
            homePane.setCenter(dishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}