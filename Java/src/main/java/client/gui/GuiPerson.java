package main.java.client.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Person;

import java.util.Date;

public abstract class GuiPerson<M extends Person> extends GuiBaseModel<M> {

    private StringProperty fiscalCode;
    private StringProperty firstName;
    private StringProperty lastName;
    private ObjectProperty<Date> birthDate;
    private StringProperty address;
    private StringProperty telephone;

    public GuiPerson(M model) {
        super(model);

        this.fiscalCode = new SimpleStringProperty(model.getFiscalCode());
        this.firstName = new SimpleStringProperty(model.getFirstName());
        this.lastName = new SimpleStringProperty(model.getLastName());
        this.birthDate = new SimpleObjectProperty<>(model.getBirthdate());
        this.address = new SimpleStringProperty(model.getAddress());
        this.telephone = new SimpleStringProperty(model.getTelephone());
    }

    @Override
    public M getModel() {
        return super.getModel();
    }

    public StringProperty fiscalCodeProperty() {
        return fiscalCode;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public ObjectProperty<Date> birthDateProperty() {
        return birthDate;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

}
