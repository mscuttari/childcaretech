package main.java.client.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import main.java.client.gui.GuiTrip;
import main.java.client.layout.MyButtonTableCell;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTripController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowTripController";

    @FXML private Pane showTripPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiTrip> tableTrip;
    @FXML private TableColumn<GuiTrip, String> columnTripTitle;
    @FXML private TableColumn<GuiTrip, String> columnTripDate;
    @FXML private TableColumn<GuiTrip, Integer> columnTripChildren;
    @FXML private TableColumn<GuiTrip, Integer> columnTripStaff;
    @FXML private TableColumn<GuiTrip, Void> columnTripShowDetails;
    @FXML private TableColumn<GuiTrip, Void> columnTripDelete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Back button
        goBackImage.setOnMouseEntered(event -> showTripPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showTripPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Trip> trips = connectionManager.getClient().getTrips();
        ObservableList<GuiTrip> tripsData = TableUtils.getGuiModelsList(trips);

        columnTripTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        columnTripDate.setCellValueFactory(param -> {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleStringProperty(formatter.format(param.getValue().getModel().getDate()));
        });

        columnTripChildren.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getModel().getChildren().size()).asObject());

        columnTripStaff.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getModel().getStaff().size()).asObject());

        Callback<GuiTrip, Object> showDetailsCallback = new Callback<GuiTrip, Object>() {
            @Override
            public Object call(GuiTrip param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showTripDetails.fxml"));

                    Pane showTripDetailsPane = loader.load();
                    ShowTripDetailsController controller = loader.getController();
                    controller.setTrip(param.getModel());

                    BorderPane homePane = (BorderPane)showTripPane.getParent();
                    homePane.setCenter(showTripDetailsPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        };

        columnTripShowDetails.setCellFactory(param -> new MyButtonTableCell<>("Visualizza dettagli", showDetailsCallback));

        // Open details page on row double click
        tableTrip.setRowFactory( tv -> {
            TableRow<GuiTrip> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    showDetailsCallback.call(row.getItem());
                }
            });
            return row ;
        });

        columnTripDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {
            if (showConfirmationDialog("Vuoi davvero eliminare la gita?\n(la procedura è irreversibile)")) {
                connectionManager.getClient().delete(param1.getModel());
                List<Trip> newTrips = connectionManager.getClient().getTrips();
                ObservableList<GuiTrip> newTripsData = TableUtils.getGuiModelsList(newTrips);

                tableTrip.setItems(newTripsData);
            }

            return null;
        }));

        tableTrip.setEditable(true);
        tableTrip.setItems(tripsData);
    }


    /**
     * Go back to the main trips page
     */
    private void goBack() {
        setCenterFXML((BorderPane)showTripPane.getParent(), "/views/tripAdministration.fxml");
    }

}