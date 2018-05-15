package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiChild;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Child")
@DiscriminatorValue("child")
public class Child extends Person {

    // Serialization
    private static final long serialVersionUID = 6653642585718262873L;

    private Pediatrist pediatrist;

    private Collection<Parent> parents = new ArrayList<>();
    private Collection<Trip> tripsEnrollments = new ArrayList<>();
    private Collection<Pullman> pullmansAssignments = new ArrayList<>();
    private Collection<Stop> stopsPresences = new ArrayList<>();


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
        if (parents == null || parents.size() == 0) throw new InvalidFieldException("Genitori mancanti");
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


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "parents",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "parent_id") }
    )
    public Collection<Parent> getParents() {
        return parents;
    }

    public void setParents(Collection<Parent> parents) {
        this.parents = parents;
    }

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "pediatrist_id")
    public Pediatrist getPediatrist() {
        return pediatrist;
    }

    public void setPediatrist(Pediatrist pediatrist) {
        this.pediatrist = pediatrist;
    }

    @ManyToMany(mappedBy = "childrenEnrollments")
    public Collection<Trip> getTripsEnrollments() {
        return tripsEnrollments;
    }

    public void setTripsEnrollments(Collection<Trip> tripsEnrollments) {
        this.tripsEnrollments = tripsEnrollments;
    }

    @ManyToMany(mappedBy = "childrenAssignments")
    public Collection<Pullman> getPullmansAssignments() {
        return pullmansAssignments;
    }

    public void setPullmansAssignments(Collection<Pullman> pullmansAssignments) {
        this.pullmansAssignments = pullmansAssignments;
    }

    @ManyToMany(mappedBy = "childrenPresences")
    public Collection<Stop> getStopsPresences() {
        return stopsPresences;
    }

    public void setStopsPresences(Collection<Stop> stopsPresences) {
        this.stopsPresences = stopsPresences;
    }

    @Override
    public String toString(){
        return super.toString();
    }


}
