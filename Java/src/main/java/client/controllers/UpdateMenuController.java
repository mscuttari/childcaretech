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
import main.java.exceptions.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;
import main.java.models.Menu;

import java.net.URL;
import java.util.*;

public class UpdateMenuController extends AbstractController implements Initializable {

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

        // Update button
        updateMenuImage.setOnMouseEntered(event -> updateMenuPane.getScene().setCursor(Cursor.HAND));
        updateMenuImage.setOnMouseExited(event -> updateMenuPane.getScene().setCursor(Cursor.DEFAULT));
        updateMenuImage.setOnMouseClicked(event -> updateMenu());
        Tooltip.install(updateMenuImage, new Tooltip("Aggiorna"));

        // Go back button
        goBackImage.setOnMouseEntered(event -> updateMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateMenuPane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        goBackImage.setOnMouseClicked(event -> {
            if (showConfirmationDialog("Sei sicuro di voler tornare indietro? Eventuali modifiche andranno perse"))
                goBack();
        });

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Dishes table
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
     * Set the menu that is going to be modified
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


    private void updateMenu() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        menu.setDayOfTheWeek(cbDayOfTheWeek.getValue());
        menu.getDishes().clear();
        menu.setDishes(TableUtils.getSelectedItems(tableDish));
        menu.setResponsible(TableUtils.getFirstSelectedItem(tableStaff));

        // Check data
        try {
            menu.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Update menu
        boolean updateResult = connectionManager.getClient().update(menu);


        // Go back to the menu list
        if (updateResult) {
            // Information dialog
            showInformationDialog("I dati sono stati aggiornati");

            // Go back to the menu list page
            goBack();
        } else {
            showErrorDialog("Salvataggio non riuscito");
        }
    }


    /**
     * Go back to the menus list page
     */
    public void goBack() {
        setCenterFXML((BorderPane)updateMenuPane.getParent(), "/views/showMenu.fxml");
    }

}