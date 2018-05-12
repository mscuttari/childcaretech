package main.java.client.layout;

import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import main.java.client.gui.GuiBaseModel;

public class RadioSelectionModel<S extends GuiBaseModel> extends SingleSelectionModel<S> {

    private ListProperty<S> listProperty;

    public RadioSelectionModel(Property<ObservableList<S>> items) {
        //listProperty = BugPropertyAdapters.listProperty(items);
        listProperty = new SimpleListProperty<>();
        listProperty.bindBidirectional(items);
        ListChangeListener<S> itemsContentObserver = this::itemsChanged;
        listProperty.addListener(itemsContentObserver);
    }

    private void itemsChanged(ListChangeListener.Change<? extends S> c) {
        // TODO need to implement update on modificatins to the underlying list
    }

    @Override
    protected S getModelItem(int index) {
        if (index < 0 || index >= getItemCount()) return null;
        return listProperty.get(index);
    }

    @Override
    protected int getItemCount() {
        return listProperty.getSize();
    }

    @Override
    public void select(int index) {
        // Avoid deselection
        if (index == getSelectedIndex())
            return;

        super.select(index);

        // Deselect all
        for (S item : listProperty)
            item.setSelected(false);

        int itemCount = getItemCount();
        if (itemCount == 0 || index < 0 || index >= itemCount) return;

        // Select only the specified row
        listProperty.get(index).setSelected(true);
    }
}