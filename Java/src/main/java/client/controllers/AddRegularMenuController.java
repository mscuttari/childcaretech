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

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AddRegularMenuController implements Initializable {

    // Debug
    private static final String TAG = "AddRegularMenuController";

    @FXML private Pane addRegularMenuPane;
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

        // Save button cursor
        addMenuImage.setOnMouseEntered(event -> addRegularMenuPane.getScene().setCursor(Cursor.HAND));
        addMenuImage.setOnMouseExited(event -> addRegularMenuPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addMenuImage.setOnMouseClicked(event -> saveMenu());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> addRegularMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addRegularMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
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

        //Data
        String menuName = tfMenuName.getText().toLowerCase().trim();
        Staff menuStaff = TableUtils.getFirstSelectedItem(tableStaff);
        DayOfTheWeek dayOfTheWeek = cbDayOfTheWeek.getValue();

        //Create regular menù
        RegularMenu menu = new RegularMenu(menuName, menuStaff, dayOfTheWeek);

        menu.addDishes(TableUtils.getSelectedItems(tableDish));

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
            BorderPane homePane = (BorderPane) addRegularMenuPane.getParent();
            homePane.setCenter(menuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }
}