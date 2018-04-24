package main.java.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "trips", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "title"}))
public class Trip extends BaseModel {

    private Long id;
    private Date date;
    private String title;

    private Collection<Stop> stops = new ArrayList<>();
    private Collection<Pullman> transports = new ArrayList<>();
    private Collection<Child> childrenEnrollments = new ArrayList<>();
    private Collection<Staff> staffEnrollments = new ArrayList<>();

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
        this.title = title;
    }

    @OneToMany(mappedBy = "trip")
    public Collection<Stop> getStops() {
        return stops;
    }

    public void setStops(Collection<Stop> stops) {
        this.stops = stops;
    }

    @OneToMany(mappedBy = "trip")
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

}
