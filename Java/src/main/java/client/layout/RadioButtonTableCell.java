package main.java.client.layout;

import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.ToggleButton;
import main.java.client.gui.GuiBaseModel;

public class RadioButtonTableCell<S extends GuiBaseModel, T> extends TableCell<S, T> {

    private RadioButton cellButton;
    private SingleSelectionModel<S> model;

    public RadioButtonTableCell(SingleSelectionModel<S> group) {
        this.model = group;
        cellButton = new RadioButton();
        cellButton.setOnAction(e -> updateToggle());
        updateToggle();
        setAlignment(Pos.CENTER);
    }

    private void updateToggle() {
        model.select(cellButton.isSelected()? getIndex() : -1);
    }

    @Override
    protected void updateItem(T t, boolean empty) {
        super.updateItem(t, empty);
        if (empty) {
            setGraphic(null);
        } else {
            cellButton.setSelected(model.isSelected(getIndex()));
            setGraphic(cellButton);
        }
    }

}