package test.java.server;

import main.java.client.InvalidFieldException;
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


    /**
     * Creation, update and delete of a valid contact
     */
    @Test
    void dbTest() {
        Ingredient ingredient = new Ingredient();
        assignValidData(ingredient);

        // Create
        HibernateUtils.getInstance().create(ingredient);
        Ingredient createdIngredient = getIngredientByName(ingredient.getName());

        // Check creation
        assertNotNull(createdIngredient);
        assertModelsEquals(ingredient, createdIngredient);

        // Update
        createdIngredient.setName("BBB");

        HibernateUtils.getInstance().update(createdIngredient);
        Ingredient updatedIngredient = getIngredientByName(createdIngredient.getName());

        // Check update
        Ingredient oldIngredient = getIngredientByName(ingredient.getName());
        assertNull(oldIngredient);      // The old name should not be found anymore

        assertNotNull(updatedIngredient);
        assertModelsEquals(createdIngredient, updatedIngredient);

        // Delete
        HibernateUtils.getInstance().delete(updatedIngredient);

        // Check delete
        Ingredient deletedIngredient = getIngredientByName(updatedIngredient.getName());
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
     * @param   name        name
     * @return  ingredient (null if not found)
     */
    private static Ingredient getIngredientByName(String name) {
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
