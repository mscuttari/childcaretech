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


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiBaseModel))
            return false;

        return getModel().equals(((GuiBaseModel) obj).getModel());
    }


    @Override
    public int hashCode() {
        return getModel().hashCode();
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
