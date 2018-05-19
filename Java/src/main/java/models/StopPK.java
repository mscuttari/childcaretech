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

    @Column(name = "place_name", nullable = false)
    private String placeName;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "nation", nullable = false)
    private String nation;

    @Column(name = "number", nullable = false)
    private Integer number;


    /**
     * Default constructor
     */
    public StopPK() {
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
    public StopPK(Trip trip, String placeName, String province, String nation, Integer number) {
        this.trip = trip;
        this.placeName = placeName;
        this.province = province;
        this.nation = nation;
        this.number = number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StopPK)) return false;

        StopPK that = (StopPK) o;
        return Objects.equals(getTrip(), that.getTrip()) &&
                Objects.equals(getPlaceName(), that.getPlaceName()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getNation(), that.getNation()) &&
                Objects.equals(getNumber(), that.getNumber());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getTrip(), getPlaceName());
    }


    public Trip getTrip() {
        return trip;
    }


    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    public String getPlaceName() {
        return placeName;
    }


    public void setPlaceName(String placeName) {
        this.placeName = placeName == null || placeName.isEmpty() ? null : placeName;
    }


    public String getProvince() {
        return province;
    }


    public void setProvince(String province) {
        this.province = province == null || province.isEmpty() ? null : province;
    }


    public String getNation() {
        return nation;
    }


    public void setNation(String nation) {
        this.nation = nation == null || nation.isEmpty() ? null : nation;
    }


    public Integer getNumber() {
        return number;
    }


    public void setNumber(Integer number) {
        this.number = number;
    }

}
