package main.java.client.gui;

import javafx.beans.property.*;
import main.java.models.Place;
import main.java.models.Stop;
import main.java.models.Trip;

public class GuiStop extends GuiBaseModel<Stop> {

    private ObjectProperty<Trip> trip;
    private ObjectProperty<Place> place;
    private IntegerProperty number;


    /**
     * Constructor
     *
     * @param   model   stop model
     */
    public GuiStop(Stop model) {
        super(model);

        this.trip = new SimpleObjectProperty<>(model.getTrip());
        this.trip.addListener((observable, oldValue, newValue) -> getModel().setTrip(newValue));

        this.place = new SimpleObjectProperty<>(model.getPlace());
        this.place.addListener((observable, oldValue, newValue) -> getModel().setPlace(newValue));

        this.number = new SimpleIntegerProperty(model.getNumber());
        this.number.addListener((observable, oldValue, newValue) -> getModel().setNumber(newValue.intValue()));
    }


    @Override
    public String toString() {
        return "#" + numberProperty().getValue() + "   -   " +
                placeProperty().getValue().getName() + ", " +
                "(" + placeProperty().getValue().getProvince() + ", " +
                placeProperty().getValue().getNation() + ")";
    }


    public ObjectProperty<Trip> tripProperty() {
        return this.trip;
    }


    public ObjectProperty<Place> placeProperty() {
        return this.place;
    }


    public IntegerProperty numberProperty() {
        return this.number;
    }

}