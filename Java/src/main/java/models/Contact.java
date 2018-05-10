package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiContact;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Contact")
@DiscriminatorValue("contact")
public class Contact extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiContact.class;
    }

    private Collection<Person> bounds = new ArrayList<>();

    public Contact(){
        this(null, null, null, null, null, null);
    }

    public Contact(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }

    @ManyToMany(mappedBy = "contacts")
    public Collection<Person> getBounds() {
        return bounds;
    }

    public void setBounds(Collection<Person> bounds) {
        this.bounds = bounds;
    }

}
