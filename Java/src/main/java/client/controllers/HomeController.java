package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.LogUtils;
import main.java.models.Child;
import main.java.models.Contact;
import main.java.models.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    // Debug
    private static final String TAG = "HomeController";

    @FXML private BorderPane borderPane;
    @FXML private Button buttonAnagraphic;
    @FXML private ImageView goToLoginImage;
    @FXML private ComboBox<Person> cbPersonType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Anagraphic button
        buttonAnagraphic.setOnAction(event -> goToAnagraphic());

        //goToLogin Image
        goToLoginImage.setOnMouseClicked(event -> goToLogin());

        //Person Type

    }

    public void goToAnagraphic(){
        try {
            Pane addPersonPane = FXMLLoader.load(getClass().getResource("/views/AnagraphicController.fxml"));
            borderPane.setCenter(addPersonPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

    }

    public void goToLogin(){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Child Care Tech");
            stage.setScene(new Scene(root, 700, 350));
            stage.setMinWidth(700);
            stage.setMinHeight(350);
            stage.show();

            borderPane.getScene().getWindow().hide();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}
