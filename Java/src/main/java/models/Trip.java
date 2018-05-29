package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiTrip;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "title"}))
@NamedQuery(name = "Trip.search", query = "SELECT t FROM Trip t WHERE t.id.date = :date AND t.id.title = :title")
public class Trip extends BaseModel {

    @Transient
    private static final long serialVersionUID = 9041385768903721723L;


    @EmbeddedId
    private TripPK id = new TripPK();


    @Column(name = "seats_assignment_type", nullable = false)
    private SeatsAssignmentType seatsAssignmentType;


    @OneToMany(mappedBy = "id.trip", cascade = {ALL} , orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Stop> stops = new HashSet<>();


    @OneToMany(mappedBy = "id.trip", cascade = {ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Pullman> pullmans = new HashSet<>();


    @ManyToMany(cascade = {PERSIST, MERGE})
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


    @ManyToMany(cascade = {PERSIST, MERGE})
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
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   date    date the trip has been scheduled for
     * @param   title   title of the trip
     */
    public Trip(Date date, String title, SeatsAssignmentType seatsAssignmentType) {
        setDate(date);
        setTitle(title);
        setSeatsAssignmentType(seatsAssignmentType);
    }


    /** {@inheritDoc} */
    @Override
    public String getSearchQueryName() {
        return "Trip.search";
    }


    /** {@inheritDoc} */
    @Override
    public boolean runSearchQuery(Query query) {
        query.setParameter("date", getDate());
        query.setParameter("title", getTitle());

        return !query.getResultList().isEmpty();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Date
        if (getDate() == null)
            throwFieldError("Data mancante");

        // Title: [a-z] [A-Z] à è é ì ò ù ' " space
        if (getTitle() == null)
            throwFieldError("Titolo mancante");

        if (!getTitle().matches("^[a-zA-Zàèéìòù'\"\\040]+$"))
            throwFieldError("Titolo non valido");

        // Seats assignment type
        if (getSeatsAssignmentType() == null)
            throwFieldError("Metodo di assegnamento posti mancante");

        // Check if the total number of seats is enough
        if (getSeatsAssignmentType() != SeatsAssignmentType.UNNECESSARY) {
            Integer totalNumberOfSeats = getAvailableSeats();

            if (totalNumberOfSeats < getChildren().size() + getStaff().size())
                throwFieldError("Posti insufficienti");
        }

        // Check if it is possible to place t least one staff member on each pullman
        if (getPullmans().size() > getStaff().size())
            throwFieldError("Non sono presenti abbastanza responsabili per il numero di pullman inseriti");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getModelName() {
        return "Gita";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiTrip.class;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDeletable() {
        return true;
    }


    /**
     * {@inheritDoc}
     */
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

        if(seatsAssignmentType != SeatsAssignmentType.UNNECESSARY){
            for(Pullman currentPullman : pullmans){
                for (Child child : currentPullman.getChildren()) {
                    child.removePullmanAssignment(currentPullman);
                }
            }
        }
        for(Stop stop : stops){
            // Place
            if (stop.getPlace() != null)
                stop.getPlace().removeStop(stop);
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
        return Objects.hash(getTitle());
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


    public SeatsAssignmentType getSeatsAssignmentType() {
        return this.seatsAssignmentType;
    }


    public void setSeatsAssignmentType(SeatsAssignmentType seatsAssignmentType) {
        this.seatsAssignmentType = seatsAssignmentType;
    }


    public Collection<Stop> getStops() {
        return this.stops;
    }


    public void addStop(Stop stop) {
        this.stops.add(stop);
    }


    public void addStops(Collection<Stop> stops) {
        this.stops.addAll(stops);
    }


    public void setStops(Collection<Stop> stops) {
        this.stops.clear();
        addStops(stops);
    }


    public Collection<Pullman> getPullmans() {
        return this.pullmans;
    }


    public void addPullman(Pullman pullman) {
        this.pullmans.add(pullman);
    }


    public void addPullmans(Collection<Pullman> pullmans) {
        this.pullmans.addAll(pullmans);
    }


    public void setPullmans(Collection<Pullman> pullmans) {
        this.pullmans.clear();
        addPullmans(pullmans);
    }


    public Integer getAvailableSeats() {
        Integer totalNumberOfSeats = 0;

        for (Pullman pullman : pullmans){
            totalNumberOfSeats += pullman.getSeats();
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

    public void removeChild(Child child) {
        this.children.remove(child);
    }

}