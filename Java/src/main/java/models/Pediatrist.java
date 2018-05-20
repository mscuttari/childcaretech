package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPediatrist;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity(name = "Pediatrist")
@Table(name = "pediatrists")
@DiscriminatorValue("pediatrist")
public class Pediatrist extends Person {

    @Transient
    private static final long serialVersionUID = -2598504963548813092L;


    @OneToMany(mappedBy = "pediatrist", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Child> children = new HashSet<>();


    /**
     * Default constructor
     */
    public Pediatrist(){
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
    public Pediatrist(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPediatrist.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pediatrist)) return false;

        return super.equals(obj);
    }


    public Collection<Child> getChildren() {
        return children;
    }


    public void addChild(Child child) {
        children.add(child);
    }


    public void addChildren(Collection<Child> children) {
        for (Child child : children) {
            addChild(child);
        }
    }


    public void setChildren(Collection<Child> children) {
        this.children.clear();
        addChildren(children);
    }


    @Override
    public String toString(){
        return super.toString();
    }

}
