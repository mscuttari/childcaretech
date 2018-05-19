package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiMenu;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "menus")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Menu extends BaseModel {

    @Transient
    private static final long serialVersionUID = -8284040804525605098L;

    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "responsible_fiscal_code", referencedColumnName = "fiscal_code")
    private Staff responsible;

    @ManyToMany
    @JoinTable(
            name = "menus_composition",
            joinColumns = { @JoinColumn(name = "menu_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "dish_name", referencedColumnName = "name") }
    )
    private Collection<Dish> dishes = new ArrayList<>();


    /**
     * Default constructor
     */
    public Menu() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   name            name
     * @param   responsible     responsible (staff person)
     */
    public Menu(String name, Staff responsible) {
        this.name = name;
        this.responsible = responsible;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (name == null || name.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Responsible
        if (responsible == null) throw new InvalidFieldException("Responsabile mancante");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiMenu.class;
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
        return name;
    }


    public void setName(String name) {
        if (this.name == null) {
            this.name = name == null || name.isEmpty() ? null : name;
        }
    }


    public Staff getResponsible() {
        return responsible;
    }


    public void setResponsible(Staff responsible) {
        this.responsible = responsible;
    }


    public Collection<Dish> getDishes() {
        return dishes;
    }


    public void addDish(Dish dish) {
        dishes.add(dish);
    }


    public void addDishes(Collection<Dish> dishes) {
        this.dishes.addAll(dishes);
    }

}
