package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStaff;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity(name = "Staff")
@Table(name = "staff")
@DiscriminatorValue("staff")
public class Staff extends Person {

    @Transient
    private static final long serialVersionUID = -3026738919615064997L;


    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "password")
    private String password;


    @OneToMany(mappedBy = "responsible", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Menu> menus = new HashSet<>();


    @ManyToMany(mappedBy = "staff", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Trip> trips = new HashSet<>();


    /**
     * Default constructor
     */
    public Staff(){
        this(null, null, null, null, null, null, null, null);
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
     * @param   username        username for login
     * @param   password        password for login
     */
    public Staff(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone, String username, String password) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);

        setUsername(username);
        setPassword(password);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        super.checkDataValidity();
        
        // Username
        if (getUsername() == null)
            throwFieldError("Username mancante");

        // Password
        if (getPassword() == null)
            throwFieldError("Password mancante");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Staff";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStaff.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return super.isDeletable() &&
                getMenus().isEmpty() &&
                getTrips().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {
        super.preDelete();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Staff)) return false;

        Staff that = (Staff)obj;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                super.equals(obj);
    }


    @Override
    public int hashCode() {
        return Objects.hash(getFiscalCode());
    }


    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }


    public String getUsername() {
        return this.username;
    }


    public void setUsername(String username) {
        this.username = trimString(username);
    }


    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = trimString(password);
    }


    public Collection<Menu> getMenus() {
        return this.menus;
    }


    public void addMenu(Menu menu) {
        this.menus.add(menu);
    }


    public void addMenus(Collection<Menu> menus) {
        this.menus.addAll(menus);
    }


    public void removeMenu(Menu menu) {
        this.menus.remove(menu);
    }


    public void setMenus(Collection<Menu> menus) {
        this.menus.clear();
        addMenus(menus);
    }


    public Collection<Trip> getTrips() {
        return this.trips;
    }


    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }


    public void addTrips(Collection<Trip> trips) {
        this.trips.addAll(trips);
    }


    public void removeTrip(Trip trip) {
        this.trips.remove(trip);
    }


    public void setTrips(Collection<Trip> trips) {
        this.trips.clear();
        addTrips(trips);
    }

}
