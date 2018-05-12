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
@DiscriminatorValue("staff")
public class Staff extends Person {

    // Serialization
    private static final long serialVersionUID = -3026738919615064997L;

    private String username;
    private String password;

    private Collection<Menu> menuResponsibility = new ArrayList<>();
    private Collection<Trip> tripsEnrollments = new ArrayList<>();


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

        // TODO: username and password management
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


    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), super.hashCode());
    }


    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null || username.isEmpty() ? null : username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null || password.isEmpty() ? null : password;
    }

    @OneToMany(mappedBy = "responsible")
    public Collection<Menu> getMenuResponsibility() {
        return menuResponsibility;
    }

    public void setMenuResponsibility(Collection<Menu> menuResponsibility) {
        this.menuResponsibility = menuResponsibility;
    }

    @ManyToMany(mappedBy = "staffEnrollments")
    public Collection<Trip> getTripsEnrollments() {
        return tripsEnrollments;
    }

    public void setTripsEnrollments(Collection<Trip> tripsEnrollments) {
        this.tripsEnrollments = tripsEnrollments;
    }

}
