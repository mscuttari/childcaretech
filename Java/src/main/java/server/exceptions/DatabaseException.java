package main.java.server.exceptions;

public class DatabaseException extends Exception {

    public DatabaseException() {
        this(null);
    }

    public DatabaseException(String message){
        super(message);
    }

}