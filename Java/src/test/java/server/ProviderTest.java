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

public class ProviderTest {

    /**
     * Creation, update and delete of a valid parent
     */
    @Test
    public void dbTest() {
        Provider provider = new Provider("AAAAAAAAAAAAAAAA", "BBB");

        // Create
        HibernateUtils.getInstance().create(provider);
        Provider createdProvider = getProviderByVat(provider.getVat());

        // Check creation
        assertNotNull(createdProvider);

        assertEquals(provider.getVat(), createdProvider.getVat());
        assertEquals(provider.getName(), createdProvider.getName());

        // Update
        createdProvider.setVat("BBBBBBBBBBBBBBBB");
        createdProvider.setName("CCC");

        HibernateUtils.getInstance().update(createdProvider);
        Provider updatedProvider = getProviderByVat(createdProvider.getVat());

        // Check update
        Provider oldProvider = getProviderByVat(provider.getVat());
        assertNull(oldProvider);    // The old vat number should not be found anymore

        assertNotNull(updatedProvider);

        assertEquals(createdProvider.getVat(), updatedProvider.getVat());
        assertEquals(createdProvider.getName(), updatedProvider.getName());

        // Delete
        HibernateUtils.getInstance().delete(updatedProvider);

        // Check delete
        Provider deletedProvider = getProviderByVat(updatedProvider.getVat());
        assertNull(deletedProvider);
    }


    /**
     * Test the VAT number validity control
     */
    @Test
    public void vatValidity() {
        // Valid data
        Provider provider = new Provider("AAAAAAAAAAAAAAAA", "BBB");
        assertDoesNotThrow(provider::checkDataValidity);

        // Invalid data
        provider.setVat(null);
        assertThrows(InvalidFieldException.class, provider::checkDataValidity);
    }


    /**
     * Test the name validity control
     */
    @Test
    public void nameValidity() {
        // Valid data
        Provider provider = new Provider("AAAAAAAAAAAAAAAA", "BBB");
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
