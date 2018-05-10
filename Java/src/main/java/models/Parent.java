package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiParent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "Parent")
@DiscriminatorValue("parent")
public class Parent extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiParent.class;
    }

}
