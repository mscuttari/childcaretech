package test.java.server;

import main.java.models.Contact;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.utils.TestUtils.assertDateEquals;

class ContactTest extends PersonTest<Contact> {

    ContactTest() {
        super(Contact.class);
    }


    /**
     * Creation, update and delete of a valid contact
     */
    @Test
    void dbTest() {
        Contact contact = new Contact();
        assignValidData(contact);

        // Create
        HibernateUtils.getInstance().create(contact);
        Contact createdContact = getPersonByFiscalCode(contact.getFiscalCode());

        // Check creation
        assertNotNull(createdContact);

        assertEquals(contact.getFiscalCode(), createdContact.getFiscalCode());
        assertEquals(contact.getFirstName(), createdContact.getFirstName());
        assertEquals(contact.getLastName(), createdContact.getLastName());
        assertDateEquals(contact.getBirthdate(), createdContact.getBirthdate());
        assertEquals(contact.getAddress(), createdContact.getAddress());
        assertEquals(contact.getTelephone(), createdContact.getTelephone());

        // Update
        createdContact.setFiscalCode("BBBBBBBBBBBBBBBB");
        createdContact.setFirstName("CCC");
        createdContact.setLastName("DDD");
        createdContact.setBirthdate(new Date());
        createdContact.setAddress("Test, A/2");
        createdContact.setTelephone("2222222222");

        HibernateUtils.getInstance().update(createdContact);
        Contact updatedContact = getPersonByFiscalCode(createdContact.getFiscalCode());

        // Check update
        Contact oldContact = getPersonByFiscalCode(contact.getFiscalCode());
        assertNull(oldContact);      // The old fiscal code should not be found anymore

        assertNotNull(updatedContact);

        assertEquals(createdContact.getFiscalCode(), updatedContact.getFiscalCode());
        assertEquals(createdContact.getFirstName(), updatedContact.getFirstName());
        assertEquals(createdContact.getLastName(), updatedContact.getLastName());
        assertDateEquals(createdContact.getBirthdate(), updatedContact.getBirthdate());
        assertEquals(createdContact.getAddress(), updatedContact.getAddress());
        assertEquals(createdContact.getTelephone(), updatedContact.getTelephone());

        // Delete
        HibernateUtils.getInstance().delete(updatedContact);

        // Check delete
        Contact deletedContact = getPersonByFiscalCode(updatedContact.getFiscalCode());
        assertNull(deletedContact);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Contact());
    }

}
