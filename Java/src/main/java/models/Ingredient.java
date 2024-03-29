package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiIngredient;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "ingredients")
@NamedQuery(name = "Ingredient.search", query = "SELECT i FROM Ingredient i WHERE i.name = :name")
public class Ingredient extends BaseModel {

    @Transient
    private static final long serialVersionUID = 2539848520616597694L;


    @Id
    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "ingredients")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Dish> dishes = new HashSet<>();


    @ManyToMany(mappedBy = "allergies")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Person> allergicPeople = new HashSet<>();


    @ManyToMany(mappedBy = "intolerances")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Person> intolerantPeople = new HashSet<>();


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
    public String getSearchQueryName() {
        return "Ingredient.search";
    }


    /** {@inheritDoc} */
    @Override
    public boolean runSearchQuery(Query query) {
        query.setParameter("name", getName());
        return !query.getResultList().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] à è é ì ò ù ' " space
        if (getName() == null)
            throwFieldError("Nome mancante");

        if (!getName().matches("^[a-zA-Zàèéìòù'\"\\040]+$"))
            throwFieldError("Nome non valido");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Ingrediente";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiIngredient.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return getDishes().isEmpty() &&
                getAllergicPeople().isEmpty() &&
                getIntolerantPeople().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {

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
        this.name = trimString(name);
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


    public void removeFromDish(Dish dish) {
        this.dishes.remove(dish);
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


    @Override
    public String toString(){
        return getName();
    }

}
