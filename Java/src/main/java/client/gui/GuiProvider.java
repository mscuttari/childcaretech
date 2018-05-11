package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Provider;

public class GuiProvider extends GuiBaseModel<Provider> {

    private StringProperty vat;
    private StringProperty name;

    public GuiProvider(Provider model) {
        super(model);

        this.vat = new SimpleStringProperty(model.getVat());
        this.name = new SimpleStringProperty(model.getName());
    }

    public StringProperty vatProperty() {
        return vat;
    }

    public StringProperty nameProperty() {
        return name;
    }

}
