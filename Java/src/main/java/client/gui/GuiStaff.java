package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import main.java.models.Staff;

public class GuiStaff extends GuiPerson<Staff> {

    private StringProperty username;
    private StringProperty password;


    /**
     * Constructor
     *
     * @param   model   staff model
     */
    public GuiStaff(Staff model) {
        super(model);

        this.username = new SimpleStringProperty(model.getUsername());
        this.username.addListener((observable, oldValue, newValue) -> getModel().setUsername(newValue));

        this.password = new SimpleStringProperty(model.getPassword());
        this.password.addListener((observable, oldValue, newValue) -> getModel().setPassword(newValue));
    }


    public StringProperty usernameProperty() {
        return username;
    }


    public StringProperty passwordProperty() {
        return password;
    }

}
