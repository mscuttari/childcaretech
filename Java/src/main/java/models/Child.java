package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiChild;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "Child")
@DiscriminatorValue("child")
public class Child extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiChild.class;
    }

}
