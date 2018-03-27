package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class home {

    private Stage actual;
    @FXML private Button anagrafica_b;
    @FXML private Button mensa_b;
    @FXML private Button gite_b;
    @FXML private ImageView login;


    public void anagrafica() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            actual = (Stage) anagrafica_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in anagrafica");
        }
    }

    public void mensa() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            actual = (Stage) mensa_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in mensa");
        }
    }

    public void gite() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/trips.fxml"));
            actual = (Stage) gite_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in gite");
        }
    }

    public void returnToLogin(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            actual = (Stage) login.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare al login");
        }
    }
}
