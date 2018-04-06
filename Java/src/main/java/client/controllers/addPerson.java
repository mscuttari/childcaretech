package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class addPerson {

    @FXML private MenuButton personChoice;
    @FXML private ImageView returnBack;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane childrenPane;
    @FXML private AnchorPane parentsPane;
    @FXML private AnchorPane responsiblePane;
    @FXML private AnchorPane contactsPane;
    @FXML private AnchorPane pediatristPane;

    public void childrenChoice() {
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        childrenPane.setVisible(true);
        personChoice.setText("Bambino");
    }

    public void parentsChoice(){
        for (Node current : stackPane.getChildren()) {
                current.setVisible(false);
            }
        parentsPane.setVisible(true);
        personChoice.setText("Genitore");
    }

    public void responsibleChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        responsiblePane.setVisible(true);
        personChoice.setText("Personale");
    }

    public void contactsChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        contactsPane.setVisible(true);
        personChoice.setText("Contatto");
    }

    public void pediatristChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        pediatristPane.setVisible(true);
        personChoice.setText("Pediatra");
    }

    public void returnBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            Stage actual = (Stage) returnBack.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare indietro");
        }
    }

}
