package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class anagraphic {

    private Stage actual;
    @FXML private AnchorPane gitePane;
    @FXML private ImageView home;
    @FXML private Button visualizzaP_b;
    @FXML private Button aggiungiP_b;

    public void visualizzaPersone() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/showPerson.fxml"));
            actual = (Stage) visualizzaP_b.getScene().getWindow();
            actual.setScene(new Scene(root, 600, 400));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'visualizza persone'");
        }
    }

    public void aggiungiPersone() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/addPerson.fxml"));
            actual = (Stage) aggiungiP_b.getScene().getWindow();
            actual.setScene(new Scene(root, 600, 400));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'aggiungi persone'");
        }
    }

    public void returnToHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Stage actual = (Stage) home.getScene().getWindow();
            actual.setScene(new Scene(root, 600, 400));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare alla home");
        }
    }
}