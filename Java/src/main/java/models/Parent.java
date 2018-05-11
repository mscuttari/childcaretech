package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiParent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "Parent")
@DiscriminatorValue("parent")
public class Parent extends Person {

    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiParent.class;
    }

    private Collection<Child> children = new ArrayList<>();

    public Parent(){
        this(null, null, null, null, null, null);
    }

    public Parent(String fiscalCode, String firstName, String lastName, Date birthDate, String address, String telephone) {
        super(fiscalCode, firstName, lastName, birthDate, address, telephone);
    }

    @ManyToMany(mappedBy = "parents")
    public Collection<Child> getChildren() {
        return children;
    }

    public void setChildren(Collection<Child> children) {
        this.children = children;
    }

}
