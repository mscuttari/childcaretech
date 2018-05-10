package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Staff;

public class GuiStaff extends GuiPerson<Staff> {

    private StringProperty username;
    private StringProperty password;

    public GuiStaff(Staff model) {
        super(model);

        this.username = new SimpleStringProperty(model.getUsername());
        this.password = new SimpleStringProperty(model.getPassword());
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

}
