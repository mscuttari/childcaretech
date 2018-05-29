package test.java.server;

import main.java.models.Pediatrist;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        Pediatrist createdPediatrist = getPediatrist(pediatrist.getFiscalCode());

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
        Pediatrist updatedPediatrist = getPediatrist(pediatrist.getFiscalCode());
        assertNotNull(updatedPediatrist);
        assertModelsEquals(pediatrist, updatedPediatrist);

        // Delete
        HibernateUtils.getInstance().delete(pediatrist);

        // Check delete
        Pediatrist deletedPediatrist = getPediatrist(pediatrist.getFiscalCode());
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


    /**
     * Get pediatrist by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  pediatrist (null if not found)
     */
    public static Pediatrist getPediatrist(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pediatrist> cq = cb.createQuery(Pediatrist.class);
        Root<Pediatrist> root = cq.from(Pediatrist.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Pediatrist> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
