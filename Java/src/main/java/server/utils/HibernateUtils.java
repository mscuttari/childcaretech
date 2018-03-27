package main.java.server.utils;

import main.java.exceptions.DatabaseException;
import main.java.models.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

public class HibernateUtils {

    // Singleton
    private static HibernateUtils instance = null;

    // Entity Manager
    private static final String persistenceUnit = "ChildCareTechPU";
    private static EntityManagerFactory emf;


    /**
     * Constructor
     *
     * Create the entity manager to be used in transactions
     */
    private HibernateUtils() {
        emf = Persistence.createEntityManagerFactory(persistenceUnit);
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
     * Get element by id
     *
     * @param   modelClass      Class       element class
     * @param   id              Long        element ID
     *
     * @return  element object
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
     * Get all the elements
     *
     * @param   modelClass          Class       model class
     * @return  list of elements objects
     * @throws  DatabaseException   if the data can't be retrieved
     */
    @Transactional
    public synchronized <M extends BaseModel> List<M> getAll(Class<M> modelClass) throws DatabaseException {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<M> cq = cb.createQuery(modelClass);
        cq.from(modelClass);
        TypedQuery<M> q = em.createQuery(cq);

        try {
            return q.getResultList();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            em.close();
        }
    }


    /**
     * Get entity manager
     *
     * @return  entity manager
     */
    public synchronized EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    /**
     * Create element
     *
     * @param   obj         M           element object
     * @return  created element object
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
     * @return  updated element object
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
