package test.java.utils;

import main.java.models.BaseModel;
import org.junit.jupiter.api.Assertions;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtils {

    private TestUtils() {

    }

    /**
     * Assert equality between to dates
     *
     * This method relies on the Date.compareTo() method because of implementation
     * failure of Hibernate in retrieving the saved date
     *
     * For a more detailed explanation of the problem, see the explanation on
     * @see <a href="https://stackoverflow.com/questions/23021648/why-is-assertequals-false-if-it-is-the-same-date-hibernate">StackOverflow</a>
     *
     * @param   x   first date
     * @param   y   second date
     */
    public static void assertDateEquals(Date x, Date y) {
        assertTrue(BaseModel.dateEquals(x, y));
    }

}
