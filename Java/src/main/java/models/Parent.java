package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiParent;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Parent")
@DiscriminatorValue("parent")
public class Parent extends Person {

    // Serialization
    private static final long serialVersionUID = -8335298083415447342L;

    private Collection<Child> children = new ArrayList<>();


    /**
     * Default constructor
     */
    public Parent(){
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
    public Parent(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiParent.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Parent)) return false;

        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @ManyToMany(mappedBy = "parents", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Collection<Child> getChildren() {
        return children;
    }

    private void setChildren(Collection<Child> children) {
        this.children = children;
    }

    public void addChild(Child child) {
        children.add(child);
        child.getParents().add(this);
    }

}
