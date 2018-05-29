package main.java.models;

import main.java.exceptions.InvalidFieldException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@NamedQuery(name = "Person.search", query = "SELECT p FROM Person p WHERE p.fiscalCode = :fiscalCode")
public abstract class Person extends BaseModel {

    @Transient
    private static final long serialVersionUID = -5315181403037638727L;


    @Id
    @Column(name = "fiscal_code", nullable = false)
    private String fiscalCode;


    @Column(name = "first_name", nullable = false)
    private String firstName;


    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "birthdate")
    private Date birthDate;


    @Column(name = "address")
    private String address;


    @Column(name = "telephone")
    private String telephone;


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "allergies",
            joinColumns = { @JoinColumn(name = "person_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Ingredient> allergies = new HashSet<>();


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "intolerances",
            joinColumns = { @JoinColumn(name = "person_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Ingredient> intolerances = new HashSet<>();


    /**
     * Default constructor
     */
    public Person() {
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
    public Person(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        setFiscalCode(fiscalCode);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthdate(birthDate);
        setAddress(address);
        setTelephone(telephone);
    }


    /** {@inheritDoc} */
    @Override
    public String getSearchQueryName() {
        return "Person.search";
    }


    /** {@inheritDoc} */
    @Override
    public boolean runSearchQuery(Query query) {
        query.setParameter("fiscalCode", getFiscalCode());
        return !query.getResultList().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Fiscal code: [A-Z] [0-9] 16 chars length
        if (getFiscalCode() == null)
            throwFieldError("Codice fiscale mancante");

        if (getFiscalCode().length() != 16)
            throwFieldError("Codice fiscale di lunghezza non valida");

        if (!getFiscalCode().matches("^[A-Z\\d]+$"))
            throwFieldError("Codice fiscale non valido");

        // First name: [a-z] [A-Z] à è é ì ò ù ' " space
        if (getFirstName() == null)
            throwFieldError("Nome mancante");

        if (!getFirstName().matches("^[a-zA-Zàèéìòù'\"\\040]+$"))
            throwFieldError("Nome non valido");

        // Last name: [a-z] [A-Z] à è é ì ò ù ' " space
        if (getLastName() == null)
            throwFieldError("Cognome mancante");

        if (!getLastName().matches("^[a-zA-Zàèéìòù'\"\\040]+$"))
            throwFieldError("Cognome non valido");

        // Date
        if (getBirthdate() == null)
            throwFieldError("Data di nascita mancante");

        // Address: [a-z] [A-Z] [0-9] à è é ì ò ù ' " space . , ; \ / °
        if (getAddress() != null && !getAddress().matches("^$|^[a-zA-Zàèéìòù'\"\\d\\040.,;°\\\\\\/()]+$"))
            throwFieldError("Indirizzo non valido");

        // Telephone: [0-9] space + - / \
        if (getTelephone() != null && !getTelephone().matches("^$|^[\\d\\040+-\\/\\\\]+$"))
            throwFieldError("Telefono non valido");
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Person)) return false;

        Person that = (Person)obj;
        return Objects.equals(getFiscalCode(), that.getFiscalCode()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                dateEquals(getBirthdate(), that.getBirthdate()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getTelephone(), that.getTelephone());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFiscalCode());
    }


    public String getFiscalCode() {
        return this.fiscalCode;
    }


    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = trimString(fiscalCode);
    }


    public String getFirstName() {
        return this.firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = trimString(firstName);
    }


    public String getLastName() {
        return this.lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = trimString(lastName);
    }


    public Date getBirthdate() {
        return this.birthDate;
    }


    public void setBirthdate(Date birthdate) {
        this.birthDate = birthdate;
    }


    public String getAddress() {
        return this.address;
    }


    public void setAddress(String address) {
        this.address = trimString(address);
    }


    public String getTelephone() {
        return this.telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = trimString(telephone);
    }


    public Collection<Ingredient> getAllergies() {
        return this.allergies;
    }


    public void addAllergy(Ingredient ingredient) {
        allergies.add(ingredient);
    }


    public void addAllergies(Collection<Ingredient> ingredients) {
        allergies.addAll(ingredients);
    }


    public void setAllergies(Collection<Ingredient> ingredients) {
        this.allergies.clear();
        addAllergies(ingredients);
    }


    public Collection<Ingredient> getIntolerances() {
        return this.intolerances;
    }


    public void addIntolerance(Ingredient ingredient) {
        intolerances.add(ingredient);
    }


    public void addIntolerances(Collection<Ingredient> ingredients) {
        intolerances.addAll(ingredients);
    }


    public void setIntolerances(Collection<Ingredient> intolerances) {
        this.intolerances.clear();
        addIntolerances(intolerances);
    }


    @Override
    public String toString(){
        return "[" + getFiscalCode() + "] - " + getFirstName() + " " + getLastName() ;
    }

}
