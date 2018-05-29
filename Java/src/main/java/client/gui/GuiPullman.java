package main.java.client.gui;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.java.models.Pullman;

public class GuiPullman extends GuiBaseModel<Pullman> {

    private StringProperty id;
    private IntegerProperty seats;


    /**
     * Constructor
     *
     * @param   model   pullman model
     */
    public GuiPullman(Pullman model) {
        super(model);

        this.id = new SimpleStringProperty(model.getId());
        this.id.addListener((observable, oldValue, newValue) -> getModel().setId(newValue));
        this.seats = new SimpleIntegerProperty(model.getSeats());
        this.seats.addListener((observable, oldValue, newValue) -> getModel().setSeats(newValue.intValue()));
    }

    @Override
    public String toString() {
        return  idProperty().getValue() + " (" +
                seatsProperty().getValue() + ")";
    }


    public StringProperty idProperty() {
        return id;
    }


    public IntegerProperty seatsProperty() {
        return seats;
    }

}
