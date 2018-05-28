package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Place;
import main.java.models.SeatsAssignmentType;
import main.java.models.Stop;
import main.java.models.Trip;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StopTest extends BaseModelTest<Stop> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Stop x, Stop y) {
        assertEquals(x.getTrip(), y.getTrip());
        assertEquals(x.getPlace(), y.getPlace());
        assertEquals(x.getNumber(), y.getNumber());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Stop obj) {
        // Trip
        obj.setTrip(new Trip(new Date(), "AAA", SeatsAssignmentType.AUTOMATIC));

        // Place
        obj.setPlace(new Place("AAA", "BBB", "CCC"));

        // Number
        obj.setNumber(1);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Stop stop = new Stop();
        assignValidData(stop);

        // Create
        HibernateUtils.getInstance().create(stop);
        Stop createdStop = getStop(stop.getTrip(), stop.getPlace(), stop.getNumber());

        // Check creation
        assertNotNull(createdStop);
        assertModelsEquals(stop, createdStop);

        // Delete
        HibernateUtils.getInstance().delete(stop);

        // Check delete
        Stop deletedStop = getStop(stop.getTrip(), stop.getPlace(), stop.getNumber());
        assertNull(deletedStop);

        // Delete trip
        Trip trip = stop.getTrip();
        HibernateUtils.getInstance().delete(trip);
        assertNull(TripTest.getTrip(trip.getDate(), trip.getTitle()));

        // Delete place
        Place place = stop.getPlace();
        HibernateUtils.getInstance().delete(place);
        assertNull(PlaceTest.getPlace(place.getName(), place.getProvince(), place.getNation()));
    }


    /**
     * Test the trip validity check
     */
    @Test
    void tripValidity() {
        Stop stop = new Stop();

        // Valid data
        assignValidData(stop);
        assertDoesNotThrow(stop::checkDataValidity);

        // Invalid data
        stop.setTrip(null);
        assertThrows(InvalidFieldException.class, stop::checkDataValidity);
    }


    /**
     * Test the place validity check
     */
    @Test
    void placeValidity() {
        Stop stop = new Stop();

        // Valid data
        assignValidData(stop);
        assertDoesNotThrow(stop::checkDataValidity);

        // Invalid data
        stop.setPlace(null);
        assertThrows(InvalidFieldException.class, stop::checkDataValidity);
    }


    /**
     * Test the number validity check
     */
    @Test
    void numberValidity() {
        Stop stop = new Stop();

        // Valid data
        assignValidData(stop);
        assertDoesNotThrow(stop::checkDataValidity);

        // Invalid data
        stop.setNumber(null);
        assertThrows(InvalidFieldException.class, stop::checkDataValidity);
        stop.setNumber(0);
        assertThrows(InvalidFieldException.class, stop::checkDataValidity);
        stop.setNumber(-1);
        assertThrows(InvalidFieldException.class, stop::checkDataValidity);
    }


    /**
     * Get stop by trip, place and stop number
     *
     * @param   trip        trip the stop belongs to
     * @param   place       place of the stop
     * @param   number      stop number in the trip
     *
     * @return  stop (null if not found)
     */
    public static Stop getStop(Trip trip, Place place, Integer number) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Stop> cq = cb.createQuery(Stop.class);
        Root<Stop> root = cq.from(Stop.class);

        cq.where(cb.equal(root.get("id").get("trip"), trip))
                .where(cb.equal(root.get("id").get("place"), place))
                .where(cb.equal(root.get("id").get("number"), number));

        TypedQuery<Stop> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
