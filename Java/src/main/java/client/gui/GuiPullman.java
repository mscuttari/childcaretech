package main.java.client.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Pullman;

public class GuiPullman extends GuiBaseModel<Pullman> {

    private StringProperty numberplate;
    private IntegerProperty seats;


    /**
     * Constructor
     *
     * @param   model   pullman model
     */
    public GuiPullman(Pullman model) {
        super(model);

        this.numberplate = new SimpleStringProperty(model.getNumberplate());
        this.numberplate.addListener((observable, oldValue, newValue) -> getModel().setNumberplate(newValue));
        this.seats = new SimpleIntegerProperty(model.getSeats());
    }


    public StringProperty numberplateProperty() {
        return numberplate;
    }

    public IntegerProperty seatsProperty() {
        return seats;
    }

}
