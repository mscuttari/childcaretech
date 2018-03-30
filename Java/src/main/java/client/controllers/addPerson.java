package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ListIterator;

public class addPerson {

    @FXML private MenuButton personChoice;
    @FXML private ImageView returnBack;
    @FXML private StackPane stackPane;
    @FXML private AnchorPane childrenPane;
    @FXML private AnchorPane parentsPane;

    @FXML
    private void initialize(){
        personChoice.setText("Bambino");
    }

    public void childrenChoice() {
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        childrenPane.setVisible(true);
    }

    public void parentsChoice(){
        for (Node current : stackPane.getChildren()) {
                current.setVisible(false);
            }
        parentsPane.setVisible(true);
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
