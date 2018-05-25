package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.*;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static test.java.server.DishTest.getDishByName;
import static test.java.server.ProviderTest.getProviderByVat;

class MenuTest extends BaseModelTest<Menu> {

    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Menu x, Menu y) {
        // Check basic data
        assertEquals(x.getName(), y.getName());
        assertEquals(x.getDayOfTheWeek(), y.getDayOfTheWeek());

        // Check responsible
        assertEquals(x.getResponsible(), y.getResponsible());

        // Check dishes
        Collection<Dish> xDishes = x.getDishes();
        Collection<Dish> yDishes = y.getDishes();

        assertEquals(xDishes.size(), yDishes.size());

        for (Dish dish : xDishes) {
            assertTrue(yDishes.contains(dish));
        }
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Menu obj) {
        // Basic data
        obj.setName("AAA");
        obj.setDayOfTheWeek(DayOfTheWeek.MONDAY);

        // Add responsible
        obj.setResponsible(new Staff("AAAAAAAAAAAAAAAA", "AAA", "AAA", new Date(), "Test, A/1", "1111111111", "user", "user"));

        // Add dishes
        Provider provider = new Provider("AAAAAAAAAA", "AAA");
        obj.addDish(new Dish("AAA", DishType.PRIMO, provider));
        obj.addDish(new Dish("BBB", DishType.SECONDO, provider));
        obj.addDish(new Dish("CCC", DishType.CONTORNO, provider));
        obj.addDish(new Dish("DDD", DishType.DOLCE, provider));
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Menu menu = new Menu();
        assignValidData(menu);

        // Create
        HibernateUtils.getInstance().create(menu);
        Menu createdMenu = getMenuByName(menu.getName());

        // Check creation
        assertNotNull(createdMenu);
        assertModelsEquals(menu, createdMenu);

        // Update
        menu.setDayOfTheWeek(DayOfTheWeek.TUESDAY);

        HibernateUtils.getInstance().update(menu);

        // Check update
        Menu updatedMenu = getMenuByName(menu.getName());
        assertNotNull(updatedMenu);
        assertModelsEquals(menu, updatedMenu);

        // Delete
        HibernateUtils.getInstance().delete(menu);

        // Check delete
        Menu deletedMenu = getMenuByName(menu.getName());
        assertNull(deletedMenu);

        // Delete responsible
        Staff responsible = menu.getResponsible();
        HibernateUtils.getInstance().delete(responsible);
        assertNull(getStaffByFiscalCode(responsible.getFiscalCode()));

        // Delete dishes
        for (Dish dish : menu.getDishes()) {
            HibernateUtils.getInstance().delete(dish);
            assertNull(getDishByName(dish.getName()));
        }

        for (Dish dish : menu.getDishes()) {
            HibernateUtils.getInstance().delete(dish.getProvider());
            assertNull(getProviderByVat(dish.getProvider().getVat()));
        }
    }


    /**
     * Test the name validity check
     */
    @Test
    void nameValidity() {
        Menu menu = new Menu();

        // Valid data
        assignValidData(menu);
        assertDoesNotThrow(menu::checkDataValidity);

        // Invalid data
        menu.setName(null);
        assertThrows(InvalidFieldException.class, menu::checkDataValidity);
        menu.setName("123");
        assertThrows(InvalidFieldException.class, menu::checkDataValidity);
    }


    /**
     * Test the day validity check
     */
    @Test
    void dayValidity() {
        Menu menu = new Menu();

        // Valid data
        assignValidData(menu);
        assertDoesNotThrow(menu::checkDataValidity);

        // Invalid data
        menu.setDayOfTheWeek(null);
        assertThrows(InvalidFieldException.class, menu::checkDataValidity);
    }


    /**
     * Get menu by name
     *
     * @param   name        name
     * @return  menu (null if not found)
     */
    private static Menu getMenuByName(String name) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
        Root<Menu> root = cq.from(Menu.class);
        cq.where(cb.equal(root.get("name"), name));
        TypedQuery<Menu> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }


    /**
     * Get staff by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  staff (null if not found)
     */
    private static Staff getStaffByFiscalCode(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Staff> cq = cb.createQuery(Staff.class);
        Root<Staff> root = cq.from(Staff.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Staff> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
