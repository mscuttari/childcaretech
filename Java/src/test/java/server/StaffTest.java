package test.java.server;

import main.java.exceptions.InvalidFieldException;
import main.java.models.Staff;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StaffTest extends PersonTest<Staff> {

    StaffTest() {
        super(Staff.class);
    }


    /** {@inheritDoc} */
    @Override
    void assertModelsEquals(Staff x, Staff y) {
        super.assertModelsEquals(x, y);

        assertEquals(x.getUsername(), y.getUsername());
        assertEquals(x.getPassword(), y.getPassword());
    }


    /** {@inheritDoc} */
    @Override
    void assignValidData(Staff obj) {
        super.assignValidData(obj);

        obj.setUsername("user");
        obj.setPassword("password");
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void dbTest() {
        Staff staff = new Staff();
        assignValidData(staff);

        // Create
        HibernateUtils.getInstance().create(staff);
        Staff createdStaff = getStaff(staff.getFiscalCode());

        // Check creation
        assertNotNull(createdStaff);
        assertModelsEquals(staff, createdStaff);

        // Update
        staff.setFirstName("BBB");
        staff.setLastName("CCC");
        staff.setBirthdate(new Date());
        staff.setAddress("Test, A/2");
        staff.setTelephone("2222222222");

        HibernateUtils.getInstance().update(staff);

        // Check update
        Staff updatedStaff = getStaff(staff.getFiscalCode());
        assertNotNull(updatedStaff);
        assertModelsEquals(staff, updatedStaff);

        // Delete
        HibernateUtils.getInstance().delete(staff);

        // Check delete
        Staff deletedStaff = getStaff(staff.getFiscalCode());
        assertNull(deletedStaff);
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void fiscalCodeValidity() {
        super.fiscalCodeValidity(new Staff());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void firstNameValidity() {
        super.firstNameValidity(new Staff());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void lastNameValidity() {
        super.lastNameValidity(new Staff());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void birthDateValidity() {
        super.birthDateValidity(new Staff());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void addressValidity() {
        super.addressValidity(new Staff());
    }


    /** {@inheritDoc} */
    @Test
    @Override
    void telephoneValidity() {
        super.telephoneValidity(new Staff());
    }


    /**
     * Test the username validity control
     */
    @Test
    void usernameValidity() {
        Staff staff = new Staff();

        // Valid data
        assignValidData(staff);
        assertDoesNotThrow(staff::checkDataValidity);

        // Invalid data
        staff.setUsername(null);
        assertThrows(InvalidFieldException.class, staff::checkDataValidity);
    }


    /**
     * Test the password validity control
     */
    @Test
    void passwordValidity() {
        Staff staff = new Staff();

        // Valid data
        assignValidData(staff);
        assertDoesNotThrow(staff::checkDataValidity);

        // Invalid data
        staff.setPassword(null);
        assertThrows(InvalidFieldException.class, staff::checkDataValidity);
    }


    /**
     * Get staff by fiscal code
     *
     * @param   fiscalCode      fiscal code
     * @return  staff (null if not found)
     */
    public static Staff getStaff(String fiscalCode) {
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
