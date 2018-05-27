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

class ChildTest extends PersonTest<Child> {

    ChildTest() {
        super(Child.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Child x, Child y) {
        super.assertModelsEquals(x, y);

        // Check basic data
        assertEquals(x.getId(), y.getId());

        // Check parents
        Collection<Parent> xParents = x.getParents();
        Collection<Parent> yParents = y.getParents();

        assertEquals(xParents.size(), yParents.size());

        for (Parent parent : xParents) {
            assertTrue(yParents.contains(parent));
        }

        // Check pediatrist
        assertTrue(x.getPediatrist().equals(y.getPediatrist()));

        // Check contacts
        Collection<Contact> xContacts = x.getContacts();
        Collection<Contact> yContacts = y.getContacts();

        assertEquals(xContacts.size(), yContacts.size());

        for (Contact contact : xContacts) {
            assertTrue(yContacts.contains(contact));
        }
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Child obj) {
        super.assignValidData(obj);

        // Basic data
        obj.setId("CHILD1111111111");

        // Add parents
        obj.addParent(new Parent("BBBBBBBBBBBBBBBB", "BBB", "BBB", new Date(), "Test, B/1", "1111111111"));
        obj.addParent(new Parent("CCCCCCCCCCCCCCCC", "CCC", "CCC", new Date(), "Test, C/1", "2222222222"));

        // Add pediatrist
        obj.setPediatrist(new Pediatrist("DDDDDDDDDDDDDDDD", "DDD", "DDD", new Date(), "Test, D/1", "3333333333"));

        // Add contacts
        obj.addContact(new Contact("EEEEEEEEEEEEEEEE", "EEE", "EEE", new Date(), "Test, E/1", "4444444444"));
        obj.addContact(new Contact("FFFFFFFFFFFFFFFF", "FFF", "FFF", new Date(), "Test, F/1", "5555555555"));
        obj.addContact(new Contact("GGGGGGGGGGGGGGGG", "GGG", "GGG", new Date(), "Test, G/1", "6666666666"));
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Child child = new Child();
        assignValidData(child);

        // Create child
        HibernateUtils.getInstance().create(child);
        Child createdChild = getChild(child.getFiscalCode());

        // Check creation
        assertNotNull(createdChild);
        assertModelsEquals(child, createdChild);

        // Update
        createdChild.setFirstName("ZZZ");
        createdChild.setLastName("ZZZ");
        createdChild.setBirthdate(new Date());
        createdChild.setAddress("Test, Z/1");
        createdChild.setTelephone("9999999999");

        HibernateUtils.getInstance().update(child);

        // Check update
        Child updatedChild = getChild(child.getFiscalCode());
        assertNotNull(updatedChild);
        assertModelsEquals(child, updatedChild);

        // Delete
        HibernateUtils.getInstance().delete(child);

        // Check delete
        Child deletedChild = getChild(child.getFiscalCode());
        assertNull(deletedChild);

        // Delete parents
        for (Parent parent : child.getParents()) {
            HibernateUtils.getInstance().delete(parent);
            assertNull(getChild(parent.getFiscalCode()));
        }

        // Delete pediatrist
        HibernateUtils.getInstance().delete(child.getPediatrist());
        assertNull(getChild(child.getPediatrist().getFiscalCode()));

        // Delete contacts
        for (Contact contact : child.getContacts()) {
            HibernateUtils.getInstance().delete(contact);
            assertNull(getChild(contact.getFiscalCode()));
        }
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Child());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Child());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Child());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Child());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Child());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Child());
    }



    /**
     * Test the identification number validity check
     */
    @Test
    void titleValidity() {
        Child child = new Child();

        // Valid data
        assignValidData(child);
        assertDoesNotThrow(child::checkDataValidity);

        // Invalid data
        child.setId(null);
        assertThrows(InvalidFieldException.class, child::checkDataValidity);
    }


    /**
     * Get child by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  child (null if not found)
     */
    public static Child getChild(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Child> cq = cb.createQuery(Child.class);
        Root<Child> root = cq.from(Child.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Child> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
