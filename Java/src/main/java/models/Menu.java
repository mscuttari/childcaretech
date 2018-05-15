package main.java.models;

import main.java.client.InvalidFieldException;
import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiMenu;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "menus", uniqueConstraints = @UniqueConstraint(columnNames = {"responsible_id"}))
public class Menu extends BaseModel {

    // Serialization
    private static final long serialVersionUID = -8284040804525605098L;

    private Long id;
    private String name;
    private String type;
    private Staff responsible;

    private Collection<Food> composition = new ArrayList<>();


    /**
     * Default constructor
     */
    public Menu() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name            name
     * @param   type            type
     * @param   responsible     responsible (staff person)
     */
    public Menu(String name, String type, Staff responsible) {
        this.name = name;
        this.type = type;
        this.responsible = responsible;
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public void checkDataValidity() throws InvalidFieldException {
        // Name: [a-z] [A-Z] space
        if (name == null || name.isEmpty()) throw new InvalidFieldException("Nome mancante");
        if (!name.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Nome non valido");

        // Type: [a-z] [A-Z] space
        if (type == null || type.isEmpty()) throw new InvalidFieldException("Tipologia mancante");
        if (!type.matches("^[a-zA-Z\\040]+$")) throw new InvalidFieldException("Tipologia non valida");

        // Responsible
        if (responsible == null) throw new InvalidFieldException("Responsabile mancante");
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() {
        return GuiMenu.class;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;

        Menu that = (Menu) o;
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
        this.name = name == null || name.isEmpty() ? null : name;
    }

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null || type.isEmpty() ? null : type;
    }

    @ManyToOne
    @JoinColumn(name = "responsible_id", referencedColumnName = "id")
    public Staff getResponsible() {
        return responsible;
    }

    public void setResponsible(Staff responsible) {
        this.responsible = responsible;
    }

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "menus_composition",
            joinColumns = { @JoinColumn(name = "menu_id") },
            inverseJoinColumns = { @JoinColumn(name = "food_id") }
    )
    public Collection<Food> getComposition() {
        return composition;
    }

    public void setComposition(Collection<Food> composition) {
        this.composition = composition;
    }

}
