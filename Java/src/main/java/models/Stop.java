package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStop;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stops", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_date", "trip_title", "place_name", "province", "nation", "number"}))
public class Stop extends BaseModel {

    @Transient
    private static final long serialVersionUID = 4963210863188064706L;

    @EmbeddedId
    private StopPK id;


    /**
     * Default constructor
     */
    public Stop() {
        this(null, null, null, null, null);
    }


    /**
     * Constructor
     *
     * @param   trip        trip the stop belongs to
     * @param   placeName   name of the place
     * @param   province    province of the place
     * @param   nation      nation of the place
     * @param   number      sequential number of the stop (in the context of the trip)
     */
    public Stop(Trip trip, String placeName, String province, String nation, Integer number) {
        this.id = new StopPK(trip, placeName, province, nation, number);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (id.getTrip() == null) throw new InvalidFieldException("Gita mancante");

        // Place name: [a-z] [A-Z] space
        if (id.getPlaceName() == null || id.getPlaceName().isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!id.getPlaceName().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Province: [a-z] [A-Z] space
        if (id.getProvince() == null || id.getProvince().isEmpty()) throw new InvalidFieldException("Provincia mancante");
        if (!id.getProvince().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Provincia non valida");

        // Nation: [a-z] [A-Z] space
        if (id.getNation() == null || id.getNation().isEmpty()) throw new InvalidFieldException("Stato mancante");
        if (!id.getNation().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Stato non valido");

        // Number: > 0
        if (id.getNumber() == null) throw new InvalidFieldException("Numero della tappa mancante");
        if (id.getNumber() < 0) throw new InvalidFieldException("Numero della tappa non valido");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStop.class;
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


    public Trip getTrip() {
        return id.getTrip();
    }


    public void setTrip(Trip trip) {
        if (this.id.getTrip() == null) {
            this.id.setTrip(trip);
        }
    }


    public String getPlaceName() {
        return id.getPlaceName();
    }


    public void setPlaceName(String placeName) {
        if (this.id.getPlaceName() == null) {
            this.id.setPlaceName(placeName);
        }
    }


    public String getProvince() {
        return id.getProvince();
    }


    public void setProvince(String province) {
        if (this.id.getProvince() == null) {
            this.id.setProvince(province);
        }
    }


    public String getNation() {
        return id.getNation();
    }


    public void setNation(String nation) {
        if (this.id.getNation() == null) {
            this.id.setNation(nation);
        }
    }


    public Integer getNumber() {
        return id.getNumber();
    }


    public void setNumber(Integer number) {
        if (this.id.getNumber() == null) {
            this.id.setNumber(number);
        }
    }

}
