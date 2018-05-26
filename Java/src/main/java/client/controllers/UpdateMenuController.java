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

public class UpdateMenuController implements Initializable {

    // Debug
    private static final String TAG = "UpdateMenuController";

    private Menu menu;

    @FXML private Pane updateMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ComboBox<DayOfTheWeek> cbDayOfTheWeek;
    @FXML private ImageView updateMenuImage;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiDish> tableDish;
    @FXML private TableColumn<GuiDish, Boolean> columnDishSelected;
    @FXML private TableColumn<GuiDish, String> columnDishName;
    @FXML private TableColumn<GuiDish, String> columnDishType;

    @FXML private TableView<GuiStaff> tableStaff;
    @FXML private TableColumn<GuiStaff, Boolean> columnStaffSelected;
    @FXML private TableColumn<GuiStaff, String> columnStaffFirstName;
    @FXML private TableColumn<GuiStaff, String> columnStaffLastName;
    @FXML private TableColumn<GuiStaff, String> columnStaffFiscalCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Day of the week
        cbDayOfTheWeek.getItems().addAll(DayOfTheWeek.values());

        // update menu button cursor
        updateMenuImage.setOnMouseEntered(event -> updateMenuPane.getScene().setCursor(Cursor.HAND));
        updateMenuImage.setOnMouseExited(event -> updateMenuPane.getScene().setCursor(Cursor.DEFAULT));

        // update menu image
        updateMenuImage.setOnMouseClicked(event -> updateMenu());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> updateMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Dish table
        List<Dish> dishes = connectionManager.getClient().getDishes();
        ObservableList<GuiDish> dishData = TableUtils.getGuiModelsList(dishes);

        columnDishSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnDishSelected));
        columnDishSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableDish.setEditable(true);
        tableDish.setItems(dishData);

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
     * Set the menù that is going to be modified
     *
     * @param   menu    menu
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
        loadData();
    }

    /**
     * Load the menu data into the corresponding fields
     */
    private void loadData() {
        // Name
        tfMenuName.setText(menu.getName());

        // Day of the week
        cbDayOfTheWeek.getSelectionModel().select(menu.getDayOfTheWeek());

        // Dishes
        for (GuiDish item : tableDish.getItems()) {
            if (menu.getDishes().contains(item.getModel()))
                item.setSelected(true);
        }

        // Staff
        for (GuiStaff item : tableStaff.getItems()) {
            if (menu.getResponsible().equals(item.getModel()))
                item.setSelected(true);
        }
    }

    public void updateMenu() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        menu.setDayOfTheWeek(cbDayOfTheWeek.getValue());
        menu.getDishes().clear();
        menu.addDishes(TableUtils.getSelectedItems(tableDish));
        menu.setResponsible(TableUtils.getFirstSelectedItem(tableStaff));

        // Check data
        try {
            menu.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Update menu
        connectionManager.getClient().update(menu);
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
            Pane showMenuPane = FXMLLoader.load(getClass().getResource("/views/showMenu.fxml"));
            BorderPane homePane = (BorderPane) updateMenuPane.getParent();
            homePane.setCenter(showMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}