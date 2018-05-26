package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiChild;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity(name = "Child")
@Table(name = "children")
@DiscriminatorValue("child")
public class Child extends Person {

    @Transient
    private static final long serialVersionUID = 6653642585718262873L;


    @GenericGenerator(name = "native_generator", strategy = "native")
    @GeneratedValue(generator = "native_generator")
    @Column(name = "child_id")
    private Long id;


    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "pediatrist_fiscal_code", referencedColumnName = "fiscal_code")
    private Pediatrist pediatrist;


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "children_parents",
            joinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "parent_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Parent> parents = new HashSet<>();


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "children_contacts",
            joinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "contact_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Contact> contacts = new HashSet<>();


    @ManyToMany(mappedBy = "children", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Trip> tripsEnrollments = new HashSet<>();


    @ManyToMany(mappedBy = "children", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
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

        setPediatrist(pediatrist);
    }


    /** {@inheritDoc */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        super.checkDataValidity();

        // Parents
        if (getParents().size() > 2)
            throwFieldError("Non è possibile specificare più di due genitori");

        // Pediatrist
        if (getPediatrist() == null)
            throwFieldError("Pediatra mancante");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Bambino";
    }


    /** {@inheritDoc */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiChild.class;
    }


    /** {@inheritDoc */
    @Override
    public boolean isDeletable() {
        return super.isDeletable();
    }


    /** {@inheritDoc */
    @Override
    public void preDelete() {
        super.preDelete();

        // Pediatrist
        if (getPediatrist() != null)
            getPediatrist().removeChild(this);

        // Parents
        for (Parent parent : getParents()) {
            parent.removeChild(this);
        }

        // Contacts
        for (Contact contact : getContacts()) {
            contact.removeChild(this);
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Child)) return false;

        Child that = (Child) obj;
        return Objects.equals(getPediatrist(), that.getPediatrist()) &&
                super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFiscalCode());
    }


    public Long getId() {
        return this.id;
    }


    public Collection<Parent> getParents() {
        return this.parents;
    }


    public void addParent(Parent parent) {
        this.parents.add(parent);
    }


    public void addParents(Collection<Parent> parents) {
        for (Parent parent : parents) {
            addParent(parent);
        }
    }


    public void setParents(Collection<Parent> parents) {
        this.parents.clear();
        addParents(parents);
    }


    public Collection<Contact> getContacts() {
        return this.contacts;
    }


    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }


    public void addContacts(Collection<Contact> contacts) {
        for (Contact contact : contacts) {
            addContact(contact);
        }
    }


    public void setContacts(Collection<Contact> contacts) {
        this.contacts.clear();
        addContacts(contacts);
    }


    public Pediatrist getPediatrist() {
        return this.pediatrist;
    }


    public void setPediatrist(Pediatrist pediatrist) {
        this.pediatrist = pediatrist;
    }


    public Collection<Trip> getTripsEnrollments() {
        return this.tripsEnrollments;
    }


    public void addTripEnrollment(Trip trip) {
        this.tripsEnrollments.add(trip);
    }


    public void addTripEnrollments(Collection<Trip> trips) {
        this.tripsEnrollments.addAll(trips);
    }


    public void removeTripEnrollment(Trip trip) {
        this.tripsEnrollments.remove(trip);
    }


    public void setTripsEnrollments(Collection<Trip> trips) {
        this.tripsEnrollments.clear();
        addTripEnrollments(trips);
    }


    public Collection<Pullman> getPullmansAssignments() {
        return this.pullmansAssignments;
    }


    public void addPullmanAssignment(Pullman pullman) {
        this.pullmansAssignments.add(pullman);
    }


    public void addPullmansAssignments(Collection<Pullman> pullmans) {
        this.pullmansAssignments.addAll(pullmans);
    }


    public void removePullmanAssignment(Pullman pullman) {
        this.pullmansAssignments.remove(pullman);
    }


    public void setPullmansAssignments(Collection<Pullman> pullmans) {
        this.pullmansAssignments.clear();
        addPullmansAssignments(pullmans);
    }

    @Override
    public String toString(){
        return super.toString();
    }

}
