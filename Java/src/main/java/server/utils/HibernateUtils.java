package main.java.server.utils;

import main.java.LogUtils;
import main.java.server.exceptions.DatabaseException;
import main.java.models.BaseModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HibernateUtils {

    // Debug
    private static final String TAG = "HibernateUtils";

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
     * @param   modelClass      element class
     * @param   id              element ID
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
     * Get entity manager
     *
     * @return  entity manager
     */
    public synchronized EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    /**
     * Flush and clear
     *
     * @param   entityManager       entity manager
     *
     * @see EntityManager#flush()
     * @see EntityManager#clear()
     */
    public void flushAndClear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }


    /**
     * Get single result
     *
     * @param   query   query to be run
     * @return  result object (null if no item or error)
     */
    public static synchronized <M extends BaseModel> M getSingleResult(TypedQuery<M> query) {
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Get results list
     *
     * @param   query   query to be run
     * @return  results list (empty if no items or error)
     */
    public static synchronized <M extends BaseModel> List<M> getResultsList(TypedQuery<M> query) {
        try {
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    /**
     * Get all the elements
     *
     * @param   modelClass          model class
     * @return  list of elements objects (null in case of error)
     */
    @Transactional
    public synchronized <M extends BaseModel> List<M> getAll(Class<M> modelClass) {
        EntityManager em = emf.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<M> cq = cb.createQuery(modelClass);
        cq.from(modelClass);
        TypedQuery<M> q = em.createQuery(cq);

        try {
            return getResultsList(q);
        } finally {
            em.close();
        }
    }


    /**
     * Create element
     *
     * @param   obj     element object
     * @return  true if everything went fine; false otherwise
     */
    @Transactional
    public synchronized boolean create(BaseModel obj) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(em.contains(obj) ? obj : em.merge(obj));
            em.getTransaction().commit();
            //flushAndClear(em);
            return true;

        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }


    /**
     * Update elements
     *
     * @param   obj     element object
     * @return  true if everything went fine; false otherwise
     *
     */
    @Transactional
    public synchronized boolean update(BaseModel obj) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
            //flushAndClear(em);
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }


    /**
     * Delete element
     *
     * @param   obj     element object
     * @return  true if everything went fine; false otherwise
     */
    @Transactional
    public final synchronized boolean delete(BaseModel obj) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.remove(em.contains(obj) ? obj : em.merge(obj));
            em.getTransaction().commit();
            //flushAndClear(em);
            return true;

        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
            e.printStackTrace();
            return false;

        } finally {
            em.close();
        }
    }

}
