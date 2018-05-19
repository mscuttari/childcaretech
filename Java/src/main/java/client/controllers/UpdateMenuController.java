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

public class UpdateMenuController { //implements Initializable {
/*
    // Debug
    private static final String TAG = "UpdateMenuController";

    private Menu menu;

    @FXML private Pane updateMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ImageView updateMenuImage;
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


    public UpdateMenuController(Menu menu){
        this.menu = menu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*
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

        //Data tab
        tfMenuName.setText(menu.getName());

        //Food table
        List<Food> food = connectionManager.getClient().getFood();
        ObservableList<GuiFood> foodData = TableUtils.getGuiModelsList(food);

        columnFoodSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnFoodSelected));
        columnFoodSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnFoodName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnFoodType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableFood.setEditable(true);
        tableFood.setItems(foodData);

        List<Food> foodInTheMenu = (List<Food>) menu.getComposition();
        for (GuiFood item : tableFood.getItems()) {
            if (foodInTheMenu.contains(item.getModel()))
                item.setSelected(true);
        }

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

        for (GuiStaff item : tableStaff.getItems()) {
            if (menu.getResponsible().equals(item.getModel()))
                item.setSelected(true);
        }

    }

    public void updateMenu() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        menu.setName(tfMenuName.getText().trim());
        menu.setComposition(TableUtils.getSelectedItems(tableFood));
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
    }*/

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
            //BorderPane homePane = (BorderPane) updateMenuPane.getParent();
            //homePane.setCenter(showMenuPane);
        } catch (IOException e) {
            //LogUtils.e(TAG, e.getMessage());
        }
    }

}