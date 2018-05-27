package main.java.client.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.gui.GuiIngredient;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showDishDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDishDetailsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
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

}