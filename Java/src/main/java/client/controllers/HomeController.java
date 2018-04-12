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
import main.java.LogUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    // Debug
    private static final String TAG = "HomeController";

    @FXML private BorderPane borderPane;
    @FXML private Button buttonAnagraphic;
    @FXML private Button buttonCanteen;
    @FXML private Button buttonTrip;
    @FXML private ImageView goToLoginImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Anagraphic button
        buttonAnagraphic.setOnAction(event -> goToAnagraphic());

        //Canteen button
        buttonCanteen.setOnAction(event -> goToCanteen());

        //Trip button
        buttonTrip.setOnAction(event -> goToTrip());

        //goToLogin Image
        goToLoginImage.setOnMouseClicked(event -> goToLogin());
    }

    public void goToAnagraphic(){
        try {
            Pane anagraphicPane = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            borderPane.setCenter(anagraphicPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

    }

    public void goToCanteen(){
        try {
            Pane canteenPane = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            borderPane.setCenter(canteenPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

    }

    public void goToTrip(){
        try {
            Pane canteenPane = FXMLLoader.load(getClass().getResource("/views/trip.fxml"));
            borderPane.setCenter(canteenPane);
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
