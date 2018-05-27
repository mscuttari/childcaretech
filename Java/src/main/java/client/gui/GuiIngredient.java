package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Ingredient;

public class GuiIngredient extends GuiBaseModel<Ingredient> {

    private StringProperty name;


    /**
     * Constructor
     *
     * @param   model   ingredient model
     */
    public GuiIngredient(Ingredient model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.name.addListener((observable, oldValue, newValue) -> getModel().setName(newValue));
    }


    @Override
    public String toString() {
        return nameProperty().getValue();
    }


    public StringProperty nameProperty() {
        return name;
    }

}
