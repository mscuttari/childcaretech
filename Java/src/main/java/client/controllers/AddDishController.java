package main.java.client.controllers;

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
import java.util.ResourceBundle;

public class AddDishController implements Initializable{

    // Debug
    private static final String TAG = "AddDishController";

    @FXML private Pane addDishPane;

    @FXML private TextField tfDishName;
    @FXML private ComboBox<DishType> cbDishType;

    @FXML private TextField tfProviderName;
    @FXML private TextField tfProviderVat;

    @FXML private ListView<Ingredient> listIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;
    @FXML private Label labelError;

    @FXML private ImageView addDishImage;
    @FXML private ImageView goBackImage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Dish type
        cbDishType.getItems().addAll(DishType.values());

        // Save button cursor
        addDishImage.setOnMouseEntered(event -> addDishPane.getScene().setCursor(Cursor.HAND));
        addDishImage.setOnMouseExited(event -> addDishPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addDishImage.setOnMouseClicked(event -> saveDish());

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> addDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> addDishPane.getScene().setCursor(Cursor.DEFAULT));

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
        String dishName = tfDishName.getText().trim();
        DishType dishType = cbDishType.getSelectionModel().getSelectedItem();

        Dish dish = new Dish(dishName, dishType, null);

        //Provider
        String providerName = tfProviderName.getText().trim();
        String providerVat = tfProviderVat.getText().trim();
        Provider provider = new Provider(providerVat, providerName);
        dish.setProvider(provider);

        dish.addIngredients(listIngredients.getItems());

        // Save dish
        connectionManager.getClient().create(dish);
    }

    public void goBack() {
        try {
            Pane dishPane = FXMLLoader.load(getClass().getResource("/views/dish.fxml"));
            BorderPane homePane = (BorderPane) addDishPane.getParent();
            homePane.setCenter(dishPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}