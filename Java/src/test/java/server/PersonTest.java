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
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class PersonTest<M extends Person> {

    private Class<M> modelClass;

    PersonTest(Class<M> modelClass) {
        this.modelClass = modelClass;
    }


    /**
     * Test the fiscal code validity control
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
     * Test the first name validity control
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
     * Test the last name validity control
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
     * Test the date validity control
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
     * Test the address validity control
     *
     * @param   person      person instance to be tested
     */
    void addressValidity(M person) {
        // Valid data
        assignValidData(person);
        assertDoesNotThrow(person::checkDataValidity);
    }


    /**
     * Test the telephone validity control
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


    /**
     * Assign basic valid data to a person instance
     *
     * @param   person      person instance to be populated with valid data
     */
    void assignValidData(M person) {
        person.setFiscalCode("AAAAAAAAAAAAAAAA");
        person.setFirstName("AAA");
        person.setLastName("BBB");
        person.setBirthdate(new Date());
        person.setAddress("Test, A/1");
        person.setTelephone("+39 111 1111111");
    }



    /**
     * Get person by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  person (null if not found)
     */
    M getPersonByFiscalCode(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<M> cq = cb.createQuery(modelClass);
        Root<M> root = cq.from(modelClass);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<M> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
