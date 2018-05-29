package test.java.server;

import main.java.exceptions.InvalidFieldException;
import main.java.models.Ingredient;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest extends BaseModelTest<Ingredient> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Ingredient x, Ingredient y) {
        assertEquals(x.getName(), y.getName());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Ingredient obj) {
        obj.setName("AAA");
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Ingredient ingredient = new Ingredient();
        assignValidData(ingredient);

        // Create
        HibernateUtils.getInstance().create(ingredient);
        Ingredient createdIngredient = getIngredient(ingredient.getName());

        // Check creation
        assertNotNull(createdIngredient);
        assertModelsEquals(ingredient, createdIngredient);

        // Delete
        HibernateUtils.getInstance().delete(ingredient);

        // Check delete
        Ingredient deletedIngredient = getIngredient(ingredient.getName());
        assertNull(deletedIngredient);
    }


    /**
     * Test the name validity check
     */
    @Test
    void titleValidity() {
        Ingredient ingredient = new Ingredient();

        // Valid data
        assignValidData(ingredient);
        assertDoesNotThrow(ingredient::checkDataValidity);

        // Invalid data
        ingredient.setName(null);
        assertThrows(InvalidFieldException.class, ingredient::checkDataValidity);
        ingredient.setName("123");
        assertThrows(InvalidFieldException.class, ingredient::checkDataValidity);
    }


    /**
     * Get ingredient by name
     *
     * @param   name        ingredient name
     * @return  ingredient (null if not found)
     */
    public static Ingredient getIngredient(String name) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ingredient> cq = cb.createQuery(Ingredient.class);
        Root<Ingredient> root = cq.from(Ingredient.class);
        cq.where(cb.equal(root.get("name"), name));
        TypedQuery<Ingredient> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
