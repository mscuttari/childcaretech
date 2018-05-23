package test.java.server;

import main.java.models.Child;
import main.java.models.Parent;
import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest extends PersonTest<Parent> {

    ParentTest() {
        super(Parent.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Parent x, Parent y) {
        super.assertModelsEquals(x, y);

        // Check children
        Collection<Child> xChildren = x.getChildren();
        Collection<Child> yChildren = y.getChildren();

        assertEquals(xChildren.size(), yChildren.size());

        for (Child child : xChildren) {
            assertTrue(yChildren.contains(child));
        }
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Parent obj) {
        super.assignValidData(obj);

        // Create children
        Pediatrist pediatrist = new Pediatrist("BBBBBBBBBBBBBBBB", "BBB", "BBB", new Date(), "Test, B/1", "2222222222");
        obj.addChild(new Child("CCCCCCCCCCCCCCCC", "CCC", "CCC", new Date(), "Test, C/1", "3333333333", pediatrist));
        obj.addChild(new Child("DDDDDDDDDDDDDDDD", "DDD", "DDD", new Date(), "Test, D/1", "4444444444", pediatrist));
        obj.addChild(new Child("EEEEEEEEEEEEEEEE", "EEE", "EEE", new Date(), "Test, E/1", "5555555555", pediatrist));
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

        // Delete the pediatrist used for the children
        Pediatrist pediatrist = parent.getChildren().iterator().next().getPediatrist();
        HibernateUtils.getInstance().delete(pediatrist);
        assertNull(getPersonByFiscalCode(pediatrist.getFiscalCode()));
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
