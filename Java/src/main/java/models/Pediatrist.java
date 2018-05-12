package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiPediatrist;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Pediatrist")
@DiscriminatorValue("pediatrist")
public class Pediatrist extends Person {

    // Serialization
    private static final long serialVersionUID = -2598504963548813092L;

    private Collection<Child> curing = new ArrayList<>();


    /**
     * Default constructor
     */
    public Pediatrist(){
        this(null, null, null, null, null, null);
    }


    /**
     * Constructor
     *
     * @param   fiscalCode      fiscal code
     * @param   firstName       first name
     * @param   lastName        last name
     * @param   birthDate       birth date
     * @param   address         address
     * @param   telephone       telephone
     */
    public Pediatrist(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiPediatrist.class;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Pediatrist)) return false;

        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @OneToMany(mappedBy = "pediatrist")
    public Collection<Child> getCuring() {
        return curing;
    }

    public void setCuring(Collection<Child> curing) {
        this.curing = curing;
    }

}
