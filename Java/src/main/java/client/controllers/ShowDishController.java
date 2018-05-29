package main.java.client.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiDish;
import main.java.client.layout.MyButtonTableCell;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ShowDishController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowDishController";

    @FXML private Pane showDishPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiDish> tableDish;
    @FXML private TableColumn<GuiDish, String> columnDishName;
    @FXML private TableColumn<GuiDish, String> columnDishType;
    @FXML private TableColumn<GuiDish, Integer> columnDishAllergicPeople;
    @FXML private TableColumn<GuiDish, Integer> columnDishIntolerantPeople;
    @FXML private TableColumn<GuiDish, Void> columnDishEdit;
    @FXML private TableColumn<GuiDish, Void> columnDishShowDetails;
    @FXML private TableColumn<GuiDish, Void> columnDishDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showDishPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDishPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Dish> dishes = connectionManager.getClient().getDishes();
        @SuppressWarnings("unchecked") ObservableList<GuiDish> dishesData = TableUtils.getGuiModelsList(dishes);

        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        columnDishAllergicPeople.setCellValueFactory(param -> {
            Dish dish = param.getValue().getModel();
            Collection<Person> allergicPeople = new HashSet<>();

            for (Ingredient ingredient : dish.getIngredients()) {
                allergicPeople.addAll(ingredient.getAllergicPeople());
            }

            return new SimpleIntegerProperty(allergicPeople.size()).asObject();
        });

        columnDishIntolerantPeople.setCellValueFactory(param -> {
            Dish dish = param.getValue().getModel();
            Collection<Person> intolerantPeople = new HashSet<>();

            for (Ingredient ingredient : dish.getIngredients()) {
                intolerantPeople.addAll(ingredient.getIntolerantPeople());
            }

            return new SimpleIntegerProperty(intolerantPeople.size()).asObject();
        });

        Callback<GuiDish, Object> showDetailsCallback = new Callback<GuiDish, Object>() {
            @Override
            public Object call(GuiDish param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showDishDetails.fxml"));

                    Pane showDishDetailsPane = loader.load();
                    ShowDishDetailsController controller = loader.getController();
                    controller.setDish(param.getModel());

                    BorderPane homePane = (BorderPane)showDishPane.getParent();
                    homePane.setCenter(showDishDetailsPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        };

        // Open details page on row double click
        tableDish.setRowFactory( tv -> {
            TableRow<GuiDish> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showDetailsCallback.call(row.getItem());
                }
            });
            return row ;
        });

        columnDishShowDetails.setCellFactory(param -> new MyButtonTableCell<>("Visualizza dettagli", showDetailsCallback));

        columnDishEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<GuiDish, Object>() {

            @Override
            public Object call(GuiDish param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateDish.fxml"));

                    Pane updateDishPane = loader.load();
                    UpdateDishController controller = loader.getController();
                    controller.setDish(param.getModel());

                    BorderPane homePane = (BorderPane)showDishPane.getParent();
                    homePane.setCenter(updateDishPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        columnDishDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {
            if (param1.getModel().isDeletable()) {
                if (showConfirmationDialog("Vuoi davvero eliminare il piatto?\n(la procedura è irreversibile)")) {
                    deleteData(connectionManager, param1);
                }
            } else {
                showErrorDialog("Questo piatto non può essere eliminato");
            }

            return null;
        }));

        tableDish.setEditable(true);
        tableDish.setItems(dishesData);
    }


    /**
     * Delete data and update table data
     */
    private void deleteData(ConnectionManager connectionManager, GuiDish param) {
        connectionManager.getClient().delete(param.getModel());
        List<Dish> newDishes = connectionManager.getClient().getDishes();
        @SuppressWarnings("unchecked") ObservableList<GuiDish> newDishesData = TableUtils.getGuiModelsList(newDishes);

        tableDish.setItems(newDishesData);
    }


    /**
     * Go back to the dish page
     */
    private void goBack() {
        setCenterFXML((BorderPane)showDishPane.getParent(), "/views/dish.fxml");
    }

}

