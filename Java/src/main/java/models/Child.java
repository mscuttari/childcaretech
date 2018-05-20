package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiChild;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Child")
@Table(name = "children")
@DiscriminatorValue("child")
public class Child extends Person {

    @Transient
    private static final long serialVersionUID = 6653642585718262873L;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "pediatrist_fiscal_code", referencedColumnName = "fiscal_code")
    private Pediatrist pediatrist;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "children_parents",
            joinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "parent_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    private Collection<Parent> parents = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "children_contacts",
            joinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "contact_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    private Collection<Contact> contacts = new HashSet<>();

    @ManyToMany(mappedBy = "children")
    private Collection<Trip> tripsEnrollments = new HashSet<>();

    @ManyToMany(mappedBy = "children")
    private Collection<Pullman> pullmansAssignments = new HashSet<>();


    /**
     * Default constructor
     */
    public Child() {
        this(null, null, null, null, null, null,null);
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
     * @param   pediatrist      pediatrist
     */
    public Child(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone, Pediatrist pediatrist) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);

        this.pediatrist = pediatrist;
    }


    /** {@inheritDoc */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        super.checkDataValidity();

        // Parents
        if (parents.size() > 2) throw new InvalidFieldException("Non è possibile specificare più di due genitori");

        // Pediatrist
        if (pediatrist == null) throw new InvalidFieldException("Pediatra mancante");
    }


    /** {@inheritDoc */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiChild.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Child)) return false;

        Child that = (Child) obj;
        return Objects.equals(getPediatrist(), that.getPediatrist()) &&
                super.equals(obj);
    }


    public Collection<Parent> getParents() {
        return parents;
    }


    public void addParent(Parent parent) {
        this.parents.add(parent);
    }


    public void addParents(Collection<Parent> parents) {
        for (Parent parent : parents) {
            addParent(parent);
        }
    }


    public Collection<Contact> getContacts() {
        return contacts;
    }


    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }


    public void addContacts(Collection<Contact> contacts) {
        for (Contact contact : contacts) {
            addContact(contact);
        }
    }


    public Pediatrist getPediatrist() {
        return this.pediatrist;
    }


    public void setPediatrist(Pediatrist pediatrist) {
        this.pediatrist = pediatrist;
    }


    public Collection<Trip> getTripsEnrollments() {
        return tripsEnrollments;
    }


    public void addTripEnrollment(Trip trip) {
        this.tripsEnrollments.add(trip);
    }


    public void addTripEnrollments(Collection<Trip> trips) {
        this.tripsEnrollments.addAll(trips);
    }


    public Collection<Pullman> getPullmansAssignments() {
        return pullmansAssignments;
    }


    public void addPullmanAssignment(Pullman pullman) {
        this.pullmansAssignments.add(pullman);
    }


    public void addPullmansAssignments(Collection<Pullman> pullmans) {
        this.pullmansAssignments.addAll(pullmans);
    }

    @Override
    public String toString(){
        return super.toString();
    }

}
