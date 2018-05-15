package test.java.server;

import main.java.LogUtils;
import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.server.TestUtils.assertDateEquals;

public class ParentTest {

    @Test
    public void addParent() {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();

        // Populate parent
        Parent parent = new Parent("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Via Test, A/1", "+39 111 1111111");

        // Save parent
        hibernateUtils.create(parent);

        // Get parent
        EntityManager em = hibernateUtils.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Parent> cq = cb.createQuery(Parent.class);
        Root<Parent> root = cq.from(Parent.class);
        cq.where(cb.equal(root.get("fiscalCode"), parent.getFiscalCode()));
        TypedQuery<Parent> q = em.createQuery(cq);

        Parent queryResult = q.getSingleResult();

        // Delete parent
        hibernateUtils.delete(queryResult);

        // Check data
        Assertions.assertNotNull(queryResult);

        assertEquals(parent.getFiscalCode(), queryResult.getFiscalCode());
        assertEquals(parent.getFirstName(), queryResult.getFirstName());
        assertEquals(parent.getLastName(), queryResult.getLastName());
        assertDateEquals(parent.getBirthdate(), queryResult.getBirthdate());
        assertEquals(parent.getAddress(), queryResult.getAddress());
        assertEquals(parent.getTelephone(), queryResult.getTelephone());
    }


}
