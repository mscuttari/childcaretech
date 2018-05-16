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
        assertModelsEquals(parent, createdParent);

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
        assertModelsEquals(createdParent, updatedParent);

        // Delete
        HibernateUtils.getInstance().delete(updatedParent);

        // Check delete
        Parent deletedParent = getPersonByFiscalCode(updatedParent.getFiscalCode());
        assertNull(deletedParent);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Parent());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Parent());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Parent());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Parent());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Parent());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Parent());
    }

}
