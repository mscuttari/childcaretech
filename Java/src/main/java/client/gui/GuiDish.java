package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Dish;
import main.java.models.DishType;
import main.java.models.Provider;

public class GuiDish extends GuiBaseModel<Dish> {

    private StringProperty name;
    private ObjectProperty<DishType> type;
    private ObjectProperty<Provider> provider;


    /**
     * Constructor
     *
     * @param   model   dish model
     */
    public GuiDish(Dish model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.name.addListener((observable, oldValue, newValue) -> getModel().setName(newValue));

        this.type = new SimpleObjectProperty<>(model.getType());
        this.type.addListener((observable, oldValue, newValue) -> getModel().setType(newValue));

        this.provider = new SimpleObjectProperty<>(model.getProvider());
        this.provider.addListener((observable, oldValue, newValue) -> getModel().setProvider(newValue));
    }


    @Override
    public String toString() {
        return "[" + typeProperty().getValue() + "] - " + nameProperty().getValue() ;
    }


    public StringProperty nameProperty() {
        return name;
    }


    public ObjectProperty<DishType> typeProperty() {
        return type;
    }


    public ObjectProperty<Provider> providerProperty() {
        return provider;
    }

}
