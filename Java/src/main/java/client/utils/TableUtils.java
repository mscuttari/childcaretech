package main.java.client.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import main.java.client.gui.GuiBaseModel;
import main.java.models.BaseModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    private TableUtils() {

    }


    /**
     * Convert GUI models to models
     *
     * @param   data    GUI models list
     * @return  models list
     */
    public static <M extends BaseModel, T extends GuiBaseModel<M>> ObservableList<M> getModelsList(List<T> data) {
        ObservableList<M> result = FXCollections.observableArrayList();

        for (T item : data) {
            result.add(item.getModel());
        }

        return result;
    }


    /**
     * Convert models to GUI models
     *
     * @param   data    models list
     * @return  GUI models list
     */
    public static <M extends BaseModel, T extends GuiBaseModel<M>> ObservableList<T> getGuiModelsList(List<M> data) {
        ObservableList<T> result = FXCollections.observableArrayList();

        for (M item : data) {
            try {
                Constructor<?> constructor = item.getGuiClass().getConstructor(item.getClass());
                //noinspection unchecked
                result.add((T)constructor.newInstance(item));

            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Get models of the selected items of a TableView
     *
     * @param   tableView   TableView
     * @return  list of the selected items
     */
    public static <M extends BaseModel, T extends GuiBaseModel<M>> List<M> getSelectedItems(TableView<T> tableView) {
        List<M> result = new ArrayList<>();

        for (T item : tableView.getItems()) {
            if (item.isSelected()) {
                result.add(item.getModel());
            }
        }

        return result;
    }


    /**
     * Get model of the first selected item of a TableView
     * Useful for tables where the selected state is represented by a radio button
     *
     * @param   tableView   TableView
     * @return  first selected item
     */
    public static <M extends BaseModel, T extends GuiBaseModel<M>> M getFirstSelectedItem(TableView<T> tableView) {
        List<M> selectedItems = getSelectedItems(tableView);
        return selectedItems == null || selectedItems.size() == 0 ? null : selectedItems.get(0);
    }

}
