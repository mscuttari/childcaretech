package main.java.utils;

import main.java.LogUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtils {

    // Debug
    private static final String TAG = "HibernateUtils";

    // Singleton
    private static HibernateUtils instance = null;

    // Entity manager
    private static final String persistanceUnit = "ChildCareTechPU";
    private EntityManager entityManager;


    /**
     * Constructor
     *
     * Create the entity manager to be used in transactions
     */
    private HibernateUtils() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistanceUnit);
        entityManager = emf.createEntityManager();
    }


    /**
     * Get singleton instance
     *
     * @return  singleton instance
     */
    public static HibernateUtils getInstance() {
        if (instance == null)
            instance = new HibernateUtils();

        return instance;
    }


    /**
     * Flush and clear
     *
     * @see EntityManager#flush()
     * @see EntityManager#clear()
     */
    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }


    /**
     * Create element
     *
     * @param   obj     object to be created
     * @return  true if everything went fine; false otherwise
     */
    public boolean create(Object obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
            flushAndClear();
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Update element
     *
     * @param   obj     object to be updated
     * @return  true if everything went fine; false otherwise
     */
    public boolean update(Object obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();
            flushAndClear();
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Delete element
     *
     * @param   obj     object to be deleted
     * @return  true if everything went fine; false otherwise
     */
    public boolean delete(Object obj) {
        try {
            entityManager.remove(obj);
            flushAndClear();
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
