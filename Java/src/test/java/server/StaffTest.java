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
        Staff createdStaff = getPersonByFiscalCode(staff.getFiscalCode());

        // Check creation
        assertNotNull(createdStaff);
        assertModelsEquals(staff, createdStaff);

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
        assertModelsEquals(createdStaff, updatedStaff);

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

}
