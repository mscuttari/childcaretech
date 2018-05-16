package test.java.server;

import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.utils.TestUtils.assertDateEquals;

class ParentTest extends PersonTest<Parent> {

    ParentTest() {
        super(Parent.class);
    }


    /**
     * Creation, update and delete of a valid parent
     */
    @Test
    void dbTest() {
        Parent parent = new Parent();
        assignValidData(parent);

        // Create
        HibernateUtils.getInstance().create(parent);
        Parent createdParent = getPersonByFiscalCode(parent.getFiscalCode());

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
        Parent updatedParent = getPersonByFiscalCode(createdParent.getFiscalCode());

        // Check update
        Parent oldParent = getPersonByFiscalCode(parent.getFiscalCode());
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
        Parent deletedParent = getPersonByFiscalCode(updatedParent.getFiscalCode());
        assertNull(deletedParent);
    }


    /**
     * Test the fiscal code validity control
     */
    @Test
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Parent());
    }


    /**
     * Test the first name validity control
     */
    @Test
    void firstNameValidity() {
        super.firstNameValidity(new Parent());
    }


    /**
     * Test the last name validity control
     */
    @Test
    void lastNameValidity() {
        super.lastNameValidity(new Parent());
    }


    /**
     * Test the date validity control
     */
    @Test
    void birthDateValidity() {
        super.birthDateValidity(new Parent());
    }


    /**
     * Test the address validity control
     */
    @Test
    void addressValidity() {
        super.addressValidity(new Parent());
    }


    /**
     * Test the telephone validity control
     */
    @Test
    void telephoneValidity() {
        super.telephoneValidity(new Parent());
    }

}
