package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiIngredient;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDishController extends AbstractController implements Initializable {

    private Dish dish;

    @FXML private Pane updateDishPane;

    @FXML private TextField tfDishName;
    @FXML private ComboBox<DishType> cbDishType;

    @FXML private TextField tfProviderName;
    @FXML private TextField tfProviderVat;

    @FXML private ListView<GuiIngredient> listIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;

    @FXML private ImageView updateDishImage;
    @FXML private ImageView goBackImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dish type
        cbDishType.getItems().addAll(DishType.values());

        // Update button
        updateDishImage.setOnMouseEntered(event -> updateDishPane.getScene().setCursor(Cursor.HAND));
        updateDishImage.setOnMouseExited(event -> updateDishPane.getScene().setCursor(Cursor.DEFAULT));
        updateDishImage.setOnMouseClicked(event -> saveDish());

        // Go back button
        goBackImage.setOnMouseEntered(event -> updateDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateDishPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Add ingredient on enter key press
        EventHandler<KeyEvent> keyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIngredient();
        };

        tfAddIngredient.setOnKeyPressed(keyPressEvent);

        // Add ingredient button
        buttonAddIngredient.setOnAction(event -> addIngredient());

        // Remove ingredient button
        buttonRemoveSelected.setOnAction(event -> removeSelectedIngredients());

        // Set multiple selection model
        listIngredients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Set the dish that is going to be modified
     *
     * @param   dish    dish
     */
    public void setDish(Dish dish) {
        this.dish = dish;
        loadData();
    }


    /**
     * Load the dish data into the corresponding fields
     */
    private void loadData() {
        // Name
        tfDishName.setText(dish.getName());

        // Type
        cbDishType.getSelectionModel().select(dish.getType());

        // Provider
        tfProviderName.setText(dish.getProvider().getName());
        tfProviderVat.setText(dish.getProvider().getVat());

        // Ingredients
        listIngredients.getItems().setAll(TableUtils.getGuiModelsList(dish.getIngredients()));
        listIngredients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    /**
     * Add ingredient to the ingredients list
     */
    private void addIngredient() {
        if (!tfAddIngredient.getText().isEmpty()){
            String ingredientName = tfAddIngredient.getText().trim().toLowerCase();

            Ingredient ingredient = new Ingredient(ingredientName);
            GuiIngredient guiIngredient = new GuiIngredient(ingredient);

            listIngredients.getItems().add(guiIngredient);
            tfAddIngredient.setText("");
        }
    }


    /**
     * Remove selected ingredients
     */
    private void removeSelectedIngredients() {
        if(!listIngredients.getSelectionModel().isEmpty()) {
            listIngredients.getItems().removeAll(listIngredients.getSelectionModel().getSelectedItems());
            listIngredients.getSelectionModel().clearSelection();

        } else{
            showErrorDialog("Nessun ingrediente selezionato");
        }
    }


    /**
     * Save dish in the database
     */
    private void saveDish() {
        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        DishType dishType = cbDishType.getSelectionModel().getSelectedItem();

        dish.setType(dishType);

        // Provider
        String providerName = tfProviderName.getText().trim();
        String providerVat = tfProviderVat.getText().trim();
        Provider provider = new Provider(providerVat, providerName);
        dish.setProvider(provider);

        // Ingredients
        dish.setIngredients(TableUtils.getModelsList(listIngredients.getItems()));

        // Save dish
        connectionManager.getClient().update(dish);

        // Confirmation dialog
        showInformationDialog("I dati sono stati aggiornati");

        // Go back to the dishes list page
        goBack();
    }


    /**
     * Go back to the dishes list page
     */
    public void goBack() {
        setCenterFXML((BorderPane)updateDishPane.getParent(), "/views/showDish.fxml");
    }

}