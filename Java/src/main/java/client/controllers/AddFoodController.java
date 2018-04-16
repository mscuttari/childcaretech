package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.models.Ingredient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddFoodController implements Initializable{

    // Debug
    private static final String TAG = "AddFoodController";

    @FXML private Pane addFoodPane;
    @FXML private ListView<Ingredient> lwIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveSelected;
    @FXML private Button buttonAddFood; // "aggiungerà" i dati al database
    @FXML private ImageView goBackImage;
    @FXML private Label labelError;


    private int i=0; //number of ingredients

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Add ingredient button
        buttonAddIngredient.setOnAction(event -> addIngredient());

        // Add Ingredient on enter key press
        EventHandler<KeyEvent> keyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIngredient();
        };

        tfAddIngredient.setOnKeyPressed(keyPressEvent);

        // Remove ingredient button
        buttonRemoveSelected.setOnAction(event -> removeSelectedIngredients());

        // Add ingredient text field initial prompt text
        tfAddIngredient.setPromptText(("Inserisci nuovo alimento"));

        // Go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Set multiple selection model
        lwIngredients.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addIngredient() {
        if(!tfAddIngredient.getText().isEmpty()){
            i++;
            String ingredientName = tfAddIngredient.getText().trim().toLowerCase();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            lwIngredients.getItems().add(ingredient);
            tfAddIngredient.setText("");
            tfAddIngredient.setPromptText("Inserisci un nuovo ingrediente (#" + (i + 1) + ")");
            labelError.setText("");
        }

    }


    public void removeSelectedIngredients() {

        if(!lwIngredients.getSelectionModel().isEmpty()) {
            i = i-(lwIngredients.getSelectionModel().getSelectedItems().size()-1)-1;
            lwIngredients.getItems().removeAll(lwIngredients.getSelectionModel().getSelectedItems());
            lwIngredients.getSelectionModel().clearSelection();
            tfAddIngredient.setPromptText("Inserisci nuovo ingrediente (#" + (i + 1) + ")");
            labelError.setText("");
        }
        else if(lwIngredients.getItems().isEmpty()){
            labelError.setText("Non ci sono ingredienti nella lista");
        }
        else{
            labelError.setText("Non ci sono ingredienti selezionati");
        }

    }

    public void goBack() {
        try {
            Pane foodPane = FXMLLoader.load(getClass().getResource("/views/food.fxml"));
            BorderPane homePane = (BorderPane) addFoodPane.getParent();
            homePane.setCenter(foodPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}