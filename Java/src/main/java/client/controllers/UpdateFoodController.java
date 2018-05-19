package main.java.client.controllers;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateFoodController implements Initializable{
/*
    // Debug
    private static final String TAG = "UpdateFoodController";

    private Food food;

    @FXML private Pane updateFoodPane;

    @FXML private TextField tfFoodName;
    @FXML private ComboBox<FoodType> cbFoodType;

    @FXML private TextField tfProviderName;
    @FXML private TextField tfProviderVat;

    @FXML private ListView<Ingredient> listIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;
    @FXML private Label labelError;

    @FXML private ImageView updateFoodImage;
    @FXML private ImageView goBackImage;

    public UpdateFoodController(Food food){ this.food = food; }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {/*

        // Food type
        cbFoodType.getItems().addAll(FoodType.values());

        // Update button cursor
        updateFoodImage.setOnMouseEntered(event -> updateFoodPane.getScene().setCursor(Cursor.HAND));
        updateFoodImage.setOnMouseExited(event -> updateFoodPane.getScene().setCursor(Cursor.DEFAULT));

        // Update button click
        updateFoodImage.setOnMouseClicked(event -> saveFood());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> updateFoodPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateFoodPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Add Ingredient on enter key press
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

        //Data
        tfFoodName.setText(food.getName());
        cbFoodType.getSelectionModel().select(food.getType());

        tfProviderName.setText(food.getProvider().getName());
        tfProviderVat.setText(food.getProvider().getVat());

        listIngredients.getItems().setAll(food.getComposition());
        listIngredients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addIngredient() {
        if(!tfAddIngredient.getText().isEmpty()){
            String ingredientName = tfAddIngredient.getText().trim().toLowerCase();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            listIngredients.getItems().add(ingredient);
            tfAddIngredient.setText("");
            labelError.setText("");
        }

    }


    public void removeSelectedIngredients() {

        if(!listIngredients.getSelectionModel().isEmpty()) {
            listIngredients.getItems().removeAll(listIngredients.getSelectionModel().getSelectedItems());
            listIngredients.getSelectionModel().clearSelection();
            labelError.setText("");
        }
        else if(listIngredients.getItems().isEmpty()){
            labelError.setText("Non ci sono ingredienti nella lista");
        }
        else{
            labelError.setText("Non ci sono ingredienti selezionati");
        }*/

    }

    /**
     * Save food in the database
     */
    private void saveFood() {/*

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        String foodName = tfFoodName.getText().trim();
        FoodType foodType = cbFoodType.getSelectionModel().getSelectedItem();

        food.setName(foodName);
        food.setType(foodType);

        //Provider
        List<Provider> allProviders = connectionManager.getClient().getProviders();
        Provider provider = null;
        String providerName = tfProviderName.getText().trim();
        String providerVat = tfProviderVat.getText().trim();
        for(Provider current : allProviders){
            if(current.getVat().equals(providerVat)){
                provider = current;
            }
        }
        if(provider == null){ provider = new Provider(providerVat, providerName); }
        food.setProvider(provider);

        boolean ingredientExists = false;
        List<Ingredient> allIngredients = connectionManager.getClient().getIngredients();
        List<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient listViewItem : listIngredients.getItems()) {
            for(Ingredient databaseListItem : allIngredients){
                if(Objects.equals(listViewItem.getName(), databaseListItem.getName())) {
                    ingredients.add(databaseListItem);
                    ingredientExists = true;
                    break;
                }
            }

            if (!ingredientExists) {
                ingredients.add(listViewItem);
            }
            ingredientExists = false;
        }

        food.setComposition(ingredients);

        // Save food
        connectionManager.getClient().update(food);
    }

    public void goBack() {
        try {
            Pane foodPane = FXMLLoader.load(getClass().getResource("/views/food.fxml"));
            BorderPane homePane = (BorderPane) updateFoodPane.getParent();
            homePane.setCenter(foodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }*/
    }

}