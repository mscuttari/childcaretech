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

public class MenuController implements Initializable{

    // Debug
    private static final String TAG = "MenuController";

    @FXML private Pane menuPane;
    @FXML private Button buttonAddMenu;
    @FXML private Button buttonShowMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add menu button
        buttonAddMenu.setOnAction(event -> addMenu());

        //Show menu button
        buttonShowMenu.setOnAction(event -> showMenu());
    }

    public void addMenu() {
        try {
            Pane addMenuPane = FXMLLoader.load(getClass().getResource("/views/addMenu.fxml"));
            BorderPane homePane = (BorderPane) menuPane.getParent();
            homePane.setCenter(addMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void showMenu() {
        try {
            Pane showMenuPane = FXMLLoader.load(getClass().getResource("/views/showMenu.fxml"));
            BorderPane homePane = (BorderPane) menuPane.getParent();
            homePane.setCenter(showMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}