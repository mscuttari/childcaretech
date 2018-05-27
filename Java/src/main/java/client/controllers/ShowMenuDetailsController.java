package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.gui.GuiDish;
import main.java.client.gui.GuiPerson;
import main.java.client.utils.TableUtils;
import main.java.models.*;
import main.java.models.Menu;

import java.net.URL;
import java.util.*;

public class ShowMenuDetailsController extends AbstractController implements Initializable {

    private Menu menu;

    @FXML private Pane showMenuDetailsPane;

    @FXML private Label labelName;
    @FXML private Label labelDay;
    @FXML private Label labelStaff;
    @FXML private ImageView goBackImage;
    @FXML private TableView<GuiDish> tableDish;
    @FXML private TableColumn<GuiDish, String> columnDishName;
    @FXML private TableColumn<GuiDish, String> columnDishType;

    @FXML private TableView<GuiPerson<Person>> tablePeople;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleFirstName;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleLastName;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleFiscalCode;
    @FXML private TextArea textAreaIngredients;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showMenuDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showMenuDetailsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Dishes table
        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableDish.setEditable(false);

        // People table
        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePeople.setEditable(false);
        tablePeople.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    /**
     * Load the menu data into the corresponding fields
     */
    private void loadData() {
        labelName.setText(menu.getName());                          // Name
        labelDay.setText(menu.getDayOfTheWeek().toString());        // Day of the week
        labelStaff.setText(menu.getResponsible().toString());       // Responsible

        // Dishes
        ObservableList<GuiDish> guiDishes = TableUtils.getGuiModelsList(menu.getDishes());
        tableDish.setItems(guiDishes);

        // Allergic or intolerant people
        Set<Person> people = new HashSet<>();

        for (Dish dish : menu.getDishes()){
            for (Ingredient ingredient : dish.getIngredients()) {
                people.addAll(ingredient.getAllergicPeople());
                people.addAll(ingredient.getIntolerantPeople());
            }
        }

        ObservableList<GuiPerson<Person>> guiPeople = TableUtils.getGuiModelsList(people);
        tablePeople.setItems(guiPeople);

        tablePeople.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textAreaIngredients.clear();

                // Dishes the person is allergic / intolerant to
                Set<Dish> personDishes = new HashSet<>();

                for (Ingredient current : newSelection.getModel().getAllergies()){
                    personDishes.addAll(current.getDishes());
                }

                for (Ingredient current : newSelection.getModel().getIntolerances()){
                    personDishes.addAll(current.getDishes());
                }

                // Allergies / intolerances to the currently selected menu
                Collection<Dish> dishesInMenu = new ArrayList<>(menu.getDishes());
                dishesInMenu.retainAll(personDishes);

                textAreaIngredients.appendText("Allergie / intolleranze nel menù:\n");

                for (Dish current : dishesInMenu){
                    textAreaIngredients.appendText(current.toString());
                    textAreaIngredients.appendText("\n");
                }

                // Dishes not to be used as replacement
                Collection<Dish> dishesNotInMenu = new ArrayList<>(personDishes);
                dishesNotInMenu.removeAll(dishesInMenu);

                textAreaIngredients.appendText("\nPiatti da non usare come sostituto:\n");

                for (Dish current : dishesNotInMenu){
                    textAreaIngredients.appendText(current.toString());
                    textAreaIngredients.appendText("\n");
                }
            }
        });
    }


    /**
     * Set the menu that is going to be shown
     *
     * @param   menu      menu
     */
    public void setMenu(Menu menu) {
        this.menu = menu;
        loadData();
    }


    /**
     * Go back to the main anagraphic page
     */
    public void goBack() {
        setCenterFXML((BorderPane)showMenuDetailsPane.getParent(), "/views/showMenu.fxml");
    }

}