package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiProvider;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "providers", uniqueConstraints = @UniqueConstraint(columnNames = {"vat"}))
public class Provider extends BaseModel {

    private Long id;
    private String vat;
    private String name;

    private Collection<Food> food = new ArrayList<>();


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
        // VAT: [a-z] [A-Z] [0-9]
        if (vat == null || vat.isEmpty()) throw new InvalidFieldException("Partita IVA mancante");
        if (!name.matches("^[a-zA-Z\\d]+$")) throw new InvalidFieldException("Partita IVA non valida");

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

    @Column(name = "vat", nullable = false)
    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "provider")
    public Collection<Food> getFood() {
        return food;
    }

    public void setFood(Collection<Food> food) {
        this.food = food;
    }

}
