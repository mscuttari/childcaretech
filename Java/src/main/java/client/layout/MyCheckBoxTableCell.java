package main.java.client.layout;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MyCheckBoxTableCell<S,T> extends TableCell<S,T> {

    private final CheckBox checkBox;
    private boolean showLabel;
    private ObservableValue<Boolean> booleanProperty;


    public MyCheckBoxTableCell() {
        this(null, null);
    }


    public MyCheckBoxTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty) {
        this(getSelectedProperty, null);
    }


    public MyCheckBoxTableCell(final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty, final StringConverter<T> converter) {
        this.getStyleClass().add("check-box-table-cell");

        this.checkBox = new CheckBox();
        this.checkBox.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                getTableView().getSelectionModel().select(getIndex());
            } else {
                getTableView().getSelectionModel().clearSelection(getIndex());
            }
        });

        // by default the graphic is null until the cell stops being empty
        setGraphic(null);

        setSelectedStateCallback(getSelectedProperty);
        setConverter(converter);
    }


    private ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter") {
                protected void invalidated() {
                    updateShowLabel();
                }
            };


    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }


    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }


    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }


    private ObjectProperty<Callback<Integer, ObservableValue<Boolean>>>
            selectedStateCallback =
            new SimpleObjectProperty<Callback<Integer, ObservableValue<Boolean>>>(
                    this, "selectedStateCallback");


    public final ObjectProperty<Callback<Integer, ObservableValue<Boolean>>> selectedStateCallbackProperty() {
        return selectedStateCallback;
    }


    public final void setSelectedStateCallback(Callback<Integer, ObservableValue<Boolean>> value) {
        selectedStateCallbackProperty().set(value);
    }


    public final Callback<Integer, ObservableValue<Boolean>> getSelectedStateCallback() {
        return selectedStateCallbackProperty().get();
    }


    @SuppressWarnings("unchecked")
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            StringConverter<T> c = getConverter();

            if (showLabel) {
                setText(c.toString(item));
            }
            setGraphic(checkBox);

            if (booleanProperty instanceof BooleanProperty) {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty)booleanProperty);
            }
            ObservableValue<?> obsValue = getSelectedProperty();
            if (obsValue instanceof BooleanProperty) {
                booleanProperty = (ObservableValue<Boolean>) obsValue;
                checkBox.selectedProperty().bindBidirectional((BooleanProperty)booleanProperty);
            }

            checkBox.disableProperty().bind(Bindings.not(
                    getTableView().editableProperty().and(
                            getTableColumn().editableProperty()).and(
                            editableProperty())
            ));
        }
    }


    private void updateShowLabel() {
        this.showLabel = converter != null;
        this.checkBox.setAlignment(showLabel ? Pos.CENTER_LEFT : Pos.CENTER);
    }


    private ObservableValue<?> getSelectedProperty() {
        return getSelectedStateCallback() != null ?
                getSelectedStateCallback().call(getIndex()) :
                getTableColumn().getCellObservableValue(getIndex());
    }

}
