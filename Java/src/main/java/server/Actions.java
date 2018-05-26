package main.java.server;

import main.java.models.Staff;
import main.java.server.utils.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Actions {

    private Actions() {

    }


    /**
     * Check user credentials
     *
     * @param   username    String      username
     * @param   password    String      password
     *
     * @return  true if the credentials are valid, false otherwise
     */
    public static boolean login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            return false;

        EntityManager em = HibernateUtils.getInstance().getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Staff> cq = cb.createQuery(Staff.class);
        Root<Staff> root = cq.from(Staff.class);
        cq.where(cb.equal(root.get("username"), username));
        TypedQuery<Staff> q = em.createQuery(cq);
        List<Staff> people = q.getResultList();
        em.close();

        for (Staff person : people) {
            if (password.equals(person.getPassword()))
                return true;
        }

        return false;
    }

}
