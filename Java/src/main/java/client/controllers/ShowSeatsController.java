package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.client.connection.ConnectionManager;
import main.java.models.*;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowSeatsController extends AbstractController implements Initializable{

    @FXML Pane showSeatsPane;
    @FXML ImageView goBackImage;

    @FXML private TableView<Trip> tableTrips;
    @FXML private TableColumn<Trip, String> columnTripsTitle;
    @FXML private TableColumn<Trip, Date> columnTripsDate;

    @FXML private TableView<Pullman> tablePullman;
    @FXML private TableColumn<Pullman, String> columnPullmanId;

    @FXML private TableView<Child> tableChildren;
    @FXML private TableColumn<Child, String> columnChildrenFiscalCode;
    @FXML private TableColumn<Child, String> columnChildrenFirstName;
    @FXML private TableColumn<Child, String> columnChildrenLastName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showSeatsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showSeatsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

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

        columnPullmanId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePullman.setEditable(true);
        tablePullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnChildrenFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableTrips.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Pullman> pullman = (List<Pullman>) tableTrips.getSelectionModel().getSelectedItem().getPullmans();
                ObservableList<Pullman> pullmanData = FXCollections.observableArrayList(pullman);
                tablePullman.setItems(pullmanData);
            }
        });

        tablePullman.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Child> children = (List<Child>) tablePullman.getSelectionModel().getSelectedItem().getChildren();
                ObservableList<Child> childrenData = FXCollections.observableArrayList(children);
                tableChildren.setItems(childrenData);
            }
        });
    }


    /**
     * Go back to the seats assignment page
     */
    private void goBack() {
        setCenterFXML((BorderPane) showSeatsPane.getParent(), "/views/seatsAssignment.fxml");
    }

}