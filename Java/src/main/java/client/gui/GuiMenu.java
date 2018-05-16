package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Menu;

public abstract class GuiMenu<M extends Menu> extends GuiBaseModel<M> {

    private StringProperty name;

    public GuiMenu(M model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
    }

    public StringProperty nameProperty() {
        return name;
    }

}
