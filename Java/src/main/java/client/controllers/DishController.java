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

public class DishController implements Initializable{

    // Debug
    private static final String TAG = "DishController";

    @FXML private Pane dishPane;
    @FXML private Button buttonAddDish;
    @FXML private Button buttonShowDish;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add dish button
        buttonAddDish.setOnAction(event -> addDish());

        //Update dish button
        buttonShowDish.setOnAction(event -> showDish());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> dishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> dishPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());
    }

    public void addDish() {
        try {
            Pane addDishPane = FXMLLoader.load(getClass().getResource("/views/addDish.fxml"));
            BorderPane homePane = (BorderPane) dishPane.getParent();
            homePane.setCenter(addDishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void showDish() {
        try {
            Pane showDishPane = FXMLLoader.load(getClass().getResource("/views/showDish.fxml"));
            BorderPane homePane = (BorderPane) dishPane.getParent();
            homePane.setCenter(showDishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void goBack() {
        try {
            Pane canteenPane = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            BorderPane homePane = (BorderPane) dishPane.getParent();
            homePane.setCenter(canteenPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


}