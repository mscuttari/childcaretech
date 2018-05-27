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
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static test.java.utils.TestUtils.assertDateEquals;

class TripTest extends BaseModelTest<Trip> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Trip x, Trip y) {
        // Check basic data
        assertDateEquals(x.getDate(), y.getDate());
        assertEquals(x.getTitle(), y.getTitle());

        // Check children
        Collection<Child> xChildren = x.getChildren();
        Collection<Child> yChildren = y.getChildren();

        assertEquals(xChildren.size(), yChildren.size());

        for (Child child : xChildren) {
            assertTrue(yChildren.contains(child));
        }

        // Check staff
        Collection<Staff> xStaff = x.getStaff();
        Collection<Staff> yStaff = y.getStaff();

        assertEquals(xStaff.size(), yStaff.size());

        for (Staff staff : xStaff) {
            assertTrue(yStaff.contains(staff));
        }

        // Check stops
        Collection<Stop> xStops = x.getStops();
        Collection<Stop> yStops = y.getStops();

        assertEquals(xStops.size(), yStops.size());

        for (Stop stop : xStops) {
            assertTrue(yStops.contains(stop));
        }

        // Check pullmans
        Collection<Pullman> xPullmans = x.getPullmans();
        Collection<Pullman> yPullmans = y.getPullmans();

        assertEquals(xPullmans.size(), yPullmans.size());

        for (Pullman pullman : xPullmans) {
            assertTrue(yPullmans.contains(pullman));
        }
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Trip obj) {
        // Basic data
        obj.setDate(new Date());
        obj.setTitle("AAA");
        obj.setSeatsAssignmentType(SeatsAssignmentType.AUTOMATIC);

        // Add children
        Pediatrist pediatrist = new Pediatrist("AAAAAAAAAAAAAAAA", "AAA", "AAA", new Date(), "Test, A/1", "1111111111");
        obj.addChild(new Child("BBBBBBBBBBBBBBBB", "BBB", "BBB", new Date(), "Test, B/2", "2222222222", pediatrist));
        obj.addChild(new Child("CCCCCCCCCCCCCCCC", "CCC", "CCC", new Date(), "Test, C/3", "3333333333", pediatrist));
        obj.addChild(new Child("DDDDDDDDDDDDDDDD", "DDD", "DDD", new Date(), "Test, D/4", "4444444444", pediatrist));
        obj.addChild(new Child("EEEEEEEEEEEEEEEE", "EEE", "EEE", new Date(), "Test, E/5", "5555555555", pediatrist));
        obj.addChild(new Child("FFFFFFFFFFFFFFFF", "FFF", "FFF", new Date(), "Test, F/6", "6666666666", pediatrist));

        // Add staff
        obj.addStaff(new Staff("GGGGGGGGGGGGGGGG", "GGG", "GGG", new Date(), "Test, G/7", "7777777777", "user", "user"));

        // Add stops
        obj.addStop(new Stop(obj, new Place("Milano", "MI", "Italia"), 1));
        obj.addStop(new Stop(obj, new Place("Firenze", "FI", "Italia"), 2));
        obj.addStop(new Stop(obj, new Place("Roma", "RM", "Italia"), 3));
        obj.addStop(new Stop(obj, new Place("Napoli", "NA", "Italia"), 4));
        obj.addStop(new Stop(obj, new Place("Bari", "BA", "Italia"), 5));

        // Add pullmans
        obj.addPullman(new Pullman(obj, "AAA", 10));
        obj.addPullman(new Pullman(obj, "BBB", 10));
        obj.addPullman(new Pullman(obj, "CCC", 10));
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

        // Check pullmans delete
        for (Pullman pullman : trip.getPullmans()) {
            assertNull(PullmanTest.getPullman(pullman.getTrip(), pullman.getId()));
        }

        // Delete places
        for (Stop stop : trip.getStops()) {
            HibernateUtils.getInstance().delete(stop.getPlace());
        }

        for (Stop stop : trip.getStops()) {
            Place place = stop.getPlace();
            assertNull(PlaceTest.getPlace(place.getName(), place.getProvince(), place.getNation()));
        }

        // Delete children
        for (Child child : trip.getChildren()) {
            HibernateUtils.getInstance().delete(child);
            assertNull(ChildTest.getChild(child.getFiscalCode()));
        }

        // Delete pediatrist
        /*
        Pediatrist pediatrist = trip.getChildren().iterator().next().getPediatrist();
        HibernateUtils.getInstance().delete(pediatrist);
        assertNull(PediatristTest.getPediatrist(pediatrist.getFiscalCode()));
*/
        // Delete staff
        for (Staff staff : trip.getStaff()) {
            HibernateUtils.getInstance().delete(staff);
            assertNull(StaffTest.getStaff(staff.getFiscalCode()));
        }
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

        cq.where(cb.equal(root.get("title"), title));

        TypedQuery<Trip> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
