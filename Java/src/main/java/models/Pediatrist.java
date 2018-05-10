package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPediatrist;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Pediatrist")
@DiscriminatorValue("pediatrist")
public class Pediatrist extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPediatrist.class;
    }

    private Collection<Child> curing = new ArrayList<>();

    public Pediatrist(){
        this(null, null, null, null, null, null);
    }

    public Pediatrist(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }

    @OneToMany(mappedBy = "pediatrist")
    public Collection<Child> getCuring() {
        return curing;
    }

    public void setCuring(Collection<Child> curing) {
        this.curing = curing;
    }

}
