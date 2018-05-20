package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Dish;
import main.java.models.DishType;

public class GuiDish extends GuiBaseModel<Dish> {

    private StringProperty name;
    private ObjectProperty<DishType> type;

    public GuiDish(Dish model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.type = new SimpleObjectProperty<>(model.getType());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<DishType> typeProperty() {
        return type;
    }

}