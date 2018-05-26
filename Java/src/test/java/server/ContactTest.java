package test.java.server;

import main.java.models.Contact;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest extends PersonTest<Contact> {

    ContactTest() {
        super(Contact.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Contact x, Contact y) {
        super.assertModelsEquals(x, y);
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Contact obj) {
        super.assignValidData(obj);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Contact contact = new Contact();
        assignValidData(contact);

        // Create
        HibernateUtils.getInstance().create(contact);
        Contact createdContact = getContact(contact.getFiscalCode());

        // Check creation
        assertNotNull(createdContact);
        assertModelsEquals(contact, createdContact);

        // Update
        contact.setFirstName("BBB");
        contact.setLastName("CCC");
        contact.setBirthdate(new Date());
        contact.setAddress("Test, A/2");
        contact.setTelephone("2222222222");

        HibernateUtils.getInstance().update(contact);

        // Check update
        Contact updatedContact = getContact(contact.getFiscalCode());
        assertNotNull(updatedContact);
        assertModelsEquals(contact, updatedContact);

        // Delete
        HibernateUtils.getInstance().delete(contact);

        // Check delete
        Contact deletedContact = getContact(contact.getFiscalCode());
        assertNull(deletedContact);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Contact());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Contact());
    }


    /**
     * Get contact by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  contact (null if not found)
     */
    public static Contact getContact(String fiscalCode) {
        HibernateUtils hibernateUtils = HibernateUtils.getInstance();
        EntityManager em = hibernateUtils.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> root = cq.from(Contact.class);
        cq.where(cb.equal(root.get("fiscalCode"), fiscalCode));
        TypedQuery<Contact> q = em.createQuery(cq);

        return HibernateUtils.getSingleResult(q);
    }

}
