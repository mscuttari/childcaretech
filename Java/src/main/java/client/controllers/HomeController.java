package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    @FXML private BorderPane borderPane;
    @FXML private Button buttonAnagraphic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Login button
        buttonAnagraphic.setOnAction(event -> goToAnagraphic());
    }

    public void goToAnagraphic(){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/views/addPerson.fxml"));
            borderPane.setCenter(newPane);
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in anagrafica");
        }

    }
}
