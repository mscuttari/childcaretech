package main.java.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "pullmans", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "numberplate"}))
public class Pullman extends BaseModel {

    private Long id;
    private Trip trip;
    private String numberplate;
    private int seats;
    private Collection<Person> childrenAssignments = new ArrayList<>();

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
        this.numberplate = numberplate;
    }

    @Column(name = "seats", nullable = false)
    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @ManyToMany
    @JoinTable(
            name = "children_pullmans_assignments",
            joinColumns = { @JoinColumn(name = "pullman_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    public Collection<Person> getChildrenAssignments() {
        return childrenAssignments;
    }

    public void setChildrenAssignments(Collection<Person> childrenAssignments) {
        this.childrenAssignments = childrenAssignments;
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

}
