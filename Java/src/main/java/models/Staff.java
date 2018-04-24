package main.java.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Staff")
@DiscriminatorValue("staff")
public final class Staff extends Person {

    private String username;
    private String password;

    private Collection<Menu> menuResponsibility = new ArrayList<>();
    private Collection<Trip> tripsEnrollments = new ArrayList<>();

    public Staff(){
        this(null, null, null, null, null, null, null, null);
    }

    public Staff(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone, String username, String password) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
        this.username = username;
        this.password = password;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
