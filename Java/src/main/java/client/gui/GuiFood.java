package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Food;

public class GuiFood extends GuiBaseModel<Food> {

    private StringProperty name;
    private StringProperty type;

    public GuiFood(Food model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.type = new SimpleStringProperty(model.getType());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty typeProperty() {
        return type;
    }

}
