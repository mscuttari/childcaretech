package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiStaff;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "Staff")
@DiscriminatorValue("staff")
public class Staff extends Person {

    private String username;
    private String password;

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiStaff.class;
    }

}
