package main.java.utils;

import main.java.exceptions.DatabaseException;
import main.java.models.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.Collection;

public class HibernateUtil {

    // Singleton
    private static HibernateUtil instance = null;

    // Entity Manager
    private static final String persistenceUnit = "ChildCareTechPU";
    private static EntityManagerFactory emf;


    /**
     * Constructor
     *
     * Create the entity manager to be used in transactions
     */
    private HibernateUtil() {
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
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
     * Get element by id
     *
     * @param   modelClass      Class       element class
     * @param   id              Long        element ID
     *
     * @return  M               element object
     *
     * @throws  DatabaseException   if the element can't be retrieved
     */
    @Transactional
    public synchronized <M extends BaseModel> M getById(Class<M> modelClass, Long id) throws DatabaseException {
        if (id == null) return null;

        EntityManager em = emf.createEntityManager();

        try {
            M result = em.find(modelClass, id);
            em.close();
            return result;
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Create element
     *
     * @param   obj         M           element object
     * @return  M           created element object
     * @throws  DatabaseException       if the element can't be created
     */
    @Transactional
    public synchronized <M extends BaseModel> M create(M obj) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            em.close();
        }

        return obj;
    }


    /**
     * Update elements
     *
     * @param   obj         M           element object
     * @return  M           updated element object
     * @throws  DatabaseException       if the element can't be updated
     *
     */
    @Transactional
    public synchronized <M extends BaseModel> M update(M obj) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            return em.merge(obj);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Delete element
     *
     * @param   obj         M           element object
     * @throws  DatabaseException       if the element can't be deleted
     */
    @Transactional
    public final synchronized <M extends BaseModel> void delete(M obj) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            em.close();
        }
    }

}
