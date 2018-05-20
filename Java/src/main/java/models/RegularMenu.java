package main.java.models;

import main.java.client.gui.GuiBaseModel;
import main.java.client.gui.GuiRegularMenu;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "RegularMenu")
@Table(name = "regular_menus")
@DiscriminatorValue("regular")
public class RegularMenu extends Menu {

    @Transient
    private static final long serialVersionUID = 4801684474319217042L;

    @Column(name = "day_of_the_week", nullable = false)
    private DayOfTheWeek dayOfTheWeek;


    @OneToMany(mappedBy = "regularMenu")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<AlternativeMenu> alternativeMenus = new ArrayList<>();


    /**
     * Default constructor
     */
    public RegularMenu() {
        this(null, null, null);
    }


    /**
     * Constructor
     *
     * @param   name      name
     * @param   responsible       responsible
     * @param   dayOfTheWeek        day of the week
     */
    public RegularMenu(String name, Staff responsible, DayOfTheWeek dayOfTheWeek) {

        super(name, responsible);
        this.dayOfTheWeek = dayOfTheWeek;
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


    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }


    public void setDayOfTheWeek(DayOfTheWeek dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }


    public Collection<AlternativeMenu> getAlternativeMenus() {
        return alternativeMenus;
    }


    public void addAlternativeMenu(AlternativeMenu alternativeMenu) {
        this.alternativeMenus.add(alternativeMenu);
    }


    public void addAlternativeMenus(Collection<AlternativeMenu> alternativeMenus) {
        this.alternativeMenus.addAll(alternativeMenus);
    }

}
