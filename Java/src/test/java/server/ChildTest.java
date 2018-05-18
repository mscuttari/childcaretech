package test.java.server;

import main.java.models.Child;
import main.java.models.Parent;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ChildTest extends PersonTest<Child> {

    ChildTest() {
        super(Child.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Child x, Child y) {
        super.assertModelsEquals(x, y);


    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Child obj) {
        super.assignValidData(obj);

        // Create two parents
        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent("AAAAAAAAAAAAAAAA", "BBB", "CCC", new Date(), "Test, A/1", "1111111111"));
        parents.add(new Parent("DDDDDDDDDDDDDDDD", "EEE", "FFF", new Date(), "Test, A/2", "2222222222"));
        obj.setParents(parents);

        // Create pediatrist
        Pediatrist pediatrist = new Pediatrist("GGGGGGGGGGGGGGGG", "HHH", "III", new Date(), "Test, A/3", "3333333333");
        obj.setPediatrist(pediatrist);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Child child = new Child();
        assignValidData(child);

        // Create parents
        //for (Parent parent : child.getParents())
        //    HibernateUtils.getInstance().create(parent);

        // Create pediatrist
        //HibernateUtils.getInstance().create(child.getPediatrist());

        // Create child
        HibernateUtils.getInstance().create(child);
        Child createdChild = getPersonByFiscalCode(child.getFiscalCode());

        // Check creation
        assertNotNull(createdChild);
        assertModelsEquals(child, createdChild);

        // Update
        /*createdChild.setFiscalCode("LLLLLLLLLLLLLLLL");
        createdChild.setFirstName("MMM");
        createdChild.setLastName("NNN");
        createdChild.setBirthdate(new Date());
        createdChild.setAddress("Test, A/4");
        createdChild.setTelephone("4444444444");

        HibernateUtils.getInstance().update(createdChild);
        Child updatedChild = getPersonByFiscalCode(createdChild.getFiscalCode());

        // Check update
        Child oldChild = getPersonByFiscalCode(child.getFiscalCode());
        assertNull(oldChild);       // The old fiscal code should not be found anymore

        assertNotNull(updatedChild);
        assertModelsEquals(createdChild, updatedChild);

        // Delete
        HibernateUtils.getInstance().delete(updatedChild);

        // Check delete
        Child deletedChild = getPersonByFiscalCode(updatedChild.getFiscalCode());
        assertNull(deletedChild);*/
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
