package main.java.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Embeddable
public class StopPK implements Serializable {

    @Transient
    private static final long serialVersionUID = 1432021014402688581L;


    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Place place;


    @Column(name = "number", nullable = false)
    private Integer number;


    /**
     * Default constructor
     */
    public StopPK() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   trip        trip the stop belongs to
     * @param   place       stop place
     * @param   number      sequential number of the stop (in the context of the trip)
     */
    public StopPK(Trip trip, Place place, Integer number) {
        setTrip(trip);
        setPlace(place);
        setNumber(number);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopPK)) return false;

        StopPK that = (StopPK) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getPlace(), that.getPlace()) &
                Objects.equals(getNumber(), that.getNumber());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }


    public Trip getTrip() {
        return this.trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    public Place getPlace() {
        return this.place;
    }


    public void setPlace(Place place) {
        this.place = place;
    }


    public Integer getNumber() {
        return this.number;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }

}
