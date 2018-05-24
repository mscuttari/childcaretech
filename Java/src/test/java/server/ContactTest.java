package test.java.server;

import main.java.models.Child;
import main.java.models.Contact;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest extends PersonTest<Contact> {

    ContactTest() {
        super(Contact.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Contact x, Contact y) {
        super.assertModelsEquals(x, y);
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Contact obj) {
        super.assignValidData(obj);
    }


    /** {@inheritDoc} */
    @Test
    @Override
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
        contact.setFirstName("BBB");
        contact.setLastName("CCC");
        contact.setBirthdate(new Date());
        contact.setAddress("Test, A/2");
        contact.setTelephone("2222222222");

        HibernateUtils.getInstance().update(contact);

        // Check update
        Contact updatedContact = getPersonByFiscalCode(contact.getFiscalCode());
        assertNotNull(updatedContact);
        assertModelsEquals(contact, updatedContact);

        // Delete
        HibernateUtils.getInstance().delete(contact);

        // Check delete
        Contact deletedContact = getPersonByFiscalCode(contact.getFiscalCode());
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
