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


    /** {@inheritDoc} */
    @Override
    void assignValidData(Child obj) {
        super.assignValidData(obj);

        List<Parent> parents = new ArrayList<>();
        parents.add(new Parent());
        parents.add(new Parent());
        obj.setParents(parents);

        Pediatrist pediatrist = new Pediatrist();
        obj.setPediatrist(pediatrist);
    }

}
