package test.java.server;

import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Parent obj) {
        super.assignValidData(obj);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Parent parent = new Parent();
        assignValidData(parent);

        // Create
        HibernateUtils.getInstance().create(parent);
        Parent createdParent = getParent(parent.getFiscalCode());

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
        Parent updatedParent = getParent(parent.getFiscalCode());
        assertNotNull(updatedParent);
        assertModelsEquals(parent, updatedParent);

        // Delete
        HibernateUtils.getInstance().delete(parent);

        // Check delete
        Parent deletedParent = getParent(parent.getFiscalCode());
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


    /**
     * Get parent by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  parent (null if not found)
     */
    public static Parent getParent(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parent> cq = cb.createQuery(Parent.class);
        Root<Parent> root = cq.from(Parent.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Parent> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
