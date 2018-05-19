package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiTrip;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "title"}))
public class Trip extends BaseModel {

    @Transient
    private static final long serialVersionUID = 9041385768903721723L;

    @EmbeddedId
    private TripPK id;

    @OneToMany(mappedBy = "id.trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Stop> stops = new ArrayList<>();

    @OneToMany(mappedBy = "id.trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Pullman> transports = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "children_trips_enrollments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "title")
            },
            inverseJoinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    private Collection<Child> children = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "staff_trips_enrollments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "title")
            },
            inverseJoinColumns = { @JoinColumn(name = "staff_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    private Collection<Staff> staff = new ArrayList<>();


    /**
     * Default constructor
     */
    public Trip() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   date    date the trip has been scheduled for
     * @param   title   name of the trip
     */
    public Trip(Date date, String title) {
        id = new TripPK(date, title);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Date
        if (id.getDate() == null) throw new InvalidFieldException("Data mancante");

        // Title: [a-z] [A-Z] space
        if (id.getTitle() == null || id.getTitle().isEmpty()) throw new InvalidFieldException("Titolo mancante");
        if (!id.getTitle().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Titolo non valido");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiTrip.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;

        Trip that = (Trip) o;
        return dateEquals(getDate(), that.getDate()) &&
                Objects.equals(getTitle(), that.getTitle());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getTitle());
    }


    public Date getDate() {
        return id.getDate();
    }


    public void setDate(Date date) {
        if (this.id.getDate() == null) {
            this.id.setDate(date);
        }
    }


    public String getTitle() {
        return id.getTitle();
    }


    public void setTitle(String title) {
        if (this.id.getTitle() == null) {
            this.id.setTitle(title);
        }
    }


    public Collection<Stop> getStops() {
        return stops;
    }


    public void setStops(Collection<Stop> stops) {
        this.stops = stops;
    }


    public Collection<Pullman> getTransports() {
        return transports;
    }


    public void addTransport(Pullman transport) {
        this.transports.add(transport);
    }


    public void addTransports(Collection<Pullman> transports) {
        this.transports.addAll(transports);
    }


    public Collection<Child> getChildren() {
        return children;
    }


    public void addChild(Child child) {
        this.children.add(child);
    }


    public void addChildren(Collection<Child> children) {
        this.children.addAll(children);
    }


    public Collection<Staff> getStaff() {
        return staff;
    }


    public void addStaff(Staff staff) {
        this.staff.add(staff);
    }


    public void addStaff(Collection<Staff> staff) {
        this.staff.addAll(staff);
    }

}
