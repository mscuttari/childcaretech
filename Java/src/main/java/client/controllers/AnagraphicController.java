package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AnagraphicController extends AbstractController implements Initializable {

    @FXML private Pane pane;
    @FXML private Button buttonAddPerson;
    @FXML private Button buttonShowPerson;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonAddPerson.setOnAction(event -> addPerson());
        buttonShowPerson.setOnAction(event -> showPerson());
    }


    /**
     * Show the add person page
     */
    private void addPerson() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/addPerson.fxml");
    }


    /**
     * Show the list of all people
     */
    private void showPerson() {
        setCenterFXML((BorderPane)pane.getParent(), "/views/showPerson.fxml");
    }

}