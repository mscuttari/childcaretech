package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class AddTripController implements Initializable {

    // Debug
    private static final String TAG = "AddTripController";

    //Create trip
    Trip trip = new Trip();

    @FXML private ImageView addTripImage;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Save button cursor
        addTripImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        addTripImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addTripImage.setOnMouseClicked(event -> savePrimaryTripData());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

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

        // Stops tab
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
                for(Stop followingItem : lvStops.getItems().subList(selectedItem.getNumber()-1, lvStops.getItems().size())){
                    followingItem.setNumber(followingItem.getNumber()-1);
                }
                lvStops.getItems().remove(selectedItem);
            }
            lvStops.getSelectionModel().clearSelection();
        }
    }

    /**
     * Add pullman to the pullman list
     */
    private void addPullman() {
        String pullmanNumberplate = tfPullmanNumberplate.getText().trim();
        Integer pullmanSeats = Integer.valueOf(tfPullmanSeats.getText().trim());
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

    /**
     * Save trip in the database
     */
    private void savePrimaryTripData() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        String title = tfTripName.getText().trim();
        Date date = dpTripDate.getValue() == null ? null : Date.from(dpTripDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        trip.setTitle(title);
        trip.setDate(date);

        trip.setChildrenEnrollments(TableUtils.getSelectedItems(tableChildren));
        trip.setStaffEnrollments(TableUtils.getSelectedItems(tableStaff));

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

        // Save trip
        connectionManager.getClient().create(trip);

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

}