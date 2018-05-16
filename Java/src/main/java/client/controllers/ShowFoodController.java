package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.layout.MyButtonTableCell;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowFoodController implements Initializable{

    // Debug
    private static final String TAG = "ShowFoodController";

    @FXML private Pane showFoodPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Food> tableFood;
    @FXML private TableColumn<Food, String> columnFoodName;
    @FXML private TableColumn<Food, String> columnFoodType;
    @FXML private TableColumn<Food, Void> columnFoodEdit;
    @FXML private TableColumn<Food, Void> columnFoodDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showFoodPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showFoodPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Food> food = connectionManager.getClient().getFood();
        ObservableList<Food> foodData = FXCollections.observableArrayList(food);

        columnFoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnFoodType.setCellValueFactory(new PropertyValueFactory<>("type"));

        columnFoodEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Food, Object>() {

            @Override
            public Object call(Food param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateFood.fxml"));

                    UpdateFoodController updateFoodController = new UpdateFoodController(param);
                    loader.setController(updateFoodController);

                    Pane updateFoodPane = loader.load();
                    BorderPane homePane = (BorderPane) showFoodPane.getParent();
                    homePane.setCenter(updateFoodPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        columnFoodDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowFood = FXMLLoader.load(getClass().getResource("/views/showFood.fxml"));
                BorderPane homePane = (BorderPane) showFoodPane.getParent();
                homePane.setCenter(newPaneShowFood);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tableFood.setEditable(true);
        tableFood.setItems(foodData);
    }

    public void goBack() {
        try {
            Pane foodPane = FXMLLoader.load(getClass().getResource("/views/food.fxml"));
            BorderPane homePane = (BorderPane) showFoodPane.getParent();
            homePane.setCenter(foodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}
