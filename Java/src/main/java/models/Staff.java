package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStaff;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Staff")
@Table(name = "staff")
@DiscriminatorValue("staff")
public class Staff extends Person {

    @Transient
    private static final long serialVersionUID = -3026738919615064997L;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "responsible")
    private Collection<Menu> menus = new ArrayList<>();

    @ManyToMany(mappedBy = "staff")
    private Collection<Trip> trips = new ArrayList<>();


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

        this.username = username;
        this.password = password;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        super.checkDataValidity();
        
        // Username
        if (username == null || username.isEmpty()) throw new InvalidFieldException("Username mancante");

        // Password
        if (password == null || password.isEmpty()) throw new InvalidFieldException("Password mancante");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStaff.class;
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


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username == null || username.isEmpty() ? null : username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password == null || password.isEmpty() ? null : password;
    }


    public Collection<Menu> getMenus() {
        return menus;
    }


    public void addMenu(Menu menu) {
        this.menus.add(menu);
    }


    public void addMenus(Collection<Menu> menus) {
        this.menus.addAll(menus);
    }


    public Collection<Trip> getTripsEnrollments() {
        return trips;
    }


    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }


    public void addTrips(Collection<Trip> trips) {
        this.trips.addAll(trips);
    }

}
