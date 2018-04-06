package main.java;

public class LogUtils {

    private LogUtils() {

    }


    /**
     * Log debug message
     *
     * @param   TAG         String      tag (displayed before the message)
     * @param   message     String      message to be shown
     */
    public static void d(String TAG, String message) {
        System.out.println(TAG + ": " + message);
    }


    /**
     * Log error message
     *
     * @param   TAG         String      tag (displayed before the message)
     * @param   message     String      message to be shown
     */
    public static void e(String TAG, String message) {
        System.err.println(TAG + ": " + message);
    }

}
