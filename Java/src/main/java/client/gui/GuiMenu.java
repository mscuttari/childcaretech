package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.DayOfTheWeek;
import main.java.models.Menu;

public class GuiMenu<M extends Menu> extends GuiBaseModel<M> {

    private StringProperty name;
    private ObjectProperty<DayOfTheWeek> dayOfTheWeek;

    public GuiMenu(M model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.dayOfTheWeek = new SimpleObjectProperty<>(model.getDayOfTheWeek());
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ObjectProperty<DayOfTheWeek> dayOfTheWeekProperty() {
        return dayOfTheWeek;
    }

}
