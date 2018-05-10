package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiContact;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "Contact")
@DiscriminatorValue("contact")
public class Contact extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiContact.class;
    }

}
