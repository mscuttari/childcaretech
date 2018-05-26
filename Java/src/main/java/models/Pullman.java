package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPullman;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "pullmans")
public class Pullman extends BaseModel {

    @Transient
    private static final long serialVersionUID = 1788156831943646482L;


    @EmbeddedId
    private PullmanPK id = new PullmanPK();


    @Column(name = "seats", nullable = false)
    private Integer seats;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "children_pullmans_assignments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "trip_date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "trip_title"),
                    @JoinColumn(name = "pullman_id", referencedColumnName = "id")
            },
            inverseJoinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Child> children = new HashSet<>();


    /**
     * Default constructor
     */
    public Pullman() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   trip        trip the pullman is used for
     * @param   id          identification number
     * @param   seats       max seats available
     */
    public Pullman(Trip trip, String id, Integer seats) {
        setTrip(trip);
        setId(id);
        setSeats(seats);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (getTrip() == null)
            throwFieldError("Gita mancante");

        // Type: [a-z] [A-Z] [0-9]
        if (getId() == null)
            throwFieldError("Numero identificativo mancante");

        if (!getId().matches("^[a-zA-Z\\d]+$"))
            throwFieldError("Numero identificativo non valido");

        // Seats: > 0
        if (getSeats() == null)
            throwFieldError("Numero di posti mancante");

        if (getSeats() <= 0)
            throwFieldError("Numero di posti non valido");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Pullman";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPullman.class;
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
            child.removePullmanAssignment(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pullman)) return false;

        Pullman that = (Pullman) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getId(), that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public Trip getTrip() {
        return this.id.getTrip();
    }


    public void setTrip(Trip trip) {
        this.id.setTrip(trip);
    }


    public String getId() {
        return this.id.getId();
    }


    public void setId(String id) {
        this.id.setId(trimString(id));
    }


    public Integer getSeats() {
        return this.seats;
    }


    public void setSeats(Integer seats) {
        this.seats = seats;
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

}
