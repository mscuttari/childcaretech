package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.server.TestUtils.assertDateEquals;

public class ParentTest {

    /**
     * Creation, update and delete of a valid parent
     */
    @Test
    public void dbTest() {
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");

        // Create
        HibernateUtils.getInstance().create(parent);
        Parent createdParent = getParentByFiscalCode(parent.getFiscalCode());

        // Check creation
        assertNotNull(createdParent);

        assertEquals(parent.getFiscalCode(), createdParent.getFiscalCode());
        assertEquals(parent.getFirstName(), createdParent.getFirstName());
        assertEquals(parent.getLastName(), createdParent.getLastName());
        assertDateEquals(parent.getBirthdate(), createdParent.getBirthdate());
        assertEquals(parent.getAddress(), createdParent.getAddress());
        assertEquals(parent.getTelephone(), createdParent.getTelephone());

        // Update
        createdParent.setFiscalCode("BBBBBBBBBBBBBBBB");
        createdParent.setFirstName("CCC");
        createdParent.setLastName("DDD");
        createdParent.setBirthdate(new Date());
        createdParent.setAddress("Test, A/2");
        createdParent.setTelephone("2222222222");

        HibernateUtils.getInstance().update(createdParent);
        Parent updatedParent = getParentByFiscalCode(createdParent.getFiscalCode());

        // Check update
        Parent oldParent = getParentByFiscalCode(parent.getFiscalCode());
        assertNull(oldParent);      // The old fiscal code should not be found anymore

        assertNotNull(updatedParent);

        assertEquals(createdParent.getFiscalCode(), updatedParent.getFiscalCode());
        assertEquals(createdParent.getFirstName(), updatedParent.getFirstName());
        assertEquals(createdParent.getLastName(), updatedParent.getLastName());
        assertDateEquals(createdParent.getBirthdate(), updatedParent.getBirthdate());
        assertEquals(createdParent.getAddress(), updatedParent.getAddress());
        assertEquals(createdParent.getTelephone(), updatedParent.getTelephone());

        // Delete
        HibernateUtils.getInstance().delete(updatedParent);

        // Check delete
        Parent deletedParent = getParentByFiscalCode(updatedParent.getFiscalCode());
        assertNull(deletedParent);
    }


    /**
     * Test the fiscal code validity control
     */
    @Test
    public void fiscalCodeValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);

        // Invalid data
        parent.setFiscalCode(null);
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
        parent.setFiscalCode("A");
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
    }


    /**
     * Test the first name validity control
     */
    @Test
    public void firstNameValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);

        // Invalid data
        parent.setFirstName(null);
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
        parent.setFirstName("AA1");
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
    }


    /**
     * Test the last name validity control
     */
    @Test
    public void lastNameValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);

        // Invalid data: fiscal code
        parent.setLastName(null);
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
        parent.setLastName("AA1");
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
    }



    /**
     * Test the date validity control
     */
    @Test
    public void birthDateValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);

        // Invalid data
        parent.setBirthdate(null);
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
    }


    /**
     * Test the address validity control
     */
    @Test
    public void addressValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);
    }


    /**
     * Test the telephone validity control
     */
    @Test
    public void telephoneValidity() {
        // Valid data
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);
        parent.setTelephone("+391111111111");
        assertDoesNotThrow(parent::checkDataValidity);
        parent.setTelephone("1111111111");
        assertDoesNotThrow(parent::checkDataValidity);
        parent.setTelephone("111 1111111");
        assertDoesNotThrow(parent::checkDataValidity);

        // Invalid data
        parent.setTelephone("AAA");
        assertThrows(InvalidFieldException.class, parent::checkDataValidity);
    }


    /**
     * Get parent by fiscal code
     *
     * @param   fiscalCode  fiscal code
     * @return  parent (null if not found)
     */
    private static Parent getParentByFiscalCode(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parent> cq = cb.createQuery(Parent.class);
        Root<Parent> root = cq.from(Parent.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Parent> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
