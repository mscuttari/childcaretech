package main.java.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PullmanPK implements Serializable {

    @Transient
    private static final long serialVersionUID = -3587751118264728128L;


    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "trip_date", referencedColumnName = "date", nullable = false),
            @JoinColumn(name = "trip_title", referencedColumnName = "title", nullable = false)
    })
    private Trip trip;


    @Column(name = "numberplate", nullable = false)
    private String numberplate;


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
        setNumberplate(numberplate);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PullmanPK)) return false;

        PullmanPK that = (PullmanPK) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getNumberplate(), that.getNumberplate());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getTrip(), getNumberplate());
    }


    public Trip getTrip() {
        return this.trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip == null ? new Trip() : trip;
    }


    public String getNumberplate() {
        return this.numberplate;
    }


    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate == null || numberplate.isEmpty() ? null : numberplate;
    }

}
