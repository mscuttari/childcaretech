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

    public GuiTrip(Trip model) {
        super(model);

        this.date = new SimpleObjectProperty<>(model.getDate());
        this.title = new SimpleStringProperty(model.getTitle());
    }

    public ObjectProperty<Date> dateProperty() {
        return date;
    }

    public StringProperty titleProperty() {
        return title;
    }

}
