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

public class canteen {

    private Stage actual;
    @FXML private ImageView home;
    @FXML private Button menu_b;
    @FXML private Button food_b;

    public void menu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
            actual = (Stage) menu_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'menù'");
        }
    }

    public void food() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/food.fxml"));
            actual = (Stage) food_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'piatti'");
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