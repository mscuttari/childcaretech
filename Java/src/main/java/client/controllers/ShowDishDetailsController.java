package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.gui.GuiIngredient;
import main.java.client.gui.GuiPerson;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.util.*;

public class ShowDishDetailsController extends AbstractController implements Initializable {

    private Dish dish;

    @FXML private Pane showDishDetailsPane;
    @FXML private ImageView goBackImage;

    @FXML private Label labelName;
    @FXML private Label labelType;
    @FXML private Label labelProvider;

    @FXML private ListView<GuiIngredient> lvIngredients;

    @FXML private TableView<GuiPerson<Person>> tablePeople;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleFirstName;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleLastName;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleType;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleAllergies;
    @FXML private TableColumn<GuiPerson<Person>, String> columnPeopleIntolerances;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showDishDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDishDetailsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Allergic / intolerant people
        tablePeople.setEditable(false);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleType.setCellValueFactory(param -> new SimpleStringProperty(PersonType.getPersonType(param.getValue().getModel()).toString()));

        columnPeopleAllergies.setCellValueFactory(param -> {
            Person person = param.getValue().getModel();
            List<Ingredient> ingredients = intersection(person.getAllergies(), dish.getIngredients());
            ingredients.sort(Comparator.comparing(Ingredient::getName));

            if (ingredients.isEmpty())
                return new SimpleStringProperty("-");

            StringBuilder result = new StringBuilder();
            String separator = "";

            for (Ingredient ingredient : ingredients) {
                result.append(separator).append(ingredient.getName());
                separator = ", ";
            }

            return new SimpleStringProperty(result.toString());
        });

        columnPeopleIntolerances.setCellValueFactory(param -> {
            Person person = param.getValue().getModel();
            List<Ingredient> ingredients = intersection(person.getIntolerances(), dish.getIngredients());
            ingredients.sort(Comparator.comparing(Ingredient::getName));

            if (ingredients.isEmpty())
                return new SimpleStringProperty("-");

            StringBuilder result = new StringBuilder();
            String separator = "";

            for (Ingredient ingredient : ingredients) {
                result.append(separator).append(ingredient.getName());
                separator = ", ";
            }

            return new SimpleStringProperty(result.toString());
        });
    }


    /**
     * Load the dish data into the corresponding fields
     */
    private void loadData() {
        labelName.setText(dish.getName());                          // Title
        labelType.setText(dish.getType().toString());               // Type
        labelProvider.setText(dish.getProvider().toString());       // Provider

        // Ingredients
        ObservableList<GuiIngredient> guiIngredients = TableUtils.getGuiModelsList(dish.getIngredients());
        lvIngredients.setItems(guiIngredients.sorted(Comparator.comparing(o -> o.getModel().getName())));

        // Allergic / intolerant people
        Collection<Person> people = new HashSet<>();

        for (Ingredient ingredient : dish.getIngredients()) {
            people.addAll(ingredient.getAllergicPeople());
            people.addAll(ingredient.getIntolerantPeople());
        }

        ObservableList<GuiPerson<Person>> guiPeople = TableUtils.getGuiModelsList(people);
        tablePeople.setItems(guiPeople);
    }


    /**
     * Set the trip that is going to be shown
     *
     * @param   dish      dish
     */
    public void setDish(Dish dish) {
        this.dish = dish;
        loadData();
    }


    /**
     * Go back to the dishes list page
     */
    public void goBack() {
        setCenterFXML((BorderPane)showDishDetailsPane.getParent(), "/views/showDish.fxml");
    }


    /**
     * Intersect two collections
     *
     * @param   x       first collection
     * @param   y       second collection
     * @return  list of common elements
     */
    private static <T> List<T> intersection(Collection<T> x, Collection<T> y) {
        List<T> list = new ArrayList<>();

        for (T element : x) {
            if (y.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }

}