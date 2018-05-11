package main.java.models;

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

@Entity(name = "Staff")
@DiscriminatorValue("staff")
public class Staff extends Person {

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

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStaff.class;
    }

}
