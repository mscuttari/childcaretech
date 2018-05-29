package main.java.client.connection;

import java.io.Serializable;

public abstract class BaseClient implements Serializable {

    // Serialization
    private static final long serialVersionUID = -8783220393218317507L;

    // Login data
    private String username;
    private String password;


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

}
