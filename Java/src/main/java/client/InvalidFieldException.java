package main.java.client;

public class InvalidFieldException extends Exception {

    private static final long serialVersionUID = -8444016514132008405L;

    private String modelName;

    public InvalidFieldException(String modelName, String message){
        super(message);

        this.modelName = modelName;
    }

    public String getModelName() {
        return this.modelName;
    }

}
