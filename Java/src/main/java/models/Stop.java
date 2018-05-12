package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStop;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "stops", uniqueConstraints = @UniqueConstraint(columnNames = {"trip_id", "place_name", "province", "nation", "number"}))
public class Stop extends BaseModel {

    private Long id;
    private Trip trip;
    private String placeName;
    private String province;
    private String nation;
    private Integer number;

    private Collection<Child> childrenPresences = new ArrayList<>();


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
        this.trip = trip;
        this.placeName = placeName;
        this.province = province;
        this.nation = nation;
        this.number = number;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Trip
        if (trip == null) throw new InvalidFieldException("Gita mancante");

        // Place name: [a-z] [A-Z] space
        if (placeName == null || placeName.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!placeName.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Province: [a-z] [A-Z] space
        if (province == null || province.isEmpty()) throw new InvalidFieldException("Provincia mancante");
        if (!province.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Provincia non valida");

        // Nation: [a-z] [A-Z] space
        if (nation == null || nation.isEmpty()) throw new InvalidFieldException("Stato mancante");
        if (!nation.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Stato non valido");

        // Number: > 0
        if (number == null) throw new InvalidFieldException("Numero della tappa mancante");
        if (number < 0) throw new InvalidFieldException("Numero della tappa non valido");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStop.class;
    }


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

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Column(name = "place_name", nullable = false)
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Column(name = "province", nullable = false)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "nation", nullable = false)
    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    @Column(name = "number", nullable = false)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @ManyToMany
    @JoinTable(
            name = "children_stops_presence",
            joinColumns = { @JoinColumn(name = "stop_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    public Collection<Child> getChildrenPresences() {
        return childrenPresences;
    }

    public void setChildrenPresences(Collection<Child> childrenPresences) {
        this.childrenPresences = childrenPresences;
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

}
