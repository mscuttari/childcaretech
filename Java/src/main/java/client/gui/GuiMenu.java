package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.DayOfTheWeek;
import main.java.models.Menu;
import main.java.models.Staff;

public class GuiMenu extends GuiBaseModel<Menu> {

    private StringProperty name;
    private ObjectProperty<DayOfTheWeek> dayOfTheWeek;
    private ObjectProperty<Staff> responsible;


    /**
     * Constructor
     *
     * @param   model   menu model
     */
    public GuiMenu(Menu model) {
        super(model);

        this.name = new SimpleStringProperty(model.getName());
        this.name.addListener((observable, oldValue, newValue) -> getModel().setName(newValue));

        this.dayOfTheWeek = new SimpleObjectProperty<>(model.getDayOfTheWeek());
        this.dayOfTheWeek.addListener((observable, oldValue, newValue) -> getModel().setDayOfTheWeek(newValue));

        this.responsible = new SimpleObjectProperty<>(model.getResponsible());
        this.responsible.addListener((observable, oldValue, newValue) -> getModel().setResponsible(newValue));
    }


    public StringProperty nameProperty() {
        return name;
    }


    public ObjectProperty<DayOfTheWeek> dayOfTheWeekProperty() {
        return dayOfTheWeek;
    }


    public ObjectProperty<Staff> responsibleProperty() {
        return responsible;
    }

}
