package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiProvider;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
    private Collection<Dish> dishes = new ArrayList<>();


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
        this.vat = vat;
        this.name = name;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // VAT: [a-z] [A-Z] [0-9] space
        if (vat == null || vat.isEmpty()) throw new InvalidFieldException("Partita IVA mancante");
        if (!vat.matches("^[a-zA-Z\\d\\040]+$")) throw new InvalidFieldException("Partita IVA non valida");

        // Name: [a-z] [A-Z] space
        if (name == null || name.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiProvider.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Provider)) return false;

        Provider that = (Provider) o;
        return Objects.equals(getVat(), that.getVat());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getVat());
    }


    public String getVat() {
        return vat;
    }


    public void setVat(String vat) {
        this.vat = vat == null || vat.isEmpty() ? null : vat;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null || name.isEmpty() ? null : name;
    }


    public Collection<Dish> getDishes() {
        return dishes;
    }


    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    public void addDishes(Collection<Dish> dishes) {
        this.dishes.addAll(dishes);
    }

    @Override
    public String toString() {
        return "[" + getVat() + "] - " + getName();
    }

}
