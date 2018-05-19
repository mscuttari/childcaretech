package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiContact;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Contact")
@Table(name = "contacts")
@DiscriminatorValue("contact")
public class Contact extends Person {

    @Transient
    private static final long serialVersionUID = 7139409983483815073L;

    @ManyToMany(mappedBy = "contacts")
    private Collection<Child> children = new ArrayList<>();


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


    public Collection<Child> getChildren() {
        return children;
    }


    public void addChild(Child child) {
        this.children.add(child);
    }


    public void addChildren(Collection<Child> children) {
        this.children.addAll(children);
    }

}
