package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPediatrist;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity(name = "Pediatrist")
@DiscriminatorValue("pediatrist")
public class Pediatrist extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPediatrist.class;
    }

}
