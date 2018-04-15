package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFoodController implements Initializable{

    // Debug
    private static final String TAG = "AddFoodController";

    @FXML private Pane addFoodPane;
    @FXML private TextArea taIngredients;
    @FXML private TextField tfAddIngredient;
    @FXML private Button buttonAddIngredient;
    @FXML private Button buttonRemoveIngredient;
    @FXML private Label labelError;
    @FXML private ImageView goBackImage;
    @FXML private Button buttonAddFood;
    @FXML private List<String> ingredientsInFood = new ArrayList<String>(); // food_composition table in database

    private int i; //number of ingredients

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
        buttonRemoveIngredient.setOnAction(event -> removeIngredient());

        //Add food button
        buttonAddFood.setOnAction(event -> printIngredients());

        // Add ingredient text field initial prompt text
        tfAddIngredient.setPromptText(("Inserisci nuovo alimento"));

        // Go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        //Number of ingredients
        i=0;
    }

    public void addIngredient() {
        if(!tfAddIngredient.getText().isEmpty()){
            i++;
            String current = tfAddIngredient.getText().trim();
            taIngredients.appendText(i+") "+current+"\n");
            ingredientsInFood.add(current.trim());
            tfAddIngredient.setText("");
            tfAddIngredient.setPromptText("Inserisci un nuovo ingrediente (#" + (i+1) + ")");
            labelError.setText("");
        }

    }


    public void removeIngredient() {

        if(!taIngredients.getText().isEmpty()) {
            int start = taIngredients.getText().lastIndexOf(String.valueOf(i));
            int end = taIngredients.getText().length();
            taIngredients.replaceText(start, end, "");
            ingredientsInFood.remove(i-1);
            i--;
            tfAddIngredient.setPromptText("Inserisci nuovo ingrediente (#" + (i + 1) + ")");
        }
        else{
            // delete ingredient error
            labelError.setText("Non ci sono ingredienti da rimuovere");
        }

    }

    //test (verrà sostituito con il metodo che 'scrive' i dati sul database)
    public void printIngredients(){
        taIngredients.clear();
        for(String current : ingredientsInFood){
            taIngredients.appendText(current+"\n");
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