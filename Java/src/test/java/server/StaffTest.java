package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Staff;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.java.utils.TestUtils.assertDateEquals;

class StaffTest extends PersonTest<Staff> {

    StaffTest() {
        super(Staff.class);
    }


    /**
     * Creation, update and delete of a valid parent
     */
    @Test
    void dbTest() {
        Staff staff = new Staff("AAAAAAAAAAAAAAAA", "AAA", "BBB", new Date(), "Test, A/1", "+39 111 1111111", "user", "pass");

        // Create
        HibernateUtils.getInstance().create(staff);
        Staff createdStaff = getPersonByFiscalCode(staff.getFiscalCode());

        // Check creation
        assertNotNull(createdStaff);

        assertEquals(staff.getFiscalCode(), createdStaff.getFiscalCode());
        assertEquals(staff.getFirstName(), createdStaff.getFirstName());
        assertEquals(staff.getLastName(), createdStaff.getLastName());
        assertDateEquals(staff.getBirthdate(), createdStaff.getBirthdate());
        assertEquals(staff.getAddress(), createdStaff.getAddress());
        assertEquals(staff.getTelephone(), createdStaff.getTelephone());

        // Update
        createdStaff.setFiscalCode("BBBBBBBBBBBBBBBB");
        createdStaff.setFirstName("CCC");
        createdStaff.setLastName("DDD");
        createdStaff.setBirthdate(new Date());
        createdStaff.setAddress("Test, A/2");
        createdStaff.setTelephone("2222222222");

        HibernateUtils.getInstance().update(createdStaff);
        Staff updatedStaff = getPersonByFiscalCode(createdStaff.getFiscalCode());

        // Check update
        Staff oldStaff = getPersonByFiscalCode(staff.getFiscalCode());
        assertNull(oldStaff);       // The old fiscal code should not be found anymore

        assertNotNull(updatedStaff);

        assertEquals(createdStaff.getFiscalCode(), updatedStaff.getFiscalCode());
        assertEquals(createdStaff.getFirstName(), updatedStaff.getFirstName());
        assertEquals(createdStaff.getLastName(), updatedStaff.getLastName());
        assertDateEquals(createdStaff.getBirthdate(), updatedStaff.getBirthdate());
        assertEquals(createdStaff.getAddress(), updatedStaff.getAddress());
        assertEquals(createdStaff.getTelephone(), updatedStaff.getTelephone());

        // Delete
        HibernateUtils.getInstance().delete(updatedStaff);

        // Check delete
        Staff deletedStaff = getPersonByFiscalCode(updatedStaff.getFiscalCode());
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
        Staff staff = createBasicStaff();

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
        Staff staff = createBasicStaff();

        // Valid data
        assignValidData(staff);
        assertDoesNotThrow(staff::checkDataValidity);

        // Invalid data
        staff.setPassword(null);
        assertThrows(InvalidFieldException.class, staff::checkDataValidity);
    }


    /**
     * Create a staff object
     *
     * @return  staff object
     */
    private static Staff createBasicStaff() {
        Staff staff = new Staff();

        staff.setUsername("user");
        staff.setPassword("pass");

        return staff;
    }

}
