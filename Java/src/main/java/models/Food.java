package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiFood;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "food")
public class Food extends BaseModel {

    // Serialization
    private static final long serialVersionUID = 4169234131175265564L;

    private Long id;
    private String name;
    private FoodType type;

    private Provider provider;
    private Collection<Ingredient> composition = new ArrayList<>();
    private Collection<Menu> menus = new ArrayList<>();


    /**
     * Default constructor
     */
    public Food() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   name    name
     * @param   type    type
     */
    public Food(String name, FoodType type) {
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

        // Food type
        if (type == null) throw new InvalidFieldException("Tipologia mancante");

        // Provider
        if (provider == null) throw new InvalidFieldException("Fornitore mancante");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiFood.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food)) return false;

        Food that = (Food) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getType(), that.getType());
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
        this.name = name == null || name.isEmpty() ? null : name;
    }

    @Column(name = "type")
    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }

    @ManyToOne(cascade = {CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "provider_id", nullable = false)
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "food_composition",
            joinColumns = { @JoinColumn(name = "food_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    public Collection<Ingredient> getComposition() {
        return composition;
    }

    public void setComposition(Collection<Ingredient> composition) {
        this.composition = composition;
    }

    @ManyToMany(mappedBy = "composition")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Collection<Menu> menus) {
        this.menus = menus;
    }

}