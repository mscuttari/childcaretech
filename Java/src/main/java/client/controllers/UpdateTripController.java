package main.java.client.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.java.LogUtils;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.layout.RadioButtonTableCell;
import main.java.client.layout.RadioSelectionModel;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class UpdateTripController implements Initializable {

    // Debug
    private static final String TAG = "UpdateTripController";

    private Trip trip;

    @FXML private Pane updateTripPane;
    @FXML private ImageView updateTripImage;
    @FXML private ImageView deleteTripImage;

    @FXML private TabPane tabPane;

    @FXML private Tab tabData;
    @FXML private TextField tfTripName;
    @FXML private DatePicker dpTripDate;

    @FXML private Tab tabChildren;
    @FXML private TableView<GuiChild> tableChildren;
    @FXML private TableColumn<GuiChild, Boolean> columnChildrenSelected;
    @FXML private TableColumn<GuiChild, String> columnChildrenFirstName;
    @FXML private TableColumn<GuiChild, String> columnChildrenLastName;
    @FXML private TableColumn<GuiChild, String> columnChildrenFiscalCode;

    @FXML private Tab tabStaff;
    @FXML private TableView<GuiStaff> tableStaff;
    @FXML private TableColumn<GuiStaff, Boolean> columnStaffSelected;
    @FXML private TableColumn<GuiStaff, String> columnStaffFirstName;
    @FXML private TableColumn<GuiStaff, String> columnStaffLastName;
    @FXML private TableColumn<GuiStaff, String> columnStaffFiscalCode;

    @FXML private Tab tabStops;
    @FXML private TextField tfStopName;
    @FXML private TextField tfStopProvince;
    @FXML private TextField tfStopNation;
    @FXML private ListView<Stop> lvStops;
    @FXML private Button buttonAddStop;
    @FXML private Button buttonRemoveSelectedStops;
    @FXML private Label labelErrorStops;

    @FXML private Tab tabPullman;
    @FXML private TextField tfPullmanNumberplate;
    @FXML private TextField tfPullmanSeats;
    @FXML private ListView<Pullman> lvPullman;
    @FXML private Button buttonAddPullman;
    @FXML private Button buttonRemoveSelectedPullman;
    @FXML private Label labelErrorPullman;

    public UpdateTripController(Trip trip){
        this.trip = trip;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // update trip image
        updateTripImage.setOnMouseClicked(event -> updateTrip());

        //delete trip image
        deleteTripImage.setOnMouseClicked(event -> deleteTrip());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Data tab
        tfTripName.setText(trip.getTitle());
        if (trip.getDate() != null) {
            dpTripDate.setValue(new java.sql.Date(trip.getDate().getTime()).toLocalDate());
        }

        // Children tab
        List<Child> children = connectionManager.getClient().getChildren();
        ObservableList<GuiChild> childrenData = TableUtils.getGuiModelsList(children);

        columnChildrenSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnChildrenSelected));
        columnChildrenSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnChildrenFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableChildren.setEditable(true);
        tableChildren.setItems(childrenData);

        List<Child> tripChildren = (List<Child>) trip.getChildrenEnrollments();
        for (GuiChild item : tableChildren.getItems()){
            if(tripChildren.contains(item.getModel()))
                item.setSelected(true);
        }

        // Staff tab
        List<Staff> staff = connectionManager.getClient().getStaff();
        ObservableList<GuiStaff> staffData = TableUtils.getGuiModelsList(staff);

        columnStaffSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnStaffSelected));
        columnStaffSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnStaffFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnStaffLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnStaffFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableStaff.setEditable(true);
        tableStaff.setItems(staffData);

        List<Staff> tripStaff = (List<Staff>) trip.getStaffEnrollments();
        for (GuiStaff item : tableStaff.getItems()){
            if(tripStaff.contains(item.getModel()))
                item.setSelected(true);
        }

        // Stops tab
        lvStops.getItems().setAll(trip.getStops());
        lvStops.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonRemoveSelectedStops.setOnAction(event -> removeSelectedStops());
        buttonAddStop.setOnAction(event -> addStop());

        // Add stop on enter key press
        EventHandler<KeyEvent> StopKeyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addStop();
        };

        tfStopName.setOnKeyPressed(StopKeyPressEvent);
        tfStopProvince.setOnKeyPressed(StopKeyPressEvent);
        tfStopNation.setOnKeyPressed(StopKeyPressEvent);


        // Pullman tab
        lvPullman.getItems().setAll(trip.getTransports());
        lvPullman.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonRemoveSelectedPullman.setOnAction(event -> removeSelectedPullman());
        buttonAddPullman.setOnAction(event -> addPullman());

        // Add pullman on enter key press
        EventHandler<KeyEvent> PullmanKeyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addPullman();
        };

        tfPullmanNumberplate.setOnKeyPressed(PullmanKeyPressEvent);
        tfPullmanSeats.setOnKeyPressed(PullmanKeyPressEvent);
    }

    /**
     * Add stop to the stops list
     */
    private void addStop() {
        String stopName = tfStopName.getText().trim();
        String stopProvince = tfStopProvince.getText().trim();
        String stopNation = tfStopNation.getText().trim();
        Stop stop = new Stop(trip, stopName, stopProvince, stopNation, lvStops.getItems().size()+1);

        // Check data
        try {
            stop.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        lvStops.getItems().add(stop);
        tfStopName.setText("");
        tfStopProvince.setText("");
        tfStopNation.setText("");
    }


    /**
     * Remove the selected stops
     */
    private void removeSelectedStops() {
        List<Stop> selectedStops = lvStops.getSelectionModel().getSelectedItems();

        if (selectedStops.isEmpty()) {
            showErrorDialog("Nessuna fermata selezionata");
        } else {
            for(Stop selectedItem : selectedStops){
                for(Stop followingItem : lvStops.getItems().subList(selectedItem.getNumber(), lvStops.getItems().size())){
                    followingItem.setNumber(followingItem.getNumber()-1);
                }
            }
            lvStops.getItems().removeAll(selectedStops);
            lvStops.getSelectionModel().clearSelection();
        }
    }

    /**
     * Add pullman to the pullman list
     */
    private void addPullman() {
        String pullmanNumberplate = tfPullmanNumberplate.getText().trim();
        Integer pullmanSeats = tfPullmanSeats.getText().isEmpty() ? null : Integer.valueOf(tfPullmanSeats.getText().trim());
        Pullman pullman = new Pullman(trip, pullmanNumberplate, pullmanSeats);

        // Check data
        try {
            pullman.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        lvPullman.getItems().add(pullman);
        tfPullmanNumberplate.setText("");
        tfPullmanSeats.setText("");
    }


    /**
     * Remove selected pullman from the pullman list
     */
    private void removeSelectedPullman() {
        List<Pullman> selectedPullman = lvPullman.getSelectionModel().getSelectedItems();

        if (selectedPullman.isEmpty()) {
            showErrorDialog("Nessun pullman selezionato");
        } else {
            lvPullman.getItems().removeAll(selectedPullman);
            lvPullman.getSelectionModel().clearSelection();
        }
    }

    public void updateTrip() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        trip.setTitle(tfTripName.getText().trim());
        trip.setDate(dpTripDate.getValue() == null ? null : Date.from(dpTripDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        trip.setChildrenEnrollments(TableUtils.getSelectedItems(tableChildren));
        trip.setStaffEnrollments(TableUtils.getSelectedItems(tableStaff));

        int totalNumberOfSeats = 0;
        for(Pullman current : lvPullman.getItems()){
            totalNumberOfSeats += current.getSeats();
        }

        if(totalNumberOfSeats < TableUtils.getSelectedItems(tableChildren).size()){
            showErrorDialog("Posti insufficienti per tutti i bambini");
            return;
        }


        int i=0;
        int occupiedSeats = 0;
        double totalNumberOfChildren = TableUtils.getSelectedItems(tableChildren).size();
        for(Pullman current : lvPullman.getItems()){
            List<Child> children = new ArrayList<>();
            occupiedSeats += current.getSeats();
            double occupiedSeatsPercentage = occupiedSeats/(double)totalNumberOfSeats;
            children.addAll(TableUtils.getSelectedItems(tableChildren).subList
                    (i, (int)(occupiedSeatsPercentage*totalNumberOfChildren)));
            current.setChildrenAssignments(children);
            i = (int)(occupiedSeatsPercentage*totalNumberOfChildren);
        }

        // Check data
        try {
            trip.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        List<Stop> stops = new ArrayList<>();
        stops.addAll(lvStops.getItems());
        trip.setStops(stops);

        List<Pullman> pullman = new ArrayList<>();
        pullman.addAll(lvPullman.getItems());
        trip.setTransports(pullman);

        // Update trip
        connectionManager.getClient().update(trip);
    }


    /**
     * Show error dialog
     *
     * @param   message     error message
     */
    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    public void deleteTrip() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //delete
        connectionManager.getClient().delete(trip);

        try {
            TableView tableTrips = FXMLLoader.load(getClass().getResource("/views/showTrip.fxml"));
            BorderPane homePane = (BorderPane) updateTripPane.getParent();
            homePane.setCenter(tableTrips);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}