package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Food;
import main.java.models.FoodType;

public class GuiFood extends GuiBaseModel<Food> {

    private StringProperty name;
    private ObjectProperty<FoodType> type;

    public GuiFood(Food model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.type = new SimpleObjectProperty<>(model.getType());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<FoodType> typeProperty() {
        return type;
    }

}
