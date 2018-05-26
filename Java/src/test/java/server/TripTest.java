package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.SeatsAssignmentType;
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
    void assertModelsEquals(Trip x, Trip y) {
        assertDateEquals(x.getDate(), y.getDate());
        assertEquals(x.getTitle(), y.getTitle());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Trip obj) {
        obj.setDate(new Date());
        obj.setTitle("AAA");
        obj.setSeatsAssignmentType(SeatsAssignmentType.AUTOMATIC);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Trip trip = new Trip();
        assignValidData(trip);

        // Create
        HibernateUtils.getInstance().create(trip);
        Trip createdTrip = getTrip(trip.getDate(), trip.getTitle());

        // Check creation
        assertNotNull(createdTrip);
        assertModelsEquals(trip, createdTrip);

        // Delete
        HibernateUtils.getInstance().delete(trip);

        // Check delete
        Trip deletedTrip = getTrip(trip.getDate(), trip.getTitle());
        assertNull(deletedTrip);
    }


    /**
     * Test the date validity check
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
     * Test the title validity check
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
    public static Trip getTrip(Date date, String title) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);
        Root<Trip> root = cq.from(Trip.class);

        cq.where(cb.equal(root.get("id").get("date"), date))
                .where(cb.equal(root.get("id").get("title"), title));

        TypedQuery<Trip> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
