package main.java.client.controllers;

import javafx.collections.FXCollections;
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

public class AddAlternativeMenuController implements Initializable {

    private RegularMenu regularMenu;

    // Debug
    private static final String TAG = "AddAlternativeMenuController";

    @FXML private Pane addAlternativeMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ImageView addMenuImage;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiDish> tableDish;
    @FXML private TableColumn<GuiDish, Boolean> columnDishSelected;
    @FXML private TableColumn<GuiDish, String> columnDishName;
    @FXML private TableColumn<GuiDish, String> columnDishType;

    @FXML private TableView<GuiPerson> tablePeople;
    @FXML private TableColumn<GuiPerson, String> columnPeopleFirstName;
    @FXML private TableColumn<GuiPerson, String> columnPeopleLastName;
    @FXML private TableColumn<GuiPerson, String> columnPeopleFiscalCode;

    public AddAlternativeMenuController(RegularMenu regularMenu){
        this.regularMenu = regularMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Save button cursor
        addMenuImage.setOnMouseEntered(event -> addAlternativeMenuPane.getScene().setCursor(Cursor.HAND));
        addMenuImage.setOnMouseExited(event -> addAlternativeMenuPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addMenuImage.setOnMouseClicked(event -> saveMenu());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> addAlternativeMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addAlternativeMenuPane.getScene().setCursor(Cursor.DEFAULT));

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

        // People table
        List<Person> people = new ArrayList<>();
        Set<Person> notHealtyPeople = new HashSet<>();
        people.addAll(connectionManager.getClient().getChildren());
        people.addAll(connectionManager.getClient().getPediatrists());
        people.addAll(connectionManager.getClient().getStaff());
        for (Dish currentDish : regularMenu.getDishes()) {
            for (Ingredient currentIngredient : currentDish.getIngredients()) {
                for (Person currentPerson : people) {
                    if (currentPerson.getIntolerances().contains(currentIngredient) ||
                            currentPerson.getAllergies().contains(currentIngredient)) {
                        notHealtyPeople.add(currentPerson);
                    }
                }
            }
        }

        ObservableList<GuiPerson> staffData = TableUtils.getGuiModelsList(new ArrayList<>(notHealtyPeople));

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePeople.setEditable(true);
        tablePeople.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tablePeople.setItems(staffData);

        tablePeople.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            tableDish.setItems(TableUtils.getGuiModelsList(connectionManager.getClient().getDishes()));
            Set<Ingredient> badIngredients = new HashSet<>();
                Set<GuiDish> badDishes = new HashSet<>();
                for(GuiPerson currentPerson : tablePeople.getSelectionModel().getSelectedItems()) {
                    badIngredients.addAll(((Person) currentPerson.getModel()).getAllergies());
                    badIngredients.addAll(((Person) currentPerson.getModel()).getIntolerances());
                }
                for(GuiDish currentDish : tableDish.getItems()){
                    for(Ingredient currentIngredient : badIngredients){
                        if(currentDish.getModel().getIngredients().contains(currentIngredient)){
                            badDishes.add(currentDish);
                        }
                    }
                }
                tableDish.getItems().removeAll(badDishes);
        });

    }


    /**
     * Save menu in the database
     */
    private void saveMenu() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Data
        String menuName = tfMenuName.getText().toLowerCase().trim();

        //Create alternative menù
        AlternativeMenu menu = new AlternativeMenu(menuName, regularMenu.getResponsible(), regularMenu);

        menu.addDishes(TableUtils.getSelectedItems(tableDish));
        for(GuiPerson currentPerson : tablePeople.getSelectionModel().getSelectedItems()){
            menu.addPerson((Person) currentPerson.getModel());
        }
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
            Pane showRegularMenuPane = FXMLLoader.load(getClass().getResource("/views/showRegularMenu.fxml"));
            BorderPane homePane = (BorderPane) addAlternativeMenuPane.getParent();
            homePane.setCenter(showRegularMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }
}