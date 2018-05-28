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
import main.java.models.Dish;
import main.java.models.DishType;
import main.java.models.Ingredient;
import main.java.models.Provider;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDishController extends AbstractController implements Initializable {

    @FXML private Pane addDishPane;

    @FXML private TextField tfDishName;
    @FXML private ComboBox<DishType> cbDishType;

    @FXML private TextField tfProviderName;
    @FXML private TextField tfProviderVat;

    @FXML private ListView<GuiIngredient> listIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;

    @FXML private ImageView addDishImage;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dish type
        cbDishType.getItems().addAll(DishType.values());

        // Save button
        addDishImage.setOnMouseEntered(event -> addDishPane.getScene().setCursor(Cursor.HAND));
        addDishImage.setOnMouseExited(event -> addDishPane.getScene().setCursor(Cursor.DEFAULT));
        addDishImage.setOnMouseClicked(event -> saveDish());
        Tooltip.install(addDishImage, new Tooltip("Salva"));

        // Go back button
        goBackImage.setOnMouseEntered(event -> addDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addDishPane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        goBackImage.setOnMouseClicked(event -> {
            if (showConfirmationDialog("Sei sicuro di voler tornare indietro? I dati inseriti andranno persi"))
                goBack();
        });

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
        String dishName = tfDishName.getText().trim();
        DishType dishType = cbDishType.getSelectionModel().getSelectedItem();

        Dish dish = new Dish(dishName, dishType, null);

        // Provider
        String providerName = tfProviderName.getText().trim();
        String providerVat = tfProviderVat.getText().trim();
        Provider provider = new Provider(providerVat, providerName);
        dish.setProvider(provider);

        // Ingredients
        dish.addIngredients(TableUtils.getModelsList(listIngredients.getItems()));

        // Save dish
        if (!connectionManager.getClient().create(dish)) {
            showErrorDialog("Impossibile salvare il piatto");
            return;
        }

        // Confirmation dialog
        showInformationDialog("Il piatto \"" + dish.getName() + "\" è stato salvato");

        // Go back to the menu
        goBack();
    }


    /**
     * Go back to the dish page
     */
    private void goBack() {
        setCenterFXML((BorderPane)addDishPane.getParent(), "/views/dish.fxml");
    }

}
