package main.java.models;

import javax.persistence.Column;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public abstract class BaseModel {

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

}
