package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    private Stage stage;
    @FXML private BorderPane borderPane;
    @FXML private Button buttonAnagraphic;
    @FXML private ImageView goToLoginImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Login button
        buttonAnagraphic.setOnAction(event -> goToAnagraphic());
        goToLoginImage.setOnMouseClicked(event -> {
            try {
                goToLogin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void goToAnagraphic(){
        try {
            Pane newPane = FXMLLoader.load(getClass().getResource("/views/addPerson.fxml"));
            borderPane.setCenter(newPane);
        } catch (IOException e) {
            System.out.println("Errore nell'entrare in anagrafica");
        }

    }

    public void goToLogin() throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        stage = (Stage) goToLoginImage.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 630));
        stage.show();
    }

}
