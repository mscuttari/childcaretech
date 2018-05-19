package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiRegularMenu;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "AlternativeMenu")
@Table(name = "alternative_menus")
@DiscriminatorValue("alternative")
public class AlternativeMenu extends Menu {

    private static final long serialVersionUID = -3413541435253247635L;

    @ManyToOne
    @JoinColumn(name = "regular_menu_name", referencedColumnName = "name")
    private RegularMenu regularMenu;

    @ManyToMany
    @JoinTable(
            name = "alternative_menus_people",
            joinColumns = { @JoinColumn(name = "alternative_menu_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "person_fiscal_code", referencedColumnName = "fiscal_code") }
    )
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
     * @param   name            name
     * @param   responsible     responsible
     * @param   regularMenu     regular menu
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


    public RegularMenu getRegularMenu() {
        return regularMenu;
    }


    public void setRegularMenu(RegularMenu regularMenu) {
        this.regularMenu = regularMenu;
    }


    public Collection<Person> getPeople() {
        return people;
    }


    public void addPerson(Person person) {
        this.people.add(person);
    }


    public void addPeople(Collection<Person> people) {
        this.people.addAll(people);
    }

}