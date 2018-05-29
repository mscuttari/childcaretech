package main.java.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
class TripPK implements Serializable {

    @Transient
    private static final long serialVersionUID = -8704900303319763605L;


    @Column(name = "date", nullable = false)
    private Date date;


    @Column(name = "title", nullable = false)
    private String title;


    /**
     * Default constructor
     */
    public TripPK() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   date    date the trip has been scheduled for
     * @param   title   name of the trip
     */
    public TripPK(Date date, String title) {
        setDate(date);
        setTitle(title);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripPK)) return false;

        TripPK that = (TripPK) o;
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTitle(), that.getTitle());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getDate(), getTitle());
    }


    public Date getDate() {
        return this.date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}
