package test.java.server;

import main.java.client.InvalidFieldException;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static test.java.utils.TestUtils.assertDateEquals;

class TripTest extends BaseModelTest<Trip> {

    /** {@inheritDoc} */
    @Override
    void assignValidData(Trip obj) {
        obj.setDate(new Date());
        obj.setTitle("AAA");
    }


    /**
     * Creation, update and delete of a valid parent
     */
    @Test
    void dbTest() {
        Trip trip = new Trip();
        assignValidData(trip);

        // Create
        HibernateUtils.getInstance().create(trip);
        Trip createdTrip = getTripByDateAndTitle(trip.getDate(), trip.getTitle());

        // Check creation
        assertNotNull(createdTrip);

        assertDateEquals(trip.getDate(), createdTrip.getDate());
        assertEquals(trip.getTitle(), createdTrip.getTitle());

        // Update
        createdTrip.setDate(new Date());
        createdTrip.setTitle("BBB");

        HibernateUtils.getInstance().update(createdTrip);
        Trip updatedTrip = getTripByDateAndTitle(createdTrip.getDate(), createdTrip.getTitle());

        // Check update
        Trip oldTrip = getTripByDateAndTitle(trip.getDate(), trip.getTitle());
        assertNull(oldTrip);        // The old fiscal code should not be found anymore

        assertNotNull(updatedTrip);

        assertDateEquals(createdTrip.getDate(), updatedTrip.getDate());
        assertEquals(createdTrip.getTitle(), updatedTrip.getTitle());

        // Delete
        HibernateUtils.getInstance().delete(updatedTrip);

        // Check delete
        Trip deletedTrip = getTripByDateAndTitle(updatedTrip.getDate(), updatedTrip.getTitle());
        assertNull(deletedTrip);
    }


    /**
     * Test the date validity control
     */
    @Test
    void dateValidty() {
        Trip trip = new Trip();

        // Valid data
        assignValidData(trip);
        assertDoesNotThrow(trip::checkDataValidity);

        // Invalid data
        trip.setDate(null);
        assertThrows(InvalidFieldException.class, trip::checkDataValidity);
    }


    /**
     * Test the title validity control
     */
    @Test
    void titleValidity() {
        Trip trip = new Trip();

        // Valid data
        assignValidData(trip);
        assertDoesNotThrow(trip::checkDataValidity);

        // Invalid data
        trip.setTitle(null);
        assertThrows(InvalidFieldException.class, trip::checkDataValidity);
    }


    /**
     * Get trip by date and title
     *
     * @param   date        date
     * @param   title       title
     *
     * @return  trip (null if not found)
     */
    private static Trip getTripByDateAndTitle(Date date, String title) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);
        Root<Trip> root = cq.from(Trip.class);
        cq.where(cb.equal(root.get("date"), date), cb.equal(root.get("title"), title));
        TypedQuery<Trip> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
