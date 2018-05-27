package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;
import main.java.models.Menu;

import java.net.URL;
import java.util.*;

public class AddMenuController extends AbstractController implements Initializable {

    @FXML private Pane addMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ComboBox<DayOfTheWeek> cbDayOfTheWeek;
    @FXML private ImageView addMenuImage;
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

        // Save button
        addMenuImage.setOnMouseEntered(event -> addMenuPane.getScene().setCursor(Cursor.HAND));
        addMenuImage.setOnMouseExited(event -> addMenuPane.getScene().setCursor(Cursor.DEFAULT));
        addMenuImage.setOnMouseClicked(event -> saveMenu());

        // Go back button
        goBackImage.setOnMouseEntered(event -> addMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addMenuPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Dish table
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
     * Save menu in the database
     */
    private void saveMenu() {
        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        String menuName = tfMenuName.getText().toLowerCase().trim();
        Staff menuStaff = TableUtils.getFirstSelectedItem(tableStaff);
        DayOfTheWeek dayOfTheWeek = cbDayOfTheWeek.getValue();

        // Create menu
        Menu menu = new Menu(menuName, menuStaff, dayOfTheWeek);

        // Dishes
        menu.addDishes(TableUtils.getSelectedItems(tableDish));

        // Check data
        try {
            menu.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getModelName(), e.getMessage());
            return;
        }

        // Save menu
        if (!connectionManager.getClient().create(menu)) {
            showErrorDialog("Impossibile salvare il menù");
            return;
        }

        // Insert information
        showInformationDialog("Il menù \"" + menu.getName() + "\" è stato salvato");

        // Go back to the menu
        goBack();
    }


    /**
     * Go back to the menu page
     */
    private void goBack() {
        setCenterFXML((BorderPane)addMenuPane.getParent(), "/views/menu.fxml");
    }

}
