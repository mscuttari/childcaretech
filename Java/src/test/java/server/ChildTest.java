package test.java.server;

import main.java.models.Child;
import main.java.models.Contact;
import main.java.models.Parent;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

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

        // Create two parents
        obj.addParent(new Parent("BBBBBBBBBBBBBBBB", "BBB", "BBB", new Date(), "Test, B/1", "1111111111"));
        obj.addParent(new Parent("CCCCCCCCCCCCCCCC", "CCC", "CCC", new Date(), "Test, C/1", "2222222222"));

        // Create pediatrist
        obj.setPediatrist(new Pediatrist("DDDDDDDDDDDDDDDD", "DDD", "DDD", new Date(), "Test, D/1", "3333333333"));

        // Create some contacts
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
        Child createdChild = getPersonByFiscalCode(child.getFiscalCode());

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
        Child updatedChild = getPersonByFiscalCode(child.getFiscalCode());
        assertNotNull(updatedChild);
        assertModelsEquals(child, updatedChild);

        // Delete
        HibernateUtils.getInstance().delete(child);

        // Check delete
        Child deletedChild = getPersonByFiscalCode(child.getFiscalCode());
        assertNull(deletedChild);

        // Delete parents
        for (Parent parent : child.getParents()) {
            HibernateUtils.getInstance().delete(parent);
            assertNull(getPersonByFiscalCode(parent.getFiscalCode()));
        }

        // Delete pediatrist
        HibernateUtils.getInstance().delete(child.getPediatrist());
        assertNull(getPersonByFiscalCode(child.getPediatrist().getFiscalCode()));

        // Delete contacts
        for (Contact contact : child.getContacts()) {
            HibernateUtils.getInstance().delete(contact);
            assertNull(getPersonByFiscalCode(contact.getFiscalCode()));
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

}
