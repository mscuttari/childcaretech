package main.java.client.controllers;

import javafx.collections.FXCollections;
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
import main.java.client.layout.MyButtonTableCell;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTripController implements Initializable{

    // Debug
    private static final String TAG = "ShowTripController";

    @FXML private Pane showTripPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Trip> tableTrip;
    @FXML private TableColumn<Trip, String> columnTripTitle;
    @FXML private TableColumn<Trip, Date> columnTripDate;
    @FXML private TableColumn<Trip, Void> columnTripEdit;
    @FXML private TableColumn<Trip, Void> columnTripDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showTripPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showTripPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Trip> trips = connectionManager.getClient().getTrips();
        ObservableList<Trip> tripsData = FXCollections.observableArrayList(trips);

        columnTripTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTripDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnTripEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Trip, Object>() {

            @Override
            public Object call(Trip param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateTrip.fxml"));

                    UpdateTripController updateTripController = new UpdateTripController(param);
                    loader.setController(updateTripController);

                    Pane updateTripPane = loader.load();
                    BorderPane homePane = (BorderPane) showTripPane.getParent();
                    homePane.setCenter(updateTripPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnTripDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {

            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowTrip = FXMLLoader.load(getClass().getResource("/views/showTrip.fxml"));
                BorderPane homePane = (BorderPane) showTripPane.getParent();
                homePane.setCenter(newPaneShowTrip);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tableTrip.setEditable(true);
        tableTrip.setItems(tripsData);
    }

    public void goBack() {
        try {
            Pane tripAdministrationPane = FXMLLoader.load(getClass().getResource("/views/tripAdministration.fxml"));
            BorderPane homePane = (BorderPane) showTripPane.getParent();
            homePane.setCenter(tripAdministrationPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}