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
import main.java.models.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowMenuController implements Initializable{

    // Debug
    private static final String TAG = "ShowMenuController";

    @FXML private Pane showMenuPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Menu> tableMenu;
    @FXML private TableColumn<Menu, String> columnMenuName;
    @FXML private TableColumn<Menu, String> columnMenuDayOfTheWeek;
    @FXML private TableColumn<Menu, Void> columnMenuEdit;
    @FXML private TableColumn<Menu, Void> columnMenuShowDetails;
    @FXML private TableColumn<Menu, Void> columnMenuDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Table
        List<Menu> menus = connectionManager.getClient().getMenus();
        ObservableList<Menu> menusData = FXCollections.observableArrayList(menus);

        columnMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnMenuDayOfTheWeek.setCellValueFactory(new PropertyValueFactory<>("dayOfTheWeek"));

        columnMenuEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Menu, Object>() {

            @Override
            public Object call(Menu param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateMenu.fxml"));

                    UpdateMenuController updateMenuController = new UpdateMenuController(param);
                    loader.setController(updateMenuController);

                    Pane updateMenuPane = loader.load();
                    BorderPane homePane = (BorderPane) showMenuPane.getParent();
                    homePane.setCenter(updateMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnMenuShowDetails.setCellFactory(param -> new MyButtonTableCell<>("Visualizza dettagli", new Callback<Menu, Object>() {

            @Override
            public Object call(Menu param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showMenuDetails.fxml"));

                    ShowMenuDetailsController showMenuDetailsController = new ShowMenuDetailsController(param);
                    loader.setController(showMenuDetailsController);

                    Pane showMenuDetailsPane = loader.load();
                    BorderPane homePane = (BorderPane) showMenuPane.getParent();
                    homePane.setCenter(showMenuDetailsPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnMenuDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowMenu = FXMLLoader.load(getClass().getResource("/views/showMenu.fxml"));
                BorderPane homePane = (BorderPane) showMenuPane.getParent();
                homePane.setCenter(newPaneShowMenu);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tableMenu.setEditable(true);
        tableMenu.setItems(menusData);
    }

    public void goBack() {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
            BorderPane homePane = (BorderPane) showMenuPane.getParent();
            homePane.setCenter(menuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}