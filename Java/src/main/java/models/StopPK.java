package main.java.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StopPK implements Serializable {

    @Transient
    private static final long serialVersionUID = 1432021014402688581L;


    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "trip_date", referencedColumnName = "date", nullable = false),
            @JoinColumn(name = "trip_title", referencedColumnName = "title", nullable = false)
    })
    private Trip trip;


    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "place_name", referencedColumnName = "name", nullable = false),
            @JoinColumn(name = "place_province", referencedColumnName = "province", nullable = false),
            @JoinColumn(name = "place_nation", referencedColumnName = "nation", nullable = false)
    })
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
        return Objects.hash(getTrip(), getPlace(), getNumber());
    }


    public Trip getTrip() {
        return this.trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip == null ? new Trip() : trip;
    }


    public Place getPlace() {
        return this.place;
    }


    public void setPlace(Place place) {
        this.place = place == null ? new Place() : place;
    }


    public Integer getNumber() {
        return this.number;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }

}
