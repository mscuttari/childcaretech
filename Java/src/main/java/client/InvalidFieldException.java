package main.java.client;

public class InvalidFieldException extends Exception {

    public InvalidFieldException() {
        this(null);
    }

    public InvalidFieldException(String message){
        super(message);
    }

}
