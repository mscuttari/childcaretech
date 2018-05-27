package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiProvider;
import main.java.server.utils.HibernateUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "providers", uniqueConstraints = @UniqueConstraint(columnNames = {"vat"}))
public class Provider extends BaseModel {

    @Transient
    private static final long serialVersionUID = -2813171935508565148L;


    @Id
    @Column(name = "vat")
    private String vat;


    @Column(name = "name", nullable = false)
    private String name;


    @OneToMany(mappedBy = "provider")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Dish> dishes = new HashSet<>();


    /**
     * Default constructor
     */
    public Provider() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   vat     VAT
     * @param   name    name
     */
    public Provider(String vat, String name) {
        setVat(vat);
        setName(name);
    }


    /** {@inheritDoc} */
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // VAT: [a-z] [A-Z] [0-9] space
        if (getVat() == null)
            throwFieldError("Partita IVA mancante");

        if (!getVat().matches("^[a-zA-Z\\d\\040]+$"))
            throwFieldError("Partita IVA non valida");

        // Name: [a-z] [A-Z] à è é ì ò ù ' " space
        if (getName() == null)
            throwFieldError("Nome mancante");

        if (!getName().matches("^[a-zA-Zàèéìòù'\"\\040]+$"))
            throwFieldError("Nome non valido");
    }


    /** {@inheritDoc} */
    @Override
    public String getModelName() {
        return "Fornitore";
    }


    /** {@inheritDoc} */
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiProvider.class;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isDeletable() {
        return getDishes().isEmpty();
    }


    /** {@inheritDoc} */
    @Override
    public void preDelete() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;

        Provider that = (Provider) o;
        return Objects.equals(getVat(), that.getVat()) &&
                Objects.equals(getName(), that.getName());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getVat());
    }


    public String getVat() {
        return this.vat;
    }


    public void setVat(String vat) {
        this.vat = trimString(vat);
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


    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    public void addDishes(Collection<Dish> dishes) {
        this.dishes.addAll(dishes);
    }


    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
    }


    public void setDishes(Collection<Dish> dishes) {
        this.dishes.clear();
        addDishes(dishes);
    }

}
