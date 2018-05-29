package main.java.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Embeddable
public class PullmanPK implements Serializable {

    @Transient
    private static final long serialVersionUID = -3587751118264728128L;


    @ManyToOne
    private Trip trip;


    @Column(name = "id", nullable = false)
    private String id;


    /**
     * Default constructor
     */
    public PullmanPK() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   trip            trip the pullman is used for
     * @param   numberplate     numberplate
     */
    public PullmanPK(Trip trip, String numberplate) {
        setTrip(trip);
        setId(numberplate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PullmanPK)) return false;

        PullmanPK that = (PullmanPK) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getId(), that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public Trip getTrip() {
        return this.trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }

}
