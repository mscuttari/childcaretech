package main.java.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stops", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "place_name", "province", "nation", "number"}))
public class Stop {

    private Long id;
    private Trip trip;
    private String placeName;
    private String province;
    private String nation;
    private Integer number;
    private Collection<Person> childrenPresences = new ArrayList<>();

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

    @Column(name = "place_name", nullable = false)
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Column(name = "province", nullable = false)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "nation", nullable = false)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "number", nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @ManyToMany
    @JoinTable(
            name = "children_stops_presence",
            joinColumns = { @JoinColumn(name = "stop_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    public Collection<Person> getChildrenPresences() {
        return childrenPresences;
    }

    public void setChildrenPresences(Collection<Person> childrenPresences) {
        this.childrenPresences = childrenPresences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;

        Stop that = (Stop) o;
        return Objects.equals(getPlaceName(), that.getPlaceName()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getNation(), that.getNation()) &&
                Objects.equals(getNumber(), that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlaceName(), getProvince(), getNation(), getNumber());
    }

}
