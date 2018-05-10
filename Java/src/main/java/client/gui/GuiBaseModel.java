package main.java.client.gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import main.java.models.BaseModel;

public abstract class GuiBaseModel<M extends BaseModel> {

    private M model;
    private BooleanProperty selected;

    public GuiBaseModel(M model) {
        this.model = model;
        this.selected = new SimpleBooleanProperty(false);
    }

    public M getModel() {
        return model;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

}
