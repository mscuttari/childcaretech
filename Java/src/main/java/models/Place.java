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

@Entity
@Table(name = "places")
public class Place extends BaseModel implements Serializable {

    @Transient
    private static final long serialVersionUID = -8024772523733312803L;


    @EmbeddedId
    private PlacePK id;


    @OneToMany(mappedBy = "id.place", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
        this.id = new PlacePK(name, province, nation);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Place name: [a-z] [A-Z] space
        if (id.getName() == null) throw new InvalidFieldException("Nome mancante");
        if (!id.getName().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Province: [a-z] [A-Z] space
        if (id.getProvince() == null) throw new InvalidFieldException("Provincia mancante");
        if (!id.getProvince().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Provincia non valida");

        // Nation: [a-z] [A-Z] space
        if (id.getNation() == null) throw new InvalidFieldException("Stato mancante");
        if (!id.getNation().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Stato non valido");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPlace.class;
    }


    public String getName() {
        return this.id.getName();
    }


    public void setName(String name) {
        this.id.setName(name);
    }


    public String getProvince() {
        return this.id.getProvince();
    }


    public void setProvince(String province) {
        this.id.setProvince(province);
    }


    public String getNation() {
        return this.id.getNation();
    }


    public void setNation(String nation) {
        this.id.setNation(nation);
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
