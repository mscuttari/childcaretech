package main.java.client.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import main.java.models.BaseModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TableUtils {

    private TableUtils() {

    }


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

}
