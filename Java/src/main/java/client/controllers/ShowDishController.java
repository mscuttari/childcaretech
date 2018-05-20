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

public class ShowDishController implements Initializable{

    // Debug
    private static final String TAG = "ShowDishController";

    @FXML private Pane showDishPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Dish> tableDish;
    @FXML private TableColumn<Dish, String> columnDishName;
    @FXML private TableColumn<Dish, String> columnDishType;
    @FXML private TableColumn<Dish, Void> columnDishEdit;
    @FXML private TableColumn<Dish, Void> columnDishDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDishPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Dish> dishes = connectionManager.getClient().getDishes();
        ObservableList<Dish> dishesData = FXCollections.observableArrayList(dishes);

        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        columnDishEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Dish, Object>() {

            @Override
            public Object call(Dish param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateDish.fxml"));

                    UpdateDishController updateDishController = new UpdateDishController(param);
                    loader.setController(updateDishController);

                    Pane updateDishPane = loader.load();
                    BorderPane homePane = (BorderPane) showDishPane.getParent();
                    homePane.setCenter(updateDishPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        columnDishDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowDish = FXMLLoader.load(getClass().getResource("/views/showDish.fxml"));
                BorderPane homePane = (BorderPane) showDishPane.getParent();
                homePane.setCenter(newPaneShowDish);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tableDish.setEditable(true);
        tableDish.setItems(dishesData);
    }

    public void goBack() {
        try {
            Pane dishPane = FXMLLoader.load(getClass().getResource("/views/dish.fxml"));
            BorderPane homePane = (BorderPane) showDishPane.getParent();
            homePane.setCenter(dishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}
