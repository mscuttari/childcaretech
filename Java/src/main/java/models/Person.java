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
    protected Date birthDate;
    protected String address;
    protected String telephone;

    protected Collection<Ingredient> allergies = new ArrayList<>();
    protected Collection<Ingredient> intollerances = new ArrayList<>();
    protected Collection<Contact> contacts = new ArrayList<>();

    public Person() {
        this(null, null, null, null, null, null);
    }

    public Person(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        this.fiscalCode = fiscalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.telephone = telephone;
    }

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
        return birthDate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthDate = birthdate;
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
            name = "contacts",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "contact_id") }
    )
    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }

}
