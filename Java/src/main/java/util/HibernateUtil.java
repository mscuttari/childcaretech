package main.java.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    // Singleton
    private static HibernateUtil instance = null;

    // Entity Manager
    private static final String persistanceUnit = "ChildCareTechPU";
    private EntityManager entityManager;


    /**
     * Constructor
     *
     * Create the entity manager to be used in transactions
     */
    private HibernateUtil() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistanceUnit);
        entityManager = emf.createEntityManager();
    }


    /**
     * Get singleton instance
     *
     * @return  HibernateUtil   instance
     */
    public static HibernateUtil getInstance() {
        if (instance == null)
            instance = new HibernateUtil();

        return instance;
    }


    /**
     * Save object in the database
     *
     * @param   obj     Object      object to be persisted
     */
    public void doTransaction(Object obj) {
        entityManager.getTransaction().begin();
        entityManager.persist(obj);
        entityManager.getTransaction().commit();
    }

}
