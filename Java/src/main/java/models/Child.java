package main.java.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Child")
@DiscriminatorValue("child")
public final class Child extends Person {

    private Pediatrist pediatrist;

    private Collection<Parent> parents = new ArrayList<>();
    private Collection<Trip> tripsEnrollments = new ArrayList<>();
    private Collection<Pullman> pullmansAssignments = new ArrayList<>();
    private Collection<Stop> stopsPresences = new ArrayList<>();

    public Child() {
        this(null, null, null, null, null, null,null);
    }

    public Child(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone, Pediatrist pediatrist) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
        this.pediatrist = pediatrist;
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

}
