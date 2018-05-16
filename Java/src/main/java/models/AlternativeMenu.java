package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiRegularMenu;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "AlternativeMenu")
@DiscriminatorValue("alternative")
public class AlternativeMenu extends Menu {

    private RegularMenu regularMenu;

    private Collection<Person> people = new ArrayList<>();

    /**
     * Default constructor
     */
    public AlternativeMenu() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name      name
     * @param   responsible       responsible
     * @param   regularMenu      regular menu
     */
    public AlternativeMenu(String name, Staff responsible, RegularMenu regularMenu) {
        super(name, responsible);

        this.regularMenu = regularMenu;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() { return GuiRegularMenu.class; }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AlternativeMenu)) return false;

        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @ManyToOne
    @JoinColumn(name = "regularMenu_id")
    public RegularMenu getRegularMenu() {
        return regularMenu;
    }

    public void setRegularMenu(RegularMenu regularMenu) {
        this.regularMenu = regularMenu;
    }

    @ManyToMany
    @JoinTable(
            name = "personalized_menus_people",
            joinColumns = { @JoinColumn(name = "menu_id") },
            inverseJoinColumns = { @JoinColumn(name = "person_id") }
    )
    public Collection<Person> getPeople() {
        return people;
    }

    public void setPeople(Collection<Person> people) {
        this.people = people;
    }
}