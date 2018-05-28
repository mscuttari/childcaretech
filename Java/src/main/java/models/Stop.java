package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStop;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "stops", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_date", "trip_title", "place_name", "place_province", "place_nation", "number"}))
public class Stop extends BaseModel {

    @Transient
    private static final long serialVersionUID = 4963210863188064706L;


    @EmbeddedId
    private StopPK id = new StopPK();


    @MapsId(value = "trip")
    @ManyToOne(cascade = {PERSIST, MERGE}, optional = false)
    @JoinColumns(value = {
            @JoinColumn(name = "trip_date", referencedColumnName = "date"),
            @JoinColumn(name = "trip_title", referencedColumnName = "title")
    })
    private Trip trip;


    @MapsId(value = "place")
    @ManyToOne(cascade = {PERSIST, MERGE}, optional = false)
    @JoinColumns(value = {
            @JoinColumn(name = "place_name", referencedColumnName = "name"),
            @JoinColumn(name = "place_province", referencedColumnName = "province"),
            @JoinColumn(name = "place_nation", referencedColumnName = "nation")
    })
    private Place place;


    /**
     * Default constructor
     */
    public Stop() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   trip        trip the stop belongs to
     * @param   place       stop place
     * @param   number      sequential number of the stop (in the context of the trip)
     */
    public Stop(Trip trip, Place place, Integer number) {
        setTrip(trip);
        setPlace(place);
        setNumber(number);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (getTrip() == null)
            throwFieldError("Gita mancante");

        // Place
        if (getPlace() == null)
            throwFieldError("Località mancante");

        getPlace().checkDataValidity();

        // Number: > 0
        if (getNumber() == null)
            throwFieldError("Numero della tappa mancante");

        if (getNumber() <= 0)
            throwFieldError("Numero della tappa non valido");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Tappa";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStop.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {
        // Place
        if (getPlace() != null)
            getPlace().removeStop(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;

        Stop that = (Stop) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getPlace(), that.getPlace()) &&
                Objects.equals(getNumber(), that.getNumber());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }


    @Override
    public String toString() {
        return "#" + getNumber() + " - " + getPlace().toString();
    }


    public StopPK getId() {
        return this.id;
    }


    public Trip getTrip() {
        return this.trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip;
        this.id.setTrip(trip);
    }


    public Place getPlace() {
        return this.place;
    }


    public void setPlace(Place place) {
        this.place = place;
        this.id.setPlace(place);
    }


    public Integer getNumber() {
        return this.id.getNumber();
    }


    public void setNumber(Integer number) {
        this.id.setNumber(number);
    }

}
