package main.java.exceptions;

public class DatabaseException extends Exception {

    private String message;

    public DatabaseException(String message){
        this.message = message;
    }

    public DatabaseException() {
        this(null);
    }

    @Override
    public String getMessage(){
        return message == null ? "" : message;
    }

}