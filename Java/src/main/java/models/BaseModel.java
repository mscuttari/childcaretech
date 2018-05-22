package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
     * Get the class used to represent the object in the GUI
     *
     * @return  GUI model class
     */
    public abstract Class<? extends GuiBaseModel> getGuiClass();


    /**
     * Get string representation of the object
     *
     * @return      String      textual data
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        ArrayList<Method> columns = getMethodsWithAnnotation(Column.class);

        for (Method column : columns) {
            column.setAccessible(true);

            String name = column.getAnnotation(Column.class).name();
            Object value;

            try {
                value = column.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                continue;
            }

            if (value != null)
                result.append(name).append(" = ").append(value).append("; ");
        }

        if (result.length() == 0) {
            return "null";
        } else {
            result.insert(0, this.getClass().getSimpleName() + ": ");
            return result.toString();
        }
    }


    /**
     * Get the methods with a specified annotation
     *
     * @param   annotationClass     Class       desired annotation class
     * @return  ArrayList           list of methods
     */
    private ArrayList<Method> getMethodsWithAnnotation(Class<? extends Annotation> annotationClass) {
        ArrayList<Method> methods = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredMethods()));

        for (Iterator<Method> it = methods.iterator(); it.hasNext(); ) {
            Method method = it.next();
            method.setAccessible(true);

            if (!method.isAnnotationPresent(annotationClass))
                it.remove();
        }

        return methods;
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
