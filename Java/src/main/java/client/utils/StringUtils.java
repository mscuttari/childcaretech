package main.java.client.utils;

public class StringUtils {

    private StringUtils() {

    }

    /**
     * Check if a string contains only characters
     *
     * @param   str     string to be checked
     * @return  true if the string contains only [a-z] or [A-Z] characters; false otherwise
     */
    public static boolean containsOnlyChars(String str) {
        return str == null || str.matches("^[a-zA-Z\\040]+$");
    }

}
