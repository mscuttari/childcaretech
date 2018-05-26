package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPlace;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "places")
public class Place extends BaseModel implements Serializable {

    @Transient
    private static final long serialVersionUID = -8024772523733312803L;


    @EmbeddedId
    private PlacePK id = new PlacePK();


    @OneToMany(mappedBy = "id.place", cascade = {PERSIST, MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Stop> stops = new HashSet<>();


    /**
     * Default constructor
     */
    public Place() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name        place name
     * @param   province    province
     * @param   nation      nation
     */
    public Place(String name, String province, String nation) {
        setName(name);
        setProvince(province);
        setNation(nation);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Place name: [a-z] [A-Z] space
        if (getName() == null)
            throwFieldError("Nome mancante");

        if (!getName().matches("^[a-zA-Z\\040]+$"))
            throwFieldError("Nome non valido");

        // Province: [a-z] [A-Z] space
        if (getProvince() == null)
            throwFieldError("Provincia mancante");

        if (!getProvince().matches("^[a-zA-Z\\040]+$"))
            throwFieldError("Provincia non valida");

        // Nation: [a-z] [A-Z] space
        if (getNation() == null)
            throwFieldError("Nazione mancante");

        if (!getNation().matches("^[a-zA-Z\\040]+$"))
            throwFieldError("Nazione non valida");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Località";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPlace.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return getStops().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place that = (Place) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getNation(), that.getNation());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProvince(), getNation());
    }



    public String getName() {
        return this.id.getName();
    }


    public void setName(String name) {
        this.id.setName(trimString(name));
    }


    public String getProvince() {
        return this.id.getProvince();
    }


    public void setProvince(String province) {
        this.id.setProvince(trimString(province));
    }


    public String getNation() {
        return this.id.getNation();
    }


    public void setNation(String nation) {
        this.id.setNation(trimString(nation));
    }


    public Collection<Stop> getStops() {
        return this.stops;
    }


    public void addStop(Stop stop) {
        this.stops.add(stop);
    }


    public void addStops(Collection<Stop> stops) {
        this.stops.addAll(stops);
    }


    public void setStops(Collection<Stop> stops) {
        this.stops.clear();
        addStops(stops);
    }

}
