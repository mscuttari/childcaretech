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


    /**
     * Constructor
     *
     * @param   model   person model
     */
    public GuiPerson(M model) {
        super(model);

        this.fiscalCode = new SimpleStringProperty(model.getFiscalCode());
        this.fiscalCode.addListener((observable, oldValue, newValue) -> getModel().setFiscalCode(newValue));

        this.firstName = new SimpleStringProperty(model.getFirstName());
        this.firstName.addListener((observable, oldValue, newValue) -> getModel().setFirstName(newValue));

        this.lastName = new SimpleStringProperty(model.getLastName());
        this.lastName.addListener((observable, oldValue, newValue) -> getModel().setLastName(newValue));

        this.birthDate = new SimpleObjectProperty<>(model.getBirthdate());
        this.birthDate.addListener((observable, oldValue, newValue) -> getModel().setBirthdate(newValue));

        this.address = new SimpleStringProperty(model.getAddress());
        this.address.addListener((observable, oldValue, newValue) -> getModel().setAddress(newValue));

        this.telephone = new SimpleStringProperty(model.getTelephone());
        this.telephone.addListener((observable, oldValue, newValue) -> getModel().setTelephone(newValue));
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
