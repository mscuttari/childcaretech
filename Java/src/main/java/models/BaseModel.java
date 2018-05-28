package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public abstract class BaseModel implements Serializable {

    // Serialization
    private static final long serialVersionUID = 5852497063598077389L;


    /**
     * Check if fields contains valid data
     *
     * @throws  InvalidFieldException   if any of the fields is invalid
     */
    public abstract void checkDataValidity() throws InvalidFieldException;


    /**
     * Get model name
     *
     * @return  friendly model name
     */
    public abstract String getModelName();


    /**
     * Get the class used to represent the object in the GUI
     *
     * @return  GUI model class
     */
    public abstract Class<? extends GuiBaseModel> getGuiClass();


    /**
     * Check if the entity can be deleted
     *
     * @return  true if the entity can be deleted
     */
    public abstract boolean isDeletable();


    /**
     * Remove associations from parent entities
     */
    public abstract void preDelete();


    /**
     * Trim string
     *
     * @param   str     string to be trimmed
     * @return  trimmed string (or null if the string is empty)
     */
    protected String trimString(String str) {
        return str == null || str.trim().isEmpty() ? null : str.trim();
    }


    /**
     * Throws an exceptions for a field value error
     *
     * @param   message     error message
     * @throws  InvalidFieldException   exception containing the error
     */
    protected void throwFieldError(String message) throws InvalidFieldException {
        throw new InvalidFieldException(getModelName(), message);
    }


    /**
     * Check equality between to dates
     *
     * This method relies on the Date.compareTo() method because of implementation
     * failure of Hibernate in retrieving the saved date
     *
     * For a more detailed explanation of the problem, see the explanation on
     * @see <a href="https://stackoverflow.com/questions/23021648/why-is-assertequals-false-if-it-is-the-same-date-hibernate">StackOverflow</a>
     *
     * @param   x   first date
     * @param   y   second date
     *
     * @return  true if the date are equal; false otherwise
     */
    public static boolean dateEquals(Date x, Date y) {
        if (x instanceof Timestamp && !(y instanceof Timestamp)) {
            Timestamp ts = new Timestamp(y.getTime());
            ((Timestamp)x).setNanos(0);
            ts.setNanos(0);
            return x.compareTo(ts) == 0;

        } else if (!(x instanceof Timestamp) && y instanceof Timestamp) {
            Timestamp ts = new Timestamp(x.getTime());
            ts.setNanos(0);
            ((Timestamp)y).setNanos(0);
            return y.compareTo(ts) == 0;

        } else {
            return x.compareTo(y) == 0;
        }
    }

}
