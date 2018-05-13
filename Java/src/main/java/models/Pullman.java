package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPullman;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pullmans", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "numberplate"}))
public class Pullman extends BaseModel {

    // Serialization
    private static final long serialVersionUID = 1788156831943646482L;

    private Long id;
    private Trip trip;
    private String numberplate;
    private Integer seats;

    private Collection<Child> childrenAssignments = new ArrayList<>();


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
        this.trip = trip;
        this.numberplate = numberplate;
        this.seats = seats;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (trip == null) throw new InvalidFieldException("Gita mancante");

        // Type: [a-z] [A-Z] [0-9]
        if (numberplate == null || numberplate.isEmpty()) throw new InvalidFieldException("Targa mancante");
        if (!numberplate.matches("^[a-zA-Z\\d]+$")) throw new InvalidFieldException("Targa non valida");

        // Seats: > 0
        if (seats == null) throw new InvalidFieldException("Numero di posti mancante");
        if (seats <= 0) throw new InvalidFieldException("Numero di posti non valido");
    }


    /** {@inheritDoc} */
    @Transient
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

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Column(name = "numberplate", nullable = false)
    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate == null || numberplate.isEmpty() ? null : numberplate;
    }

    @Column(name = "seats", nullable = false)
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "children_pullmans_assignments",
            joinColumns = { @JoinColumn(name = "pullman_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id")
            }
    )
    public Collection<Child> getChildrenAssignments() {
        return childrenAssignments;
    }

    public void setChildrenAssignments(Collection<Child> childrenAssignments) {
        this.childrenAssignments = childrenAssignments;
    }

    @Override
    public String toString(){
        return numberplate;
    }

}
