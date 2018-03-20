package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class showPerson {

    @FXML private ImageView returnBack;

    public void returnBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            Stage actual = (Stage) returnBack.getScene().getWindow();
            actual.setScene(new Scene(root, 600, 400));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare indietro");
        }
    }
}
