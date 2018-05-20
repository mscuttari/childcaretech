package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Provider;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;

class ProviderTest extends BaseModelTest<Provider> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Provider x, Provider y) {
        assertEquals(x.getVat(), y.getVat());
        assertEquals(x.getName(), y.getName());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Provider obj) {
        obj.setVat("AAAAAAAAAAAAAAAA");
        obj.setName("BBB");
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Provider provider = new Provider();
        assignValidData(provider);

        // Create
        HibernateUtils.getInstance().create(provider);
        Provider createdProvider = getProviderByVat(provider.getVat());

        // Check creation
        assertNotNull(createdProvider);
        assertModelsEquals(provider, createdProvider);

        // Update
        provider.setName("CCC");

        HibernateUtils.getInstance().update(provider);

        // Check update
        Provider updatedProvider = getProviderByVat(provider.getVat());
        assertNotNull(updatedProvider);
        assertModelsEquals(provider, updatedProvider);

        // Delete
        HibernateUtils.getInstance().delete(provider);

        // Check delete
        Provider deletedProvider = getProviderByVat(provider.getVat());
        assertNull(deletedProvider);
    }


    /**
     * Test the VAT number validity check
     */
    @Test
    void vatValidity() {
        // Valid data
        Provider provider = new Provider();
        assignValidData(provider);
        assertDoesNotThrow(provider::checkDataValidity);

        // Invalid data
        provider.setVat(null);
        assertThrows(InvalidFieldException.class, provider::checkDataValidity);
    }


    /**
     * Test the name validity check
     */
    @Test
    void nameValidity() {
        Provider provider = new Provider();

        // Valid data
        assignValidData(provider);
        assertDoesNotThrow(provider::checkDataValidity);

        // Invalid data
        provider.setName(null);
        assertThrows(InvalidFieldException.class, provider::checkDataValidity);
        provider.setName("ABC123");
        assertThrows(InvalidFieldException.class, provider::checkDataValidity);
    }


    /**
     * Get provider by VAT number
     *
     * @param   vat     VAT number
     * @return  provider (null if not found)
     */
    private static Provider getProviderByVat(String vat) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Provider> cq = cb.createQuery(Provider.class);
        Root<Provider> root = cq.from(Provider.class);
        cq.where(cb.equal(root.get("vat"), vat));
        TypedQuery<Provider> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
