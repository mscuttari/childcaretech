package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiDish;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "provider_vat", nullable = false)
    private Provider provider;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "dishes_composition",
            joinColumns = { @JoinColumn(name = "dish_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_name", referencedColumnName = "name") }
    )
    private Collection<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany(mappedBy = "dishes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Collection<Menu> menus = new ArrayList<>();


    /**
     * Default constructor
     */
    public Dish() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   name    name
     * @param   type    type
     */
    public Dish(String name, DishType type) {
        this.name = name;
        this.type = type;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (name == null || name.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Dish type
        if (type == null) throw new InvalidFieldException("Tipologia mancante");

        // Provider
        if (provider == null) throw new InvalidFieldException("Fornitore mancante");
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiDish.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish that = (Dish) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getType(), that.getType());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null || name.isEmpty() ? null : name;
    }


    public DishType getType() {
        return type;
    }


    public void setType(DishType type) {
        this.type = type;
    }


    public Provider getProvider() {
        return provider;
    }


    public void setProvider(Provider provider) {
        this.provider = provider;
    }


    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }


    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }


    public void addIngredients(Collection<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }


    public Collection<Menu> getMenus() {
        return menus;
    }


    public void addToMenu(Menu menu) {
        this.menus.add(menu);
    }


    public void addToMenus(Collection<Menu> menus) {
        this.menus.addAll(menus);
    }

    @Override
    public String toString(){
        return name;
    }

}