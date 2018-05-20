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

public class ShowRegularMenuController implements Initializable{

    // Debug
    private static final String TAG = "ShowRegularMenuController";

    @FXML private Pane showRegularMenuPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<RegularMenu> tableRegularMenu;
    @FXML private TableColumn<RegularMenu, String> columnRegularMenuName;
    @FXML private TableColumn<RegularMenu, Void> columnRegularMenuEdit;
    @FXML private TableColumn<RegularMenu, Void> columnRegularMenuDelete;
    @FXML private TableColumn<RegularMenu, Void> columnAlternativeMenuAdd;
    @FXML private TableColumn<RegularMenu, Void> columnAlternativeMenuShow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        /*

        //Show Regular Menus without Alternative Menus that satisfy all allergies and intollerance
        List<RegularMenu> incompleteMenus = new ArrayList<>();

        Set<Person> peopleWithAllergiesInTheMenu = new HashSet<>();
        Set<Person> peopleWithAlternativeMenu = new HashSet<>();


        for (Menu currentRegularMenu : connectionManager.getClient().getRegularMenus()){
            for(AlternativeMenu currentAlternativeMenu : currentRegularMenu.getAlternativeMenus()){
                peopleWithAlternativeMenu.addAll(currentAlternativeMenu.getPeople();
            }
            for (Food currentFood : currentRegularMenu.getComposition()) {
                for (Ingredient currentIngredient : currentFood.getComposition()) {
                    peopleWithAllergiesInTheMenu.addAll(currentIngredient.getAllergicPeople());
                    peopleWithAllergiesInTheMenu.addAll(currentIngredient.getIntollerantPeople());
                }
            }
            peopleWithAllergiesInTheMenu.removeAll(peopleWithAlternativeMenu)
            if(!peopleWithALeergiesInTheMenu.isEmpty()){
                incompleteMenus.add(currentRegularMenu);
            }
        }


        */

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showRegularMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showRegularMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Table
        List<RegularMenu> regularMenus = connectionManager.getClient().getRegularMenus();
        ObservableList<RegularMenu> regularMenusData = FXCollections.observableArrayList(regularMenus);

        columnRegularMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnRegularMenuEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<RegularMenu, Object>() {

            @Override
            public Object call(RegularMenu param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateRegularMenu.fxml"));

                    UpdateRegularMenuController updateMenuController = new UpdateRegularMenuController(param);
                    loader.setController(updateMenuController);

                    Pane updateMenuPane = loader.load();
                    BorderPane homePane = (BorderPane) showRegularMenuPane.getParent();
                    homePane.setCenter(updateMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnRegularMenuDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowRegularMenu = FXMLLoader.load(getClass().getResource("/views/showRegularMenu.fxml"));
                BorderPane homePane = (BorderPane) showRegularMenuPane.getParent();
                homePane.setCenter(newPaneShowRegularMenu);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));
        columnAlternativeMenuAdd.setCellFactory(param -> new MyButtonTableCell<>("Aggiungi menù alternativo", new Callback<RegularMenu, Object>() {

            @Override
            public Object call(RegularMenu param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addAlternativeMenu.fxml"));

                    AddAlternativeMenuController addAlternativeMenuController = new AddAlternativeMenuController(param);
                    loader.setController(addAlternativeMenuController);

                    Pane addAlternativeMenuPane = loader.load();
                    BorderPane homePane = (BorderPane) showRegularMenuPane.getParent();
                    homePane.setCenter(addAlternativeMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnAlternativeMenuShow.setCellFactory(param -> new MyButtonTableCell<>("Mostra menù alternativi", new Callback<RegularMenu, Object>() {

            @Override
            public Object call(RegularMenu param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showAlternativeMenu.fxml"));

                    ShowAlternativeMenuController showAlternativeMenuController = new ShowAlternativeMenuController(param);
                    loader.setController(showAlternativeMenuController);

                    Pane showAlternativeMenuPane = loader.load();
                    BorderPane homePane = (BorderPane) showRegularMenuPane.getParent();
                    homePane.setCenter(showAlternativeMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        tableRegularMenu.setEditable(true);
        tableRegularMenu.setItems(regularMenusData);
    }

    public void goBack() {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
            BorderPane homePane = (BorderPane) showRegularMenuPane.getParent();
            homePane.setCenter(menuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}