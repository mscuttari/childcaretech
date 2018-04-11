package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class AnagraphicController {

    private Stage actual;
    @FXML private ImageView home;
    @FXML private Button showP_b;
    @FXML private Button addP_b;

    public void showPerson() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/showPerson.fxml"));
            actual = (Stage) showP_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'visualizza persone'");
        }
    }

    public void addPerson() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/AddPersonController.fxml"));
            actual = (Stage) addP_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'aggiungi persone'");
        }
    }

    public void returnToHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/HomeController.fxml"));
            Stage actual = (Stage) home.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare alla HomeController");
        }
    }
}