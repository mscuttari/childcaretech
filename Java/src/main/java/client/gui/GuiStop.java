package main.java.client.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.models.Stop;

public class GuiStop extends GuiBaseModel<Stop> {

    private StringProperty placeName;
    private StringProperty province;
    private StringProperty nation;

    public GuiStop(Stop model) {
        super(model);

        this.placeName = new SimpleStringProperty(model.getPlaceName());
        this.province = new SimpleStringProperty(model.getProvince());
        this.nation = new SimpleStringProperty(model.getNation());
    }

    public StringProperty placeNameProperty() {
        return placeName;
    }

    public StringProperty provinceProperty() {
        return province;
    }

    public StringProperty nationProperty() {
        return nation;
    }
    
}
