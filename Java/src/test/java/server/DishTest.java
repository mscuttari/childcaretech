package test.java.server;

import main.java.exceptions.InvalidFieldException;
import main.java.models.*;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class DishTest extends BaseModelTest<Dish> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Dish x, Dish y) {
        // Check basic data
        assertEquals(x.getName(), y.getName());
        assertEquals(x.getType(), y.getType());

        // Check provider
        assertEquals(x.getProvider(), y.getProvider());

        // Check ingredients
        Collection<Ingredient> xIngredients = x.getIngredients();
        Collection<Ingredient> yIngredients = y.getIngredients();

        assertEquals(xIngredients.size(), yIngredients.size());

        for (Ingredient ingredient : xIngredients) {
            assertTrue(yIngredients.contains(ingredient));
        }
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Dish obj) {
        // Basic data
        obj.setName("AAA");
        obj.setType(DishType.PRIMO);

        // Provider
        obj.setProvider(new Provider("AAAAAAAAAA", "AAA"));

        // Add ingredients
        obj.addIngredient(new Ingredient("AAA"));
        obj.addIngredient(new Ingredient("BBB"));
        obj.addIngredient(new Ingredient("CCC"));
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Dish dish = new Dish();
        assignValidData(dish);

        // Create
        HibernateUtils.getInstance().create(dish);
        Dish createdContact = getDish(dish.getName());

        // Check creation
        assertNotNull(createdContact);
        assertModelsEquals(dish, createdContact);

        // Update
        dish.setType(DishType.SECONDO);

        HibernateUtils.getInstance().update(dish);

        // Check update
        Dish updatedDish = getDish(dish.getName());
        assertNotNull(updatedDish);
        assertModelsEquals(dish, updatedDish);

        // Delete
        HibernateUtils.getInstance().delete(dish);

        // Check delete
        Dish deletedDish = getDish(dish.getName());
        assertNull(deletedDish);

        // Delete provider
        Provider provider = dish.getProvider();
        HibernateUtils.getInstance().delete(provider);
        assertNull(getDish(provider.getName()));

        // Delete ingredients
        for (Ingredient ingredient : dish.getIngredients()) {
            HibernateUtils.getInstance().delete(ingredient);
            assertNull(IngredientTest.getIngredient(ingredient.getName()));
        }
    }


    /**
     * Test the name validity check
     */
    @Test
    void nameValidity() {
        Dish dish = new Dish();

        // Valid data
        assignValidData(dish);
        assertDoesNotThrow(dish::checkDataValidity);

        // Invalid data
        dish.setName(null);
        assertThrows(InvalidFieldException.class, dish::checkDataValidity);
        dish.setName("123");
        assertThrows(InvalidFieldException.class, dish::checkDataValidity);
    }


    /**
     * Test the type validity check
     */
    @Test
    void typeValidity() {
        Dish dish = new Dish();

        // Valid data
        assignValidData(dish);
        assertDoesNotThrow(dish::checkDataValidity);

        // Invalid data
        dish.setType(null);
        assertThrows(InvalidFieldException.class, dish::checkDataValidity);
    }


    /**
     * Test the provider validity check
     */
    @Test
    void providerValidity() {
        Dish dish = new Dish();

        // Valid data
        assignValidData(dish);
        assertDoesNotThrow(dish::checkDataValidity);

        // Invalid data
        dish.setProvider(null);
        assertThrows(InvalidFieldException.class, dish::checkDataValidity);
    }


    /**
     * Get dish by name
     *
     * @param   name        name
     * @return  dish (null if not found)
     */
    public static Dish getDish(String name) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Dish> cq = cb.createQuery(Dish.class);
        Root<Dish> root = cq.from(Dish.class);
        cq.where(cb.equal(root.get("name"), name));
        TypedQuery<Dish> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
