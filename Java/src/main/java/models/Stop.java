package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStop;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stops", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_date", "trip_title", "place_name", "place_province", "place_nation", "number"}))
public class Stop extends BaseModel {

    @Transient
    private static final long serialVersionUID = 4963210863188064706L;


    @EmbeddedId
    private StopPK id = new StopPK();


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
        if (getTrip() == null) throw new InvalidFieldException("Gita mancante");

        // Place
        if (getPlace() == null) throw new InvalidFieldException("Località mancante");
        getPlace().checkDataValidity();

        // Number: > 0
        if (getNumber() == null) throw new InvalidFieldException("Numero della tappa mancante");
        if (getNumber() <= 0) throw new InvalidFieldException("Numero della tappa non valido");
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
        return Objects.hash(getTrip(), getPlace(), getNumber());
    }


    public StopPK getId() {
        return this.id;
    }


    public Trip getTrip() {
        return this.id.getTrip();
    }


    public void setTrip(Trip trip) {
        this.id.setTrip(trip);
    }


    public Place getPlace() {
        return this.id.getPlace();
    }


    public void setPlace(Place place) {
        this.id.setPlace(place);
    }


    public Integer getNumber() {
        return this.id.getNumber();
    }


    public void setNumber(Integer number) {
        this.id.setNumber(number);
    }

}
