package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiTrip;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "title"}))
public class Trip extends BaseModel {

    // Serialization
    private static final long serialVersionUID = 9041385768903721723L;

    private Long id;
    private Date date;
    private String title;

    private Collection<Stop> stops = new ArrayList<>();
    private Collection<Pullman> transports = new ArrayList<>();
    private Collection<Child> childrenEnrollments = new ArrayList<>();
    private Collection<Staff> staffEnrollments = new ArrayList<>();


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
        this.date = date;
        this.title = title;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Date
        if (date == null) throw new InvalidFieldException("Data mancante");

        // Title: [a-z] [A-Z] space
        if (title == null || title.isEmpty()) throw new InvalidFieldException("Titolo mancante");
        if (!title.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Titolo non valido");
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
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTitle(), that.getTitle());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getTitle());
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

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null || title.isEmpty() ? null : title;
    }

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Collection<Stop> getStops() {
        return stops;
    }

    public void setStops(Collection<Stop> stops) {
        this.stops = stops;
    }

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Collection<Pullman> getTransports() {
        return transports;
    }

    public void setTransports(Collection<Pullman> transports) {
        this.transports = transports;
    }

    @ManyToMany
    @JoinTable(
            name = "children_trips_enrollments",
            joinColumns = { @JoinColumn(name = "trip_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    public Collection<Child> getChildrenEnrollments() {
        return childrenEnrollments;
    }

    public void setChildrenEnrollments(Collection<Child> childrenEnrollments) {
        this.childrenEnrollments = childrenEnrollments;
    }

    @ManyToMany
    @JoinTable(
            name = "staff_trips_enrollments",
            joinColumns = { @JoinColumn(name = "trip_id") },
            inverseJoinColumns = { @JoinColumn(name = "person_id") }
    )
    public Collection<Staff> getStaffEnrollments() {
        return staffEnrollments;
    }

    public void setStaffEnrollments(Collection<Staff> staffEnrollments) {
        this.staffEnrollments = staffEnrollments;
    }

}
