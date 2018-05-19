package test.java.server;

import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest extends PersonTest<Parent> {

    ParentTest() {
        super(Parent.class);
    }


    /** {@inheritDoc} */
    @Test
    @Override
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
        parent.setFirstName("BBB");
        parent.setLastName("CCC");
        parent.setBirthdate(new Date());
        parent.setAddress("Test, A/2");
        parent.setTelephone("2222222222");

        HibernateUtils.getInstance().update(parent);

        // Check update
        Parent updatedParent = getPersonByFiscalCode(parent.getFiscalCode());
        assertNotNull(updatedParent);
        assertModelsEquals(parent, updatedParent);

        // Delete
        HibernateUtils.getInstance().delete(parent);

        // Check delete
        Parent deletedParent = getPersonByFiscalCode(parent.getFiscalCode());
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
