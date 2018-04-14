package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
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

        // Add ingredient text field initial prompt text
        tfAddIngredient.setPromptText(("Inserisci nuovo alimento"));

        //Number of ingredients
        i=0;
    }

    public void addIngredient() {
        if(!tfAddIngredient.getText().isEmpty()){
            i++;
            taIngredients.appendText(i+") "+tfAddIngredient.getText().trim()+"\n");
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
            i--;
            tfAddIngredient.setPromptText("Inserisci nuovo ingrediente (#" + (i + 1) + ")");
        }
        else{
            // delete ingredient error
            labelError.setText("Non ci sono ingredienti da rimuovere");
        }

    }

}