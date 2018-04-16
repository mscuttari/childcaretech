package main.java.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseModel {

    private Long id;
    private String name;
    private Collection<Food> food = new ArrayList<>();
    private Collection<Person> allergicPeople = new ArrayList<>();
    private Collection<Person> intollerantPeople = new ArrayList<>();

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
