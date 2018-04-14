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
    @FXML private Button buttonFood;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Menu button
        buttonMenu.setOnAction(event -> menu());

        //Food button
        buttonFood.setOnAction(event -> food());
    }

    public void menu() {

    }

    public void food() {
        try {
            Pane foodPane = FXMLLoader.load(getClass().getResource("/views/food.fxml"));
            BorderPane homePane = (BorderPane) canteenPane.getParent();
            homePane.setCenter(foodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}