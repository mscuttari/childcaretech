package main.java.models;

import main.java.exceptions.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiDish;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "dishes")
@NamedQuery(name = "Dish.search", query = "SELECT d FROM Dish d WHERE d.name = :name")
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
    private Collection<Ingredient> ingredients = new HashSet<>();


    @ManyToMany(mappedBy = "dishes", cascade = {PERSIST, MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Menu> menus = new HashSet<>();


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
    public String getSearchQueryName() {
        return "Dish.search";
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

        // Type
        if (getType() == null)
            throwFieldError("Tipologia mancante");

        // Provider
        if (getProvider() == null)
            throwFieldError("Fornitore mancante");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Pietanza";
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
        if (getProvider() != null)
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


    @Override
    public String toString() {
        return getName();
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
        this.provider = provider;
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