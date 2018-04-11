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

public class AnagraphicController implements Initializable{

    // Debug
    private static final String TAG = "AnagraphicController";

    @FXML private Pane anagraphicPane;
    @FXML private Button buttonAddPerson;
    @FXML private Button buttonUpdatePerson;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add person button
        buttonAddPerson.setOnAction(event -> addPerson());

        //Update person button
        buttonUpdatePerson.setOnAction(event -> updatePerson());
    }

    public void addPerson() {
        try {
            Pane addPersonPane = FXMLLoader.load(getClass().getResource("/views/addPerson.fxml"));
            BorderPane homePane = (BorderPane) anagraphicPane.getParent();
            homePane.setCenter(addPersonPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void updatePerson() {

    }

}