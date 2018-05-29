package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Trip;

import java.util.Date;

public class GuiTrip extends GuiBaseModel<Trip> {

    private ObjectProperty<Date> date;
    private StringProperty title;


    /**
     * Constructor
     *
     * @param   model   trip model
     */
    public GuiTrip(Trip model) {
        super(model);

        this.date = new SimpleObjectProperty<>(model.getDate());
        this.date.addListener((observable, oldValue, newValue) -> getModel().setDate(newValue));

        this.title = new SimpleStringProperty(model.getTitle());
        this.title.addListener((observable, oldValue, newValue) -> getModel().setTitle(newValue));
    }


    public ObjectProperty<Date> dateProperty() {
        return date;
    }


    public StringProperty titleProperty() {
        return title;
    }

}
