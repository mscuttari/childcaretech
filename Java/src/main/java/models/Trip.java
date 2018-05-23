package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiTrip;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "title"}))
public class Trip extends BaseModel {

    @Transient
    private static final long serialVersionUID = 9041385768903721723L;


    @EmbeddedId
    private TripPK id = new TripPK();


    @OneToMany(mappedBy = "id.trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Stop> stops = new HashSet<>();


    @OneToMany(mappedBy = "id.trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Pullman> transports = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "children_trips_enrollments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "title")
            },
            inverseJoinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Child> children = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "staff_trips_enrollments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "title")
            },
            inverseJoinColumns = { @JoinColumn(name = "staff_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Staff> staff = new HashSet<>();


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
     * @param   title   title of the trip
     */
    public Trip(Date date, String title) {
        setDate(date);
        setTitle(title);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Date
        if (getDate() == null) throw new InvalidFieldException("Data mancante");

        // Title: [a-z] [A-Z] space
        if (getTitle() == null) throw new InvalidFieldException("Titolo mancante");
        if (!getTitle().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Titolo non valido");

        // Check if the total number of seats is enough
        Integer totalNumberOfSeats = getAvailableSeats();

        if (totalNumberOfSeats < getChildren().size() + getStaff().size())
            throw new InvalidFieldException("Posti insufficienti");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiTrip.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {
        // Children
        for (Child child : getChildren()) {
            child.removeTripEnrollment(this);
        }

        // Staff
        for (Staff staff : getStaff()) {
            staff.removeTrip(this);
        }
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
        return this.id.getDate();
    }


    public void setDate(Date date) {
        this.id.setDate(date);
    }


    public String getTitle() {
        return this.id.getTitle();
    }


    public void setTitle(String title) {
        this.id.setTitle(trimString(title));
    }


    public Collection<Stop> getStops() {
        return this.stops;
    }


    public void setStops(Collection<Stop> stops) {
        this.stops = stops;
    }


    public Collection<Pullman> getTransports() {
        return this.transports;
    }


    public void addTransport(Pullman transport) {
        this.transports.add(transport);
    }


    public void addTransports(Collection<Pullman> transports) {
        this.transports.addAll(transports);
    }


    public void setTransports(Collection<Pullman> transports) {
        this.transports.clear();
        addTransports(transports);
    }


    public Integer getAvailableSeats() {
        // TODO: return null if some seats are not specified (example: train)
        Integer totalNumberOfSeats = 0;

        for (Pullman transport : transports){
            totalNumberOfSeats += transport.getSeats();
        }

        return totalNumberOfSeats;
    }


    public Collection<Child> getChildren() {
        return this.children;
    }


    public void addChild(Child child) {
        this.children.add(child);
    }


    public void addChildren(Collection<Child> children) {
        this.children.addAll(children);
    }


    public void setChildren(Collection<Child> children) {
        this.children.clear();
        addChildren(children);
    }


    public Collection<Staff> getStaff() {
        return this.staff;
    }


    public void addStaff(Staff staff) {
        this.staff.add(staff);
    }


    public void addStaff(Collection<Staff> staff) {
        this.staff.addAll(staff);
    }


    public void setStaff(Collection<Staff> staff) {
        this.staff.clear();
        addStaff(staff);
    }

}
