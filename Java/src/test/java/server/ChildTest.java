package test.java.server;

import main.java.models.Child;
import main.java.models.Parent;
import main.java.models.Pediatrist;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ChildTest extends PersonTest<Child> {

    ChildTest() {
        super(Child.class);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(createBasicChild());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(createBasicChild());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(createBasicChild());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(createBasicChild());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(createBasicChild());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(createBasicChild());
    }


    /**
     * Create a child object with two parents and a pediatrist
     *
     * @return  child object
     */
    private static Child createBasicChild() {
        Child child = new Child();

        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent());
        parents.add(new Parent());
        child.setParents(parents);

        Pediatrist pediatrist = new Pediatrist();
        child.setPediatrist(pediatrist);

        return child;
    }

}
