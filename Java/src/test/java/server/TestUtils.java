package test.java.server;

import org.junit.jupiter.api.Assertions;

import java.sql.Timestamp;
import java.util.Date;

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
        if (x instanceof Timestamp && !(y instanceof Timestamp)) {
            Timestamp ts = new Timestamp(y.getTime());
            //ts.setNanos(0);
            Assertions.assertTrue(x.compareTo(ts) == 0);

        } else if (!(x instanceof Timestamp) && y instanceof Timestamp) {
            Timestamp ts = new Timestamp(x.getTime());
            //ts.setNanos(0);
            Assertions.assertTrue(y.compareTo(ts) == 0);

        } else {
            Assertions.assertTrue(x.compareTo(y) == 0);
        }
    }

}
