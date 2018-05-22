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
    private PullmanPK id;


    @Column(name = "seats", nullable = false)
    private Integer seats;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "children_pullmans_assignments",
            joinColumns = {
                    @JoinColumn(name = "trip_date", referencedColumnName = "trip_date"),
                    @JoinColumn(name = "trip_title", referencedColumnName = "trip_title"),
                    @JoinColumn(name = "pullman_numberplate", referencedColumnName = "numberplate")
            },
            inverseJoinColumns = { @JoinColumn(name = "child_fiscal_code", referencedColumnName = "fiscal_code") }
    )
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
     * @param   trip            trip the pullman is used for
     * @param   numberplate     numberplate
     * @param   seats           max seats available
     */
    public Pullman(Trip trip, String numberplate, Integer seats) {
        this.id = new PullmanPK(trip, numberplate);
        setSeats(seats);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (id.getTrip() == null) throw new InvalidFieldException("Gita mancante");

        // Type: [a-z] [A-Z] [0-9]
        if (id.getNumberplate() == null) throw new InvalidFieldException("Targa mancante");
        if (!id.getNumberplate().matches("^[a-zA-Z\\d]+$")) throw new InvalidFieldException("Targa non valida");

        // Seats: > 0
        if (seats == null) throw new InvalidFieldException("Numero di posti mancante");
        if (seats <= 0) throw new InvalidFieldException("Numero di posti non valido");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPullman.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pullman)) return false;

        Pullman that = (Pullman) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getNumberplate(), that.getNumberplate());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getTrip(), getNumberplate());
    }


    public Trip getTrip() {
        return this.id.getTrip();
    }


    public void setTrip(Trip trip) {
        this.id.setTrip(trip);
    }


    public String getNumberplate() {
        return this.id.getNumberplate();
    }


    public void setNumberplate(String numberplate) {
        this.id.setNumberplate(numberplate);
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
