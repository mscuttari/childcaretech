package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Menu;

public class GuiMenu extends GuiBaseModel<Menu> {

    private StringProperty name;
    private StringProperty type;

    public GuiMenu(Menu model) {
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
