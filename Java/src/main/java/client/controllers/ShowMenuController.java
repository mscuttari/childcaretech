package main.java.client.controllers;

import javafx.beans.property.SimpleIntegerProperty;
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
import main.java.client.gui.GuiMenu;
import main.java.client.layout.MyButtonTableCell;
import main.java.client.utils.TableUtils;
import main.java.models.Dish;
import main.java.models.Ingredient;
import main.java.models.Menu;
import main.java.models.Person;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowMenuController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowMenuController";

    @FXML private Pane showMenuPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiMenu> tableMenu;
    @FXML private TableColumn<GuiMenu, String> columnMenuName;
    @FXML private TableColumn<GuiMenu, String> columnMenuDayOfTheWeek;
    @FXML private TableColumn<GuiMenu, Integer> columnMenuAllergicPeople;
    @FXML private TableColumn<GuiMenu, Integer> columnMenuIntolerantPeople;
    @FXML private TableColumn<GuiMenu, Void> columnMenuEdit;
    @FXML private TableColumn<GuiMenu, Void> columnMenuShowDetails;
    @FXML private TableColumn<GuiMenu, Void> columnMenuDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showMenuPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Menu> menus = connectionManager.getClient().getMenus();
        @SuppressWarnings("unchecked") ObservableList<GuiMenu> menusData = TableUtils.getGuiModelsList(menus);

        columnMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnMenuDayOfTheWeek.setCellValueFactory(new PropertyValueFactory<>("dayOfTheWeek"));

        columnMenuAllergicPeople.setCellValueFactory(param -> {
            Menu menu = param.getValue().getModel();
            Collection<Person> allergicPeople = new HashSet<>();

            for (Dish dish : menu.getDishes()) {
                for (Ingredient ingredient : dish.getIngredients()) {
                    allergicPeople.addAll(ingredient.getAllergicPeople());
                }
            }

            return new SimpleIntegerProperty(allergicPeople.size()).asObject();
        });

        columnMenuIntolerantPeople.setCellValueFactory(param -> {
            Menu menu = param.getValue().getModel();
            Collection<Person> intolerantPeople = new HashSet<>();

            for (Dish dish : menu.getDishes()) {
                for (Ingredient ingredient : dish.getIngredients()) {
                    intolerantPeople.addAll(ingredient.getIntolerantPeople());
                }
            }

            return new SimpleIntegerProperty(intolerantPeople.size()).asObject();
        });

        Callback<GuiMenu, Object> showDetailsCallback = new Callback<GuiMenu, Object>() {
            @Override
            public Object call(GuiMenu param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showMenuDetails.fxml"));

                    Pane showMenuDetailsPane = loader.load();
                    ShowMenuDetailsController controller = loader.getController();
                    controller.setMenu(param.getModel());

                    BorderPane homePane = (BorderPane)showMenuPane.getParent();
                    homePane.setCenter(showMenuDetailsPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        };

        // Open details page on row double click
        tableMenu.setRowFactory( tv -> {
            TableRow<GuiMenu> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showDetailsCallback.call(row.getItem());
                }
            });
            return row ;
        });

        columnMenuShowDetails.setCellFactory(param -> new MyButtonTableCell<>("Visualizza dettagli", showDetailsCallback));

        columnMenuEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<GuiMenu, Object>() {

            @Override
            public Object call(GuiMenu param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateMenu.fxml"));

                    Pane updateMenuPane = loader.load();
                    UpdateMenuController controller = loader.getController();
                    controller.setMenu(param.getModel());

                    BorderPane homePane = (BorderPane)showMenuPane.getParent();
                    homePane.setCenter(updateMenuPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        columnMenuDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {
            if (param1.getModel().isDeletable()) {
                if (showConfirmationDialog("Vuoi davvero eliminare il menù?\n(la procedura è irreversibile)")) {
                    deleteData(connectionManager, param1);
                }
            } else {
                showErrorDialog("Questo menù non può essere eliminato");
            }

            return null;
        }));

        tableMenu.setEditable(true);
        tableMenu.setItems(menusData);
    }


    /**
     * Delete data and update table data
     */
    private void deleteData(ConnectionManager connectionManager, GuiMenu param) {
        connectionManager.getClient().delete(param.getModel());
        List<Menu> newMenus = connectionManager.getClient().getMenus();
        @SuppressWarnings("unchecked") ObservableList<GuiMenu> newMenusData = TableUtils.getGuiModelsList(newMenus);

        tableMenu.setItems(newMenusData);
    }


    /**
     * Go back to the dish page
     */
    private void goBack() {
        setCenterFXML((BorderPane)showMenuPane.getParent(), "/views/menu.fxml");
    }

}