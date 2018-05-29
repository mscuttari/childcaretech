package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Provider;

public class GuiProvider extends GuiBaseModel<Provider> {

    private StringProperty vat;
    private StringProperty name;


    /**
     * Constructor
     *
     * @param   model   provider model
     */
    public GuiProvider(Provider model) {
        super(model);

        this.vat = new SimpleStringProperty(model.getVat());
        this.vat.addListener((observable, oldValue, newValue) -> getModel().setVat(newValue));

        this.name = new SimpleStringProperty(model.getName());
        this.name.addListener((observable, oldValue, newValue) -> getModel().setName(newValue));
    }


    public StringProperty vatProperty() {
        return vat;
    }


    public StringProperty nameProperty() {
        return name;
    }

}
