package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiContact;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity(name = "Contact")
@Table(name = "contacts")
@DiscriminatorValue("contact")
public class Contact extends Person {

    @Transient
    private static final long serialVersionUID = 7139409983483815073L;


    @ManyToMany(mappedBy = "contacts", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Child> children = new HashSet<>();


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
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        super.checkDataValidity();
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Contatto";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiContact.class;
    }


    /** {@inheritDoc */
    @Override
    public boolean isDeletable() {
        return super.isDeletable();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {

        // Children
        for (Child child : getChildren()) {
            child.removeContact(this);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contact)) return false;

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFiscalCode());
    }


    @Override
    public String toString() {
        String result = "";

        result += getFirstName() + " " + getLastName();

        if (getTelephone() != null)
            result += " - Tel: " + getTelephone();

        return result;
    }


    public Collection<Child> getChildren() {
        return this.children;
    }


    public void addChild(Child child) {
        this.children.add(child);
        child.addContact(this);
    }


    public void addChildren(Collection<Child> children) {
        for (Child child : children)
            addChild(child);
    }


    public void removeChild(Child child) {
        this.children.remove(child);
    }


    public void setChildren(Collection<Child> children) {
        this.children.clear();
        addChildren(children);
    }

}
