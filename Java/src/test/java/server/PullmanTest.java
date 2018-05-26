package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.*;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PullmanTest extends BaseModelTest<Pullman> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Pullman x, Pullman y) {
        assertEquals(x.getTrip(), y.getTrip());
        assertEquals(x.getId(), y.getId());
        assertEquals(x.getSeats(), y.getSeats());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Pullman obj) {
        // Trip
        obj.setTrip(new Trip(new Date(), "AAA", SeatsAssignmentType.AUTOMATIC));

        // Basic data
        obj.setId("AAA");
        obj.setSeats(10);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Pullman pullman = new Pullman();
        assignValidData(pullman);

        // Create
        HibernateUtils.getInstance().create(pullman);
        Pullman createdPullman = getPullman(pullman.getTrip(), pullman.getId());

        // Check creation
        assertNotNull(createdPullman);
        assertModelsEquals(pullman, createdPullman);

        // Update
        pullman.setSeats(20);

        HibernateUtils.getInstance().update(pullman);

        // Check update
        Pullman updatedPullman = getPullman(pullman.getTrip(), pullman.getId());
        assertNotNull(updatedPullman);
        assertModelsEquals(pullman, updatedPullman);

        // Delete
        HibernateUtils.getInstance().delete(pullman);

        // Check delete
        Pullman deletedPullman = getPullman(pullman.getTrip(), pullman.getId());
        assertNull(deletedPullman);

        // Delete trip
        Trip trip = pullman.getTrip();
        HibernateUtils.getInstance().delete(trip);
        assertNull(TripTest.getTrip(trip.getDate(), trip.getTitle()));
    }


    /**
     * Test the trip validity check
     */
    @Test
    void tripValidity() {
        Pullman pullman = new Pullman();

        // Valid data
        assignValidData(pullman);
        assertDoesNotThrow(pullman::checkDataValidity);

        // Invalid data
        pullman.setTrip(null);
        assertThrows(InvalidFieldException.class, pullman::checkDataValidity);
    }


    /**
     * Test the identification number validity check
     */
    @Test
    void idValidity() {
        Pullman pullman = new Pullman();

        // Valid data
        assignValidData(pullman);
        assertDoesNotThrow(pullman::checkDataValidity);

        // Invalid data
        pullman.setId(null);
        assertThrows(InvalidFieldException.class, pullman::checkDataValidity);
    }


    /**
     * Test the seats validity check
     */
    @Test
    void seatsValidity() {
        Pullman pullman = new Pullman();

        // Valid data
        assignValidData(pullman);
        assertDoesNotThrow(pullman::checkDataValidity);

        // Invalid data
        pullman.setSeats(null);
        assertThrows(InvalidFieldException.class, pullman::checkDataValidity);
        pullman.setSeats(0);
        assertThrows(InvalidFieldException.class, pullman::checkDataValidity);
        pullman.setSeats(-1);
        assertThrows(InvalidFieldException.class, pullman::checkDataValidity);
    }


    /**
     * Get pullman by trip and identification number
     *
     * @param   trip    trip
     * @param   id      identification number
     *
     * @return  provider (null if not found)
     */
    public static Pullman getPullman(Trip trip, String id) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pullman> cq = cb.createQuery(Pullman.class);
        Root<Pullman> root = cq.from(Pullman.class);

        cq.where(cb.equal(root.get("id").get("trip"), trip))
                .where(cb.equal(root.get("id").get("id"), id));

        TypedQuery<Pullman> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
