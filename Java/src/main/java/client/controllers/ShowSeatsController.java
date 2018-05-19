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
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowSeatsController implements Initializable{

    // Debug
    private static final String TAG = "ShowSeatsController";

    @FXML Pane showSeatsPane;
    @FXML ImageView goBackImage;

    @FXML private TableView<Trip> tableTrips;
    @FXML private TableColumn<Trip, String> columnTripsTitle;
    @FXML private TableColumn<Trip, Date> columnTripsDate;

    @FXML private TableView<Pullman> tablePullman;
    @FXML private TableColumn<Pullman, String> columnPullmanNumberplate;

    @FXML private TableView<Child> tableChildren;
    @FXML private TableColumn<Child, String> columnChildrenFiscalCode;
    @FXML private TableColumn<Child, String> columnChildrenFirstName;
    @FXML private TableColumn<Child, String> columnChildrenLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showSeatsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showSeatsPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table trip
        List<Trip> trips = connectionManager.getClient().getTrips();
        ObservableList<Trip> tripsData = FXCollections.observableArrayList(trips);

        columnTripsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTripsDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableTrips.setEditable(true);
        tableTrips.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableTrips.setItems(tripsData);

        columnPullmanNumberplate.setCellValueFactory(new PropertyValueFactory<>("numberplate"));
        tablePullman.setEditable(true);
        tablePullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnChildrenFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableTrips.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Pullman> pullman = (List<Pullman>) tableTrips.getSelectionModel().getSelectedItem().getTransports();
                ObservableList<Pullman> pullmanData = FXCollections.observableArrayList(pullman);
                tablePullman.setItems(pullmanData);
            }
        });
/*
        tablePullman.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Child> children = (List<Child>) tablePullman.getSelectionModel().getSelectedItem().getChildrenAssignments();
                ObservableList<Child> childrenData = FXCollections.observableArrayList(children);
                tableChildren.setItems(childrenData);
            }
        });*/
    }

    public void goBack() {
        try {
            Pane seatsAssignmentPane = FXMLLoader.load(getClass().getResource("/views/seatsAssignment.fxml"));
            BorderPane homePane = (BorderPane) showSeatsPane.getParent();
            homePane.setCenter(seatsAssignmentPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }
}