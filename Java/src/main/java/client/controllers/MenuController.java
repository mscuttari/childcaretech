package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable{

    // Debug
    private static final String TAG = "MenuController";

    @FXML private Pane menuPane;
    @FXML private Button buttonAddRegularMenu;
    @FXML private Button buttonShowRegularMenu;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add regular menu button
        buttonAddRegularMenu.setOnAction(event -> addRegularMenu());

        //Show menu button
        buttonShowRegularMenu.setOnAction(event -> showRegularMenu());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> menuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> menuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());
    }

    public void addRegularMenu() {
        try {
            Pane addRegularMenuPane = FXMLLoader.load(getClass().getResource("/views/addRegularMenu.fxml"));
            BorderPane homePane = (BorderPane) menuPane.getParent();
            homePane.setCenter(addRegularMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void showRegularMenu() {
        try {
            Pane showRegularMenuPane = FXMLLoader.load(getClass().getResource("/views/showRegularMenu.fxml"));
            BorderPane homePane = (BorderPane) menuPane.getParent();
            homePane.setCenter(showRegularMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

    public void goBack() {
        try {
            Pane canteenPane = FXMLLoader.load(getClass().getResource("/views/canteen.fxml"));
            BorderPane homePane = (BorderPane) menuPane.getParent();
            homePane.setCenter(canteenPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}