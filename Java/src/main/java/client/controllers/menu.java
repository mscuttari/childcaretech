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

public class menu {

    private Stage actual;
    @FXML private ImageView canteen;
    @FXML private Button showM_b;
    @FXML private Button addM_b;

    public void showMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/showMenu.fxml"));
            actual = (Stage) showM_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'visualizza persone'");
        }
    }

    public void addMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/addMenu.fxml"));
            actual = (Stage) addM_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in 'aggiungi persone'");
        }
    }

    public void returnToCanteen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            Stage actual = (Stage) canteen.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare alla HomeController");
        }
    }
}