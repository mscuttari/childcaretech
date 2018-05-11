package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Pullman;

public class GuiPullman extends GuiBaseModel<Pullman> {

    private StringProperty numberplate;

    public GuiPullman(Pullman model) {
        super(model);

        this.numberplate = new SimpleStringProperty(model.getNumberplate());
    }

    public StringProperty numberplateProperty() {
        return numberplate;
    }

}
