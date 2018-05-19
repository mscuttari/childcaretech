package test.java.server;

import main.java.models.Child;
import main.java.models.Parent;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

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
        assertEquals(x.getParents().size(), y.getParents().size());
        for (Parent parent : x.getParents()) {
            assertTrue(y.getParents().contains(parent));
        }

        // Check pediatrist
        Pediatrist xP = x.getPediatrist();
        Pediatrist yP = y.getPediatrist();
        assertTrue(x.getPediatrist().equals(y.getPediatrist()));
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Child obj) {
        super.assignValidData(obj);

        // Create two parents
        obj.addParent(new Parent("BBBBBBBBBBBBBBBB", "BBB", "CCC", new Date(), "Test, A/1", "1111111111"));
        obj.addParent(new Parent("CCCCCCCCCCCCCCCC", "DDD", "EEE", new Date(), "Test, A/2", "2222222222"));

        // Create pediatrist
        obj.setPediatrist(new Pediatrist("DDDDDDDDDDDDDDDD", "FFF", "GGG", new Date(), "Test, A/3", "3333333333"));
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
        createdChild.setFirstName("HHH");
        createdChild.setLastName("III");
        createdChild.setBirthdate(new Date());
        createdChild.setAddress("Test, A/4");
        createdChild.setTelephone("4444444444");

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
