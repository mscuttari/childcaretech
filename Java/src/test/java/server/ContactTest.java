package test.java.server;

import main.java.models.Contact;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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
        assertModelsEquals(contact, createdContact);

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
        assertModelsEquals(createdContact, updatedContact);

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
