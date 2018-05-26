package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Person;
import main.java.server.utils.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.java.utils.TestUtils.assertDateEquals;

abstract class PersonTest<M extends Person> extends BaseModelTest<M> {

    private Class<M> modelClass;

    PersonTest(Class<M> modelClass) {
        this.modelClass = modelClass;
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(M x, M y) {
        assertEquals(x.getFiscalCode(), y.getFiscalCode());
        assertEquals(x.getFirstName(), y.getFirstName());
        assertEquals(x.getLastName(), y.getLastName());
        assertDateEquals(x.getBirthdate(), y.getBirthdate());
        assertEquals(x.getAddress(), y.getAddress());
        assertEquals(x.getTelephone(), y.getTelephone());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(M obj) {
        obj.setFiscalCode("AAAAAAAAAAAAAAAA");
        obj.setFirstName("AAA");
        obj.setLastName("AAA");
        obj.setBirthdate(new Date());
        obj.setAddress("Test, A/1");
        obj.setTelephone("+39 111 1111111");
    }


    /**
     * Test the fiscal code validity check
     */
    abstract void fiscalCodeValidity();


    /**
     * Test the first name validity check
     */
    abstract void firstNameValidity();


    /**
     * Test the last name validity check
     */
    abstract void lastNameValidity();


    /**
     * Test the date validity check
     */
    abstract void birthDateValidity();


    /**
     * Test the address validity check
     */
    abstract void addressValidity();


    /**
     * Test the telephone validity check
     */
    abstract void telephoneValidity();


    /**
     * Test the fiscal code validity check
     *
     * @param   person      person instance to be tested
     */
    void fiscalCodeValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);

        // Invalid data
        person.setFiscalCode(null);
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
        person.setFiscalCode("A");
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
    }


    /**
     * Test the first name validity check
     *
     * @param   person      person instance to be tested
     */
    void firstNameValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);

        // Invalid data
        person.setFirstName(null);
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
        person.setFirstName("AA1");
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
    }


    /**
     * Test the last name validity check
     *
     * @param   person      person instance to be tested
     */
    void lastNameValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);

        // Invalid data: fiscal code
        person.setLastName(null);
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
        person.setLastName("AA1");
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
    }


    /**
     * Test the date validity check
     *
     * @param   person      person instance to be tested
     */
    void birthDateValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);

        // Invalid data
        person.setBirthdate(null);
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
    }


    /**
     * Test the address validity check
     *
     * @param   person      person instance to be tested
     */
    void addressValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);
    }


    /**
     * Test the telephone validity check
     *
     * @param   person      person instance to be tested
     */
    void telephoneValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);
        person.setTelephone("+391111111111");
        assertDoesNotThrow(person::checkDataValidity);
        person.setTelephone("1111111111");
        assertDoesNotThrow(person::checkDataValidity);
        person.setTelephone("111 1111111");
        assertDoesNotThrow(person::checkDataValidity);

        // Invalid data
        person.setTelephone("AAA");
        assertThrows(InvalidFieldException.class, person::checkDataValidity);
    }

}
