package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    private Stage actual;
    @FXML private Button anagraphic_b;
    @FXML private Button canteen_b;
    @FXML private Button trip_b;
    @FXML private ImageView login;


    public void anagraphic() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            actual = (Stage) anagraphic_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in anagrafica");
        }
    }

    public void canteen() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            actual = (Stage) canteen_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in mensa");
        }
    }

    public void trip() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/trips.fxml"));
            actual = (Stage) trip_b.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in gite");
        }
    }

    public void returnToLogin(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/LoginController.fxml"));
            actual = (Stage) login.getScene().getWindow();
            actual.setScene(new Scene(root, 1000, 630));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare al LoginController");
        }
    }
}
