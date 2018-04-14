package main.java.client.layout;

import com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import main.java.LogUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class MyTableViewSelectionModel<S> extends TableView.TableViewSelectionModel<S> {

    private ObservableList<TablePosition> selectedCells;

    /** {@inheritDoc} */
    public MyTableViewSelectionModel(TableView<S> tableView) {
        super(tableView);

        selectedCells = new SimpleListProperty<>();
    }


    /** {@inheritDoc} */
    @Override
    public ObservableList<TablePosition> getSelectedCells() {
        return selectedCells;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isSelected(int row, TableColumn column) {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public void select(int row, TableColumn<S, ?> column) {

    }


    /** {@inheritDoc} */
    @Override
    public void clearAndSelect(int row, TableColumn column) {

    }


    /** {@inheritDoc} */
    @Override
    public void clearSelection(int row, TableColumn column) {

    }


    /** {@inheritDoc} */
    @Override
    public void selectLeftCell() {

    }


    /** {@inheritDoc} */
    @Override
    public void selectRightCell() {

    }


    /** {@inheritDoc} */
    @Override
    public void selectAboveCell() {

    }


    /** {@inheritDoc} */
    @Override
    public void selectBelowCell() {

    }

}
