package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Pullman;

public class GuiPullman extends GuiBaseModel<Pullman> {

    private StringProperty numberplate;


    /**
     * Constructor
     *
     * @param   model   pullman model
     */
    public GuiPullman(Pullman model) {
        super(model);

        this.numberplate = new SimpleStringProperty(model.getNumberplate());
        this.numberplate.addListener((observable, oldValue, newValue) -> getModel().setNumberplate(newValue));
    }


    public StringProperty numberplateProperty() {
        return numberplate;
    }

}
