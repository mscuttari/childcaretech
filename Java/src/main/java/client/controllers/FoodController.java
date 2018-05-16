package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FoodController implements Initializable{

    // Debug
    private static final String TAG = "FoodController";

    @FXML private Pane foodPane;
    @FXML private Button buttonAddFood;
    @FXML private Button buttonShowFood;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add food button
        buttonAddFood.setOnAction(event -> addFood());

        //Update food button
        buttonShowFood.setOnAction(event -> showFood());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> foodPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> foodPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());
    }

    public void addFood() {
        try {
            Pane addFoodPane = FXMLLoader.load(getClass().getResource("/views/addFood.fxml"));
            BorderPane homePane = (BorderPane) foodPane.getParent();
            homePane.setCenter(addFoodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void showFood() {
        try {
            Pane showFoodPane = FXMLLoader.load(getClass().getResource("/views/showFood.fxml"));
            BorderPane homePane = (BorderPane) foodPane.getParent();
            homePane.setCenter(showFoodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void goBack() {
        try {
            Pane canteenPane = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            BorderPane homePane = (BorderPane) foodPane.getParent();
            homePane.setCenter(canteenPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


}