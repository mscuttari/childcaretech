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

    private Collection<Person> bounds = new ArrayList<>();


    /**
     * Default constructor
     */
    public Contact(){
        this(null, null, null, null, null, null);
    }


    /**
     * Constructor
     *
     * @param   fiscalCode      fiscal code
     * @param   firstName       first name
     * @param   lastName        last name
     * @param   birthDate       birth date
     * @param   address         address
     * @param   telephone       telephone
     */
    public Contact(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiContact.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contact)) return false;

        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @ManyToMany(mappedBy = "contacts")
    public Collection<Person> getBounds() {
        return bounds;
    }

    public void setBounds(Collection<Person> bounds) {
        this.bounds = bounds;
    }

}
