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

import static org.junit.jupiter.api.Assertions.*;


class PlaceTest extends BaseModelTest<Place> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Place x, Place y) {
        // Check basic data
        assertEquals(x.getName(), y.getName());
        assertEquals(x.getProvince(), y.getProvince());
        assertEquals(x.getNation(), y.getNation());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Place obj) {
        // Basic data
        obj.setName("AAA");
        obj.setProvince("AAA");
        obj.setNation("AAA");
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Place place = new Place();
        assignValidData(place);

        // Create
        HibernateUtils.getInstance().create(place);
        Place createdPlace = getPlace(place.getName(), place.getProvince(), place.getNation());

        // Check creation
        assertNotNull(createdPlace);
        assertModelsEquals(place, createdPlace);

        // Delete
        HibernateUtils.getInstance().delete(place);

        // Check delete
        Place deletedPlace = getPlace(place.getName(), place.getProvince(), place.getNation());
        assertNull(deletedPlace);
    }



    /**
     * Test the name validity check
     */
    @Test
    void nameValidity() {
        Place place = new Place();

        // Valid data
        assignValidData(place);
        assertDoesNotThrow(place::checkDataValidity);

        // Invalid data
        place.setName(null);
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
        place.setName("123");
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
    }


    /**
     * Test the province validity check
     */
    @Test
    void provinceValidity() {
        Place place = new Place();

        // Valid data
        assignValidData(place);
        assertDoesNotThrow(place::checkDataValidity);

        // Invalid data
        place.setProvince(null);
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
        place.setProvince("123");
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
    }


    /**
     * Test the nation validity check
     */
    @Test
    void nationValidity() {
        Place place = new Place();

        // Valid data
        assignValidData(place);
        assertDoesNotThrow(place::checkDataValidity);

        // Invalid data
        place.setNation(null);
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
        place.setNation("123");
        assertThrows(InvalidFieldException.class, place::checkDataValidity);
    }


    /**
     * Get place by name, province and nation
     *
     * @param   name        place name
     * @param   province    place province
     * @param   nation      place nation
     *
     * @return  place (null if not found)
     */
    public static Place getPlace(String name, String province, String nation) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Place> cq = cb.createQuery(Place.class);
        Root<Place> root = cq.from(Place.class);

        cq.where(cb.equal(root.get("id").get("name"), name))
                .where(cb.equal(root.get("id").get("province"), province))
                .where(cb.equal(root.get("id").get("nation"), nation));

        TypedQuery<Place> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
