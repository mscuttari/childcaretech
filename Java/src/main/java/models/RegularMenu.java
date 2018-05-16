package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiRegularMenu;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "RegularMenu")
@DiscriminatorValue("regular")
public class RegularMenu extends Menu {

    // Serialization
    private static final long serialVersionUID = 4801684474319217042L;

    private Collection<AlternativeMenu> alternativeMenus = new ArrayList<>();


    /**
     * Default constructor
     */
    public RegularMenu() {
        this(null, null);
    }


    /**
     * Constructor
     *
     * @param   name      name
     * @param   responsible       responsible
     */
    public RegularMenu(String name, Staff responsible) {
        super(name, responsible);
    }


    /** {@inheritDoc} */
    @Transient
    @Override
    public Class<? extends GuiBaseModel> getGuiClass() { return GuiRegularMenu.class; }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RegularMenu)) return false;

        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @OneToMany(mappedBy = "regularMenu")
    public Collection<AlternativeMenu> getAlternativeMenus() {
        return alternativeMenus;
    }

    public void setAlternativeMenus(Collection<AlternativeMenu> alternativeMenus) {
        this.alternativeMenus = alternativeMenus;
    }
}
