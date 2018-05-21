package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.models.*;
import main.java.models.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowMenuDetailsController implements Initializable {

    // Debug
    private static final String TAG = "ShowMenuDetailsController";

    private Menu menu;

    @FXML private Pane showMenuDetailsPane;

    @FXML private Label labelName;
    @FXML private Label labelDay;
    @FXML private Label labelStaff;
    @FXML private ImageView goBackImage;
    @FXML private TableView<Dish> tableDish;
    @FXML private TableColumn<Dish, String> columnDishName;
    @FXML private TableColumn<Dish, String> columnDishType;

    @FXML private TableView<Person> tablePeople;
    @FXML private TableColumn<Person, String> columnPeopleFirstName;
    @FXML private TableColumn<Person, String> columnPeopleLastName;
    @FXML private TableColumn<Person, String> columnPeopleFiscalCode;
    @FXML private TextArea textAreaIngredients;

    public ShowMenuDetailsController(Menu menu){ this.menu = menu; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showMenuDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showMenuDetailsPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Data tab
        labelName.setText(menu.getName());
        labelDay.setText(menu.getDayOfTheWeek().toString());
        labelStaff.setText(menu.getResponsible().toString());

        //Dish table
        ObservableList<Dish> dishesData = FXCollections.observableArrayList(menu.getDishes());

        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableDish.setEditable(false);
        tableDish.setItems(dishesData);

        //People table
        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        Set<Person> people = new HashSet<>();
        Set<Ingredient> ingredients = new HashSet<>();
        for(Dish currentMenu : menu.getDishes()){
            for(Ingredient currentIngredient : currentMenu.getIngredients()){
                people.addAll(currentIngredient.getAllergicPeople());
                people.addAll(currentIngredient.getIntolerantPeople());
            }
            ingredients.addAll(currentMenu.getIngredients());
        }
        ObservableList<Person> peopleData = FXCollections.observableArrayList(people);
        tablePeople.setEditable(false);
        tablePeople.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tablePeople.setItems(peopleData);

        tablePeople.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textAreaIngredients.clear();
                textAreaIngredients.appendText("Allergie/intolleranze nel menù:\n");
                Set<Dish> personDishes = new HashSet<>();
                for(Ingredient current : newSelection.getAllergies()){
                    personDishes.addAll(current.getDishes());
                }
                for(Ingredient current : newSelection.getIntolerances()){
                    personDishes.addAll(current.getDishes());
                }
                List<Dish> dishesInMenu = new ArrayList<>(menu.getDishes());
                dishesInMenu.retainAll(personDishes);
                for(Dish current : dishesInMenu){
                    textAreaIngredients.appendText(current.toString());
                    textAreaIngredients.appendText("\n");
                }
                List<Dish> dishesNotInMenu = new ArrayList<>(personDishes);
                dishesNotInMenu.removeAll(dishesInMenu);
                textAreaIngredients.appendText("\nPiatti da non usare come sostituto:\n");
                for(Dish current : dishesNotInMenu){
                    textAreaIngredients.appendText(current.toString());
                    textAreaIngredients.appendText("\n");
                }
            }
        });
    }

    public void goBack() {
        try {
            Pane showMenuPane = FXMLLoader.load(getClass().getResource("/views/showMenu.fxml"));
            BorderPane homePane = (BorderPane) showMenuDetailsPane.getParent();
            homePane.setCenter(showMenuPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}