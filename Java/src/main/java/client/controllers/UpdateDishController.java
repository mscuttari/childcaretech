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

public class UpdateDishController implements Initializable{

    // Debug
    private static final String TAG = "UpdateDishController";

    private Dish dish;

    @FXML private Pane updateDishPane;

    @FXML private TextField tfDishName;
    @FXML private ComboBox<DishType> cbDishType;

    @FXML private TextField tfProviderName;
    @FXML private TextField tfProviderVat;

    @FXML private ListView<Ingredient> listIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;
    @FXML private Label labelError;

    @FXML private ImageView updateDishImage;
    @FXML private ImageView goBackImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Dish type
        cbDishType.getItems().addAll(DishType.values());

        // Update button cursor
        updateDishImage.setOnMouseEntered(event -> updateDishPane.getScene().setCursor(Cursor.HAND));
        updateDishImage.setOnMouseExited(event -> updateDishPane.getScene().setCursor(Cursor.DEFAULT));

        // Update button click
        updateDishImage.setOnMouseClicked(event -> saveDish());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> updateDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateDishPane.getScene().setCursor(Cursor.DEFAULT));

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
        //Name
        tfDishName.setText(dish.getName());

        //Type
        cbDishType.getSelectionModel().select(dish.getType());

        //Provider
        tfProviderName.setText(dish.getProvider().getName());
        tfProviderVat.setText(dish.getProvider().getVat());

        //Ingredients
        listIngredients.getItems().setAll(dish.getIngredients());
        listIngredients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addIngredient() {
        if(!tfAddIngredient.getText().isEmpty()){
            String ingredientName = tfAddIngredient.getText().trim().toLowerCase();
            Ingredient ingredient = new Ingredient(ingredientName);
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

        //Provider
        String providerName = tfProviderName.getText().trim();
        String providerVat = tfProviderVat.getText().trim();
        Provider provider = new Provider(providerVat, providerName);
        dish.setProvider(provider);


        dish.getIngredients().clear();
        dish.addIngredients(listIngredients.getItems());

        // Save dish
        connectionManager.getClient().update(dish);
    }

    public void goBack() {
        try {
            Pane dishPane = FXMLLoader.load(getClass().getResource("/views/dish.fxml"));
            BorderPane homePane = (BorderPane) updateDishPane.getParent();
            homePane.setCenter(dishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}