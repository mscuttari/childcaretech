package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiMenu;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "menus")
public class Menu extends BaseModel {

    @Transient
    private static final long serialVersionUID = -8284040804525605098L;


    @Id
    @Column(name = "name")
    private String name;


    @Column(name = "day_of_the_week", nullable = false)
    private DayOfTheWeek dayOfTheWeek;


    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "responsible_fiscal_code", referencedColumnName = "fiscal_code")
    private Staff responsible;


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "menus_composition",
            joinColumns = { @JoinColumn(name = "menu_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "dish_name", referencedColumnName = "name") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Dish> dishes = new ArrayList<>();


    /**
     * Default constructor
     */
    public Menu() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name                name
     * @param   responsible         responsible (staff person)
     * @param   dayOfTheWeek        day of the week
     */
    public Menu(String name, Staff responsible, DayOfTheWeek dayOfTheWeek) {
        setName(name);
        setResponsible(responsible);
        setDayOfTheWeek(dayOfTheWeek);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (getName() == null)
            throwFieldError("Nome mancante");

        if (!getName().matches("^[a-zA-Z\\040]+$"))
            throwFieldError("Nome non valido");

        // Day of the week
        if (getDayOfTheWeek() == null)
            throwFieldError("Giorno della settimana mancante");

        // Responsible
        if (getResponsible() == null)
            throwFieldError("Responsabile mancante");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Menù";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiMenu.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return true;
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {
        // Staff
        if (getResponsible() != null)
            getResponsible().removeMenu(this);

        // Dishes
        for (Dish dish : getDishes()) {
            dish.removeFromMenu(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu that = (Menu) o;
        return Objects.equals(getName(), that.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = trimString(name);
    }


    public DayOfTheWeek getDayOfTheWeek() {
        return this.dayOfTheWeek;
    }


    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }


    public Staff getResponsible() {
        return this.responsible;
    }


    public void setResponsible(Staff responsible) {
        this.responsible = responsible;
    }


    public Collection<Dish> getDishes() {
        return this.dishes;
    }


    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    public void addDishes(Collection<Dish> dishes) {
        this.dishes.addAll(dishes);
    }


    public void setDishes(Collection<Dish> dishes) {
        this.dishes.clear();
        addDishes(dishes);
    }

    @Override
    public String toString(){ return "[" + getDayOfTheWeek() + "] - " + getName() ; }

}
