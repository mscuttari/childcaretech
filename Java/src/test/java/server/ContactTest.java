package test.java.server;

import main.java.client.InvalidFieldException;
import main.java.models.Contact;
import main.java.models.Parent;
import main.java.server.utils.HibernateUtils;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static test.java.utils.TestUtils.assertDateEquals;

public class ContactTest extends PersonTest<Contact> {

    ContactTest() {
        super(Contact.class);
    }

}
