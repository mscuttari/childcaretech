package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Dish;
import main.java.models.DishType;
import main.java.models.Ingredient;
import main.java.models.Provider;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.*;

class DishTest extends BaseModelTest<Dish> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Dish x, Dish y) {
        assertEquals(x.getName(), y.getName());
        assertEquals(x.getType(), y.getType());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Dish obj) {
        obj.setName("AAA");
        obj.setType(DishType.PRIMO);
        obj.setProvider(new Provider("AAAAAAAAAA", "AAA"));
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Dish dish = new Dish();
        assignValidData(dish);

        // Create
        HibernateUtils.getInstance().create(dish);
        Dish createdContact = getDishByName(dish.getName());

        // Check creation
        assertNotNull(createdContact);
        assertModelsEquals(dish, createdContact);

        // Update
        dish.setType(DishType.SECONDO);

        HibernateUtils.getInstance().update(dish);

        // Check update
        Dish updatedDish = getDishByName(dish.getName());
        assertNotNull(updatedDish);
        assertModelsEquals(dish, updatedDish);

        // Delete
        HibernateUtils.getInstance().delete(dish);

        // Check delete
        Dish deletedDish = getDishByName(dish.getName());
        assertNull(deletedDish);

        // Delete provider
        Provider provider = dish.getProvider();
        HibernateUtils.getInstance().delete(provider);
        assertNull(getDishByName(provider.getName()));
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
    private static Dish getDishByName(String name) {
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
