package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiDish;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "dishes")
public class Dish extends BaseModel {

    @Transient
    private static final long serialVersionUID = 4169234131175265564L;


    @Id
    @Column(name = "name")
    private String name;


    @Column(name = "type", nullable = false)
    private DishType type;


    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "provider_vat", nullable = false)
    private Provider provider;


    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(
            name = "dishes_composition",
            joinColumns = { @JoinColumn(name = "dish_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Ingredient> ingredients = new ArrayList<>();


    @ManyToMany(mappedBy = "dishes", cascade = {PERSIST, MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Menu> menus = new ArrayList<>();


    /**
     * Default constructor
     */
    public Dish() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name        name
     * @param   type        type
     * @param   provider    provider
     */
    public Dish(String name, DishType type, Provider provider) {
        setName(name);
        setType(type);
        setProvider(provider);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (getName() == null) throw new InvalidFieldException("Nome mancante");
        if (!getName().matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Dish type
        if (getType() == null) throw new InvalidFieldException("Tipologia mancante");

        // Provider
        if (getProvider().getName() == null) throw new InvalidFieldException("Fornitore mancante");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiDish.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return menus.isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {
        // Provider
        getProvider().removeDish(this);

        // Ingredients
        for (Ingredient ingredient : getIngredients()) {
            ingredient.removeFromDish(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish that = (Dish) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getType(), that.getType());
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


    public DishType getType() {
        return this.type;
    }


    public void setType(DishType type) {
        this.type = type;
    }


    public Provider getProvider() {
        return this.provider;
    }


    public void setProvider(Provider provider) {
        this.provider = provider == null ? new Provider() : provider;
    }


    public Collection<Ingredient> getIngredients() {
        return this.ingredients;
    }


    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }


    public void addIngredients(Collection<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }


    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients.clear();
        addIngredients(ingredients);
    }


    public Collection<Menu> getMenus() {
        return this.menus;
    }


    public void addToMenu(Menu menu) {
        this.menus.add(menu);
    }


    public void addToMenus(Collection<Menu> menus) {
        this.menus.addAll(menus);
    }


    public void removeFromMenu(Menu menu) {
        this.menus.remove(menu);
    }

}