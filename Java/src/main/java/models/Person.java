package main.java.models;

import main.java.client.InvalidFieldException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.naming.OperationNotSupportedException;
import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "people")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "allergies",
            joinColumns = { @JoinColumn(name = "person_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    private Collection<Ingredient> allergies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "intolerances",
            joinColumns = { @JoinColumn(name = "person_fiscal_code", referencedColumnName = "fiscal_code") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    private Collection<Ingredient> intolerances = new ArrayList<>();


    @ManyToMany(mappedBy = "people")
    private Collection<AlternativeMenu> personalizedMenus = new ArrayList<>();


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
        this.fiscalCode = fiscalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.telephone = telephone;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Fiscal code: [A-Z] [0-9] 16 chars length
        if (fiscalCode == null || fiscalCode.isEmpty()) throw new InvalidFieldException("Codice fiscale mancante");
        if (fiscalCode.length() != 16) throw new InvalidFieldException("Codice fiscale non valido");
        if (!fiscalCode.matches("^[A-Z\\d]+$")) throw new InvalidFieldException("Codice fiscale non valido");

        // First name: [a-z] [A-Z] space
        if (firstName == null || firstName.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!firstName.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Last name: [a-z] [A-Z] space
        if (lastName == null || lastName.isEmpty()) throw new InvalidFieldException("Cognome mancante");
        if (!lastName.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Cognome non valido");

        // Date
        if (birthDate == null) throw new InvalidFieldException("Data di nascita mancante");

        // Address: [a-z] [A-Z] [0-9] space . , ; \ / °
        if (address != null && !address.matches("^$|^[a-zA-Z\\d\\040.,;°\\\\\\/]+$")) throw new InvalidFieldException("Indirizzo non valido");

        // Telephone: [0-9] space +
        if (telephone != null && !telephone.matches("^$|^[\\d\\040+]+$")) throw new InvalidFieldException("Telefono non valido");
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
        return fiscalCode;
    }


    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode == null || fiscalCode.isEmpty() ? null : fiscalCode;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName == null || firstName.isEmpty() ? null : firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName == null || lastName.isEmpty() ? null : lastName;
    }


    public Date getBirthdate() {
        return birthDate;
    }


    public void setBirthdate(Date birthdate) {
        this.birthDate = birthdate;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address == null || address.isEmpty() ? null : address;
    }


    public String getTelephone() {
        return telephone;
    }


    public void setTelephone(String telephone) {
        this.telephone = telephone == null || telephone.isEmpty() ? null : telephone;
    }


    public Collection<Ingredient> getAllergies() {
        return allergies;
    }


    public void addAllergy(Ingredient ingredient) {
        allergies.add(ingredient);
    }


    public void addAllergies(Collection<Ingredient> ingredients) {
        allergies.addAll(ingredients);
    }


    public Collection<Ingredient> getIntolerances() {
        return intolerances;
    }


    public void addIntolerance(Ingredient ingredient) {
        intolerances.add(ingredient);
    }


    public void addIntollerances(Collection<Ingredient> ingredients) {
        intolerances.addAll(ingredients);
    }


    public Collection<AlternativeMenu> getPersonalizedMenus() {
        return personalizedMenus;
    }


    public void addPersonalizedMenu(AlternativeMenu menu) {
        this.personalizedMenus.add(menu);
    }


    public void addPersonalizedMenus(Collection<AlternativeMenu> personalizedMenus) {
        this.personalizedMenus.addAll(personalizedMenus);
    }

    @Override
    public String toString(){
        return "(" + getFiscalCode() + ")" + getFirstName() + getLastName() ;
    }

}
