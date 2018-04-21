package main.java.models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "people", uniqueConstraints = {@UniqueConstraint(columnNames = "fiscal_code")})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Person extends BaseModel {

    protected Long id;
    protected String fiscalCode;
    protected String firstName;
    protected String lastName;
    protected Date birthdate;
    protected String address;
    protected String telephone;

    private Collection<Ingredient> allergies = new ArrayList<>();
    private Collection<Ingredient> intollerances = new ArrayList<>();
    private Collection<Person> parents = new ArrayList<>();
    private Collection<Person> children = new ArrayList<>();
    private Collection<Person> contacts = new ArrayList<>();
    private Collection<Person> bounds = new ArrayList<>();
    private Person pediatrist;
    private Collection<Person> curing = new ArrayList<>();
    private Collection<Menu> menuResponsibility = new ArrayList<>();
    private Collection<Trip> childTripsEnrollments = new ArrayList<>();
    private Collection<Trip> staffTripsEnrollments = new ArrayList<>();
    private Collection<Stop> childStopsPresences = new ArrayList<>();
    private Collection<Pullman> childPullmansAssignments = new ArrayList<>();

    @Id
    @GenericGenerator(name = "native_generator", strategy = "native")
    @GeneratedValue(generator = "native_generator")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "fiscal_code", nullable = false)
    public String getFiscalCode() {
        return fiscalCode;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    @Column(name = "name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "surname", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate")
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "allergies",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    public Collection<Ingredient> getAllergies() {
        return allergies;
    }

    public void setAllergies(Collection<Ingredient> allergies) {
        this.allergies = allergies;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "intollerances",
            joinColumns = { @JoinColumn(name = "person_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    public Collection<Ingredient> getIntollerances() {
        return intollerances;
    }

    public void setIntollerances(Collection<Ingredient> intollerances) {
        this.intollerances = intollerances;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "parents",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "parent_id") }
    )
    public Collection<Person> getParents() {
        return parents;
    }

    public void setParents(Collection<Person> parents) {
        this.parents = parents;
    }

    @ManyToMany(mappedBy = "parents")
    public Collection<Person> getChildren() {
        return children;
    }

    public void setChildren(Collection<Person> children) {
        this.children = children;
    }

    @ManyToMany
    @JoinTable(
            name = "contacts",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "contact_id") }
    )
    public Collection<Person> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Person> contacts) {
        this.contacts = contacts;
    }

    @ManyToMany(mappedBy = "contacts")
    public Collection<Person> getBounds() {
        return bounds;
    }

    public void setBounds(Collection<Person> bounds) {
        this.bounds = bounds;
    }

    @ManyToOne
    @JoinColumn(name = "pediatrist_id")
    public Person getPediatrist() {
        return pediatrist;
    }

    public void setPediatrist(Person pediatrist) {
        this.pediatrist = pediatrist;
    }

    @OneToMany(mappedBy = "pediatrist")
    public Collection<Person> getCuring() {
        return curing;
    }

    public void setCuring(Collection<Person> curing) {
        this.curing = curing;
    }

    @OneToMany(mappedBy = "responsible")
    public Collection<Menu> getMenuResponsibility() {
        return menuResponsibility;
    }

    public void setMenuResponsibility(Collection<Menu> menuResponsibility) {
        this.menuResponsibility = menuResponsibility;
    }

    @ManyToMany(mappedBy = "childrenEnrollments")
    public Collection<Trip> getChildTripsEnrollments() {
        return childTripsEnrollments;
    }

    public void setChildTripsEnrollments(Collection<Trip> childTripsEnrollments) {
        this.childTripsEnrollments = childTripsEnrollments;
    }

    @ManyToMany(mappedBy = "staffEnrollments")
    public Collection<Trip> getStaffTripsEnrollments() {
        return staffTripsEnrollments;
    }

    public void setStaffTripsEnrollments(Collection<Trip> staffTripsEnrollments) {
        this.staffTripsEnrollments = staffTripsEnrollments;
    }

    @Transient
    public Collection<Trip> getTripEnrollments() {
        return this instanceof Child ? getChildTripsEnrollments() : getStaffTripsEnrollments();
    }

    @Transient
    public void setTripEnrollments(Collection<Trip> trips) {
        if (this instanceof Child) {
            setChildTripsEnrollments(trips);
        } else {
            setStaffTripsEnrollments(trips);
        }
    }

    @ManyToMany(mappedBy = "childrenPresences")
    public Collection<Stop> getChildStopsPresences() {
        return childStopsPresences;
    }

    public void setChildStopsPresences(Collection<Stop> childStopsPresences) {
        this.childStopsPresences = childStopsPresences;
    }

    @ManyToMany(mappedBy = "childrenAssignments")
    public Collection<Pullman> getChildPullmansAssignments() {
        return childPullmansAssignments;
    }

    public void setChildPullmansAssignments(Collection<Pullman> childPullmansAssignments) {
        this.childPullmansAssignments = childPullmansAssignments;
    }

}
