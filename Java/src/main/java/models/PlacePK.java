package main.java.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlacePK implements Serializable {

    @Transient
    private static final long serialVersionUID = 4610744958408783214L;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "province", nullable = false)
    private String province;


    @Column(name = "nation", nullable = false)
    private String nation;


    /**
     * Default constructor
     */
    public PlacePK() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name            place name
     * @param   province        province
     * @param   nation          nation
     */
    public PlacePK(String name, String province, String nation) {
        setName(name);
        setProvince(province);
        setNation(nation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlacePK)) return false;

        PlacePK that = (PlacePK) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getNation(), that.getNation());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName(), getProvince(), getNation());
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getProvince() {
        return this.province;
    }


    public void setProvince(String province) {
        this.province = province;
    }


    public String getNation() {
        return this.nation;
    }


    public void setNation(String nation) {
        this.nation = nation;
    }

}
