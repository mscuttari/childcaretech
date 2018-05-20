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
import main.java.models.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAlternativeMenuController implements Initializable{

    // Connection
    ConnectionManager connectionManager = ConnectionManager.getInstance();

    // Debug
    private static final String TAG = "ShowAlternativeMenuController";

    private RegularMenu regularMenu;

    @FXML private Pane showAlternativeMenuPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<AlternativeMenu> tableAlternativeMenu;
    @FXML private TableColumn<AlternativeMenu, String> columnAlternativeMenuName;
    @FXML private TableColumn<AlternativeMenu, Void> columnAlternativeMenuEdit;
    @FXML private TableColumn<AlternativeMenu, Void> columnAlternativeMenuDelete;

    public ShowAlternativeMenuController(RegularMenu regularMenu){this.regularMenu = regularMenu;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showAlternativeMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showAlternativeMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Table
        List<AlternativeMenu> alternativeMenus = new ArrayList<>(regularMenu.getAlternativeMenus());
        ObservableList<AlternativeMenu> alternativeMenusData = FXCollections.observableArrayList(alternativeMenus);

        columnAlternativeMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAlternativeMenuEdit.setCellFactory(param -> new MyButtonTableCell<>("Mostra dettagli", new Callback<AlternativeMenu, Object>() {

            @Override
            public Object call(AlternativeMenu param) {
              try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showDetailsAlternativeMenu.fxml"));

                    ShowDetailsAlternativeMenuController showDetailsAlternativeMenuController = new ShowDetailsAlternativeMenuController(param);
                    loader.setController(showDetailsAlternativeMenuController);

                    Pane updateMenuPane = loader.load();
                    BorderPane homePane = (BorderPane) showAlternativeMenuPane.getParent();
                    homePane.setCenter(updateMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnAlternativeMenuDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane showRegularMenuPane = FXMLLoader.load(getClass().getResource("/views/showRegularMenu.fxml"));
                BorderPane homePane = (BorderPane) showAlternativeMenuPane.getParent();
                homePane.setCenter(showRegularMenuPane);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tableAlternativeMenu.setEditable(true);
        tableAlternativeMenu.setItems(alternativeMenusData);
    }

    public void goBack() {
        try {
            Pane showRegularMenuPane = FXMLLoader.load(getClass().getResource("/views/showRegularMenu.fxml"));
            BorderPane homePane = (BorderPane) showAlternativeMenuPane.getParent();
            homePane.setCenter(showRegularMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }
}