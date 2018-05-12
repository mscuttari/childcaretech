package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiIngredient;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseModel {

    private Long id;
    private String name;
    private Collection<Food> food = new ArrayList<>();
    private Collection<Person> allergicPeople = new ArrayList<>();
    private Collection<Person> intollerantPeople = new ArrayList<>();


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
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (name == null || name.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");
    }


    /** {@inheritDoc} */
    @Transient
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


    @Id
    @GenericGenerator(name = "native_generator", strategy = "native")
    @GeneratedValue(generator = "native_generator")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "composition")
    public Collection<Food> getFood() {
        return food;
    }

    public void setFood(Collection<Food> food) {
        this.food = food;
    }

    @ManyToMany(mappedBy = "allergies")
    public Collection<Person> getAllergicPeople() {
        return allergicPeople;
    }

    public void setAllergicPeople(Collection<Person> allergicPeople) {
        this.allergicPeople = allergicPeople;
    }

    @ManyToMany(mappedBy = "intollerances")
    public Collection<Person> getIntollerantPeople() {
        return intollerantPeople;
    }

    public void setIntollerantPeople(Collection<Person> intollerantPeople) {
        this.intollerantPeople = intollerantPeople;
    }

    @Override
    public String toString(){
        return name;
    }

}
