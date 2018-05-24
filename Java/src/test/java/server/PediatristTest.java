package test.java.server;

import main.java.models.Child;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PediatristTest extends PersonTest<Pediatrist> {

    PediatristTest() {
        super(Pediatrist.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Pediatrist x, Pediatrist y) {
        super.assertModelsEquals(x, y);
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Pediatrist obj) {
        super.assignValidData(obj);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Pediatrist pediatrist = new Pediatrist();
        assignValidData(pediatrist);

        // Create
        HibernateUtils.getInstance().create(pediatrist);
        Pediatrist createdPediatrist = getPersonByFiscalCode(pediatrist.getFiscalCode());

        // Check creation
        assertNotNull(createdPediatrist);
        assertModelsEquals(pediatrist, createdPediatrist);

        // Update
        pediatrist.setFirstName("BBB");
        pediatrist.setLastName("BBB");
        pediatrist.setBirthdate(new Date());
        pediatrist.setAddress("Test, A/2");
        pediatrist.setTelephone("2222222222");

        HibernateUtils.getInstance().update(pediatrist);

        // Check update
        Pediatrist updatedPediatrist = getPersonByFiscalCode(pediatrist.getFiscalCode());
        assertNotNull(updatedPediatrist);
        assertModelsEquals(pediatrist, updatedPediatrist);

        // Delete
        HibernateUtils.getInstance().delete(pediatrist);

        // Check delete
        Pediatrist deletedPediatrist = getPersonByFiscalCode(pediatrist.getFiscalCode());
        assertNull(deletedPediatrist);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Pediatrist());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Pediatrist());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Pediatrist());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Pediatrist());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Pediatrist());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Pediatrist());
    }

}
