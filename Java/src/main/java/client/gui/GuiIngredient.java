package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Ingredient;

public class GuiIngredient extends GuiBaseModel<Ingredient> {

    private StringProperty name;

    public GuiIngredient(Ingredient model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
    }

    public StringProperty nameProperty() {
        return name;
    }

}
