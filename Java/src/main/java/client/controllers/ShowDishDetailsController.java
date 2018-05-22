package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.models.*;

import java.net.URL;
import java.util.*;

public class ShowDishDetailsController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowDishDetailsController";

    private Dish dish;

    @FXML private Pane showDishDetailsPane;
    @FXML private ImageView goBackImage;

    @FXML private Label labelName;
    @FXML private Label labelType;
    @FXML private Label labelProvider;

    @FXML private TextArea textAreaIngredients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showDishDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDishDetailsPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

    }

    /**
     * Load the dish data into the corresponding fields
     */
    private void loadData() {
        labelName.setText(dish.getName());                           // Title
        labelType.setText(dish.getType().toString());               // Type
        labelProvider.setText(dish.getProvider().toString());       // Provider

        // Ingredients
        for(Ingredient current: dish.getIngredients()){
            textAreaIngredients.appendText(current.toString());
            textAreaIngredients.appendText("\n");
        }
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
     * Go back to the main anagraphic page
     */
    public void goBack() {
        setCenterFXML((BorderPane)showDishDetailsPane.getParent(), "/views/showDish.fxml");
    }

}