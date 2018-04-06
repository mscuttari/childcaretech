package main.java.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "menus", uniqueConstraints = @UniqueConstraint(columnNames = {"responsible_id"}))
public class Menu extends BaseModel {

    private Long id;
    private String name;
    private String type;
    private Person responsible;
    private Collection<Food> composition = new ArrayList<>();

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

    @Column(name = "type", nullable = false)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "responsible_id", referencedColumnName = "id")
    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    @ManyToMany
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
