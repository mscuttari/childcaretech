package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;
import main.java.models.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddMenuController implements Initializable {

    // Debug
    private static final String TAG = "AddMenuController";

    @FXML private Pane addMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ComboBox<MenuType> cbMenuType;
    @FXML private ImageView addMenuImage;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiFood> tableFood;
    @FXML private TableColumn<GuiFood, Boolean> columnFoodSelected;
    @FXML private TableColumn<GuiFood, String> columnFoodName;
    @FXML private TableColumn<GuiFood, String> columnFoodType;

    @FXML private TableView<GuiStaff> tableStaff;
    @FXML private TableColumn<GuiStaff, Boolean> columnStaffSelected;
    @FXML private TableColumn<GuiStaff, String> columnStaffFirstName;
    @FXML private TableColumn<GuiStaff, String> columnStaffLastName;
    @FXML private TableColumn<GuiStaff, String> columnStaffFiscalCode;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Menu type
        cbMenuType.getItems().addAll(MenuType.values());
        cbMenuType.getSelectionModel().select(1);


        // Save button cursor
        addMenuImage.setOnMouseEntered(event -> addMenuPane.getScene().setCursor(Cursor.HAND));
        addMenuImage.setOnMouseExited(event -> addMenuPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addMenuImage.setOnMouseClicked(event -> saveMenu());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> addMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Food table
        List<Food> food = connectionManager.getClient().getFood();
        ObservableList<GuiFood> foodData = TableUtils.getGuiModelsList(food);

        columnFoodSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnFoodSelected));
        columnFoodSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnFoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnFoodType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableFood.setEditable(true);
        tableFood.setItems(foodData);

        // Staff table
        List<Staff> staff = connectionManager.getClient().getStaff();
        ObservableList<GuiStaff> staffData = TableUtils.getGuiModelsList(staff);

        columnStaffSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnStaffSelected));
        columnStaffSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnStaffFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnStaffLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStaffFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableStaff.setEditable(true);
        tableStaff.setItems(staffData);

    }


    /**
     * Save menu in the database
     */
    private void saveMenu() {

        //Create menù
        Menu menu = null;

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        String name = tfMenuName.getText().trim();

        switch (cbMenuType.getValue()) {
            case ALTERNATIVEMENU:
                menu = new AlternativeMenu(name, TableUtils.getFirstSelectedItem(tableStaff), null);
                break;

            case REGULARMENU:
                menu = new RegularMenu(name, TableUtils.getFirstSelectedItem(tableStaff));
                break;
        }

        menu.setComposition(TableUtils.getSelectedItems(tableFood));

        // Check data
        try {
            menu.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Save menu
        connectionManager.getClient().create(menu);

    }

    /**
     * Show error dialog
     *
     * @param   message     error message
     */
    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void goBack() {
        try {
            Pane menuPane = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
            BorderPane homePane = (BorderPane) addMenuPane.getParent();
            homePane.setCenter(menuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }
}