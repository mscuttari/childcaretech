package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiIngredient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseModel {

    @Transient
    private static final long serialVersionUID = 2539848520616597694L;


    @Id
    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "ingredients")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Dish> dishes = new ArrayList<>();


    @ManyToMany(mappedBy = "allergies")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Person> allergicPeople = new ArrayList<>();


    @ManyToMany(mappedBy = "intolerances")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Person> intolerantPeople = new ArrayList<>();


    /**
     * Default constructor
     */
    public Ingredient() {
        this(null);
    }


    /**
     * Constructor
     *
     * @param   name    name
     */
    public Ingredient(String name) {
        this.name = name;
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (name == null) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiIngredient.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;

        Ingredient that = (Ingredient) o;
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
        this.name = name == null || name.isEmpty() ? null : name;
    }


    public Collection<Dish> getDishes() {
        return this.dishes;
    }


    public void addToDish(Dish dish) {
        this.dishes.add(dish);
    }


    public void addToDishes(Collection<Dish> dishes) {
        this.dishes.addAll(dishes);
    }


    public Collection<Person> getAllergicPeople() {
        return this.allergicPeople;
    }


    public void addAllergicPerson(Person person) {
        this.allergicPeople.add(person);
    }


    public void addAllergicPeople(Collection<Person> people) {
        this.allergicPeople.addAll(people);
    }


    public void setAllergicPeople(Collection<Person> people) {
        this.allergicPeople.clear();
        addAllergicPeople(people);
    }


    public Collection<Person> getIntolerantPeople() {
        return this.intolerantPeople;
    }


    public void addIntolerantPerson(Person person) {
        this.intolerantPeople.add(person);
    }


    public void addIntolerantPeople(Collection<Person> people) {
        this.intolerantPeople.addAll(people);
    }


    public void setIntolerantPeople(Collection<Person> people) {
        this.intolerantPeople.clear();
        addIntolerantPeople(people);
    }

}
