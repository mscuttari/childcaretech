package main.java.client.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class UpdateTripController extends AbstractController implements Initializable {

    private Trip trip;

    @FXML private Pane updateTripPane;
    @FXML private ImageView updateTripImage;
    @FXML private ImageView goBackImage;

    @FXML private TextField tfTripName;
    @FXML private DatePicker dpTripDate;

    @FXML private TableView<GuiChild> tableChildren;
    @FXML private TableColumn<GuiChild, Boolean> columnChildrenSelected;
    @FXML private TableColumn<GuiChild, String> columnChildrenFirstName;
    @FXML private TableColumn<GuiChild, String> columnChildrenLastName;
    @FXML private TableColumn<GuiChild, String> columnChildrenFiscalCode;

    @FXML private TableView<GuiStaff> tableStaff;
    @FXML private TableColumn<GuiStaff, Boolean> columnStaffSelected;
    @FXML private TableColumn<GuiStaff, String> columnStaffFirstName;
    @FXML private TableColumn<GuiStaff, String> columnStaffLastName;
    @FXML private TableColumn<GuiStaff, String> columnStaffFiscalCode;

    @FXML private TextField tfStopName;
    @FXML private TextField tfStopProvince;
    @FXML private TextField tfStopNation;
    @FXML private TextField tfStopNumber;
    @FXML private ListView<Stop> lvStops;
    @FXML private Button buttonAddStop;
    @FXML private Button buttonRemoveSelectedStops;

    @FXML private TextField tfPullmanNumberplate;
    @FXML private TextField tfPullmanSeats;
    @FXML private ListView<Pullman> lvPullman;
    @FXML private Button buttonAddPullman;
    @FXML private Button buttonRemoveSelectedPullman;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Update button
        updateTripImage.setOnMouseEntered(event -> updateTripPane.getScene().setCursor(Cursor.HAND));
        updateTripImage.setOnMouseExited(event -> updateTripPane.getScene().setCursor(Cursor.DEFAULT));
        updateTripImage.setOnMouseClicked(event -> updateTrip());


        // Go back button
        goBackImage.setOnMouseEntered(event -> updateTripPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> updateTripPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());


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
        lvStops.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
     * Set the trip that is going to be modified
     *
     * @param   trip    trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
        loadData();
    }


    /**
     * Add stop to the stops list
     */
    private void addStop() {
        String placeName = tfStopName.getText().trim();
        String placeProvince = tfStopProvince.getText().trim();
        String placeNation = tfStopNation.getText().trim();
        Integer stopNumber = tfStopNumber.getText().isEmpty() ? null : Integer.valueOf(tfStopNumber.getText().trim());

        Place place = new Place(placeName, placeProvince, placeNation);
        Stop stop = new Stop(trip, place, stopNumber);

        // Check data
        try {
            stop.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        lvStops.getItems().add(stop);
        lvStops.getItems().setAll(lvStops.getItems().sorted(new Comparator<Stop>() {
            @Override
            public int compare(Stop o1, Stop o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        }));

        tfStopName.setText("");
        tfStopProvince.setText("");
        tfStopNation.setText("");
        tfStopNumber.setText("");
    }


    /**
     * Remove the selected stops
     */
    private void removeSelectedStops() {
        Stop selectedItem = lvStops.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showErrorDialog("Nessuna fermata selezionata");
        } else {
            List<Stop> stopsList = new ArrayList<>(lvStops.getItems());

            /*
            for(Stop followingItem : stopsList){
                if(selectedItem.getNumber()<followingItem.getNumber()){
                    Stop newStop = new Stop(followingItem.getTrip(), followingItem.getPlaceName(),
                            followingItem.getProvince(), followingItem.getNation(), followingItem.getNumber()-1);
                    lvStops.getItems().remove(followingItem);
                    lvStops.getItems().add(newStop);
                }
            }
            lvStops.getItems().remove(selectedItem);
            lvStops.getSelectionModel().clearSelection();
            */
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
        Pullman selectedPullman = lvPullman.getSelectionModel().getSelectedItem();
        if (selectedPullman == null) {
            showErrorDialog("Nessun pullman selezionato");
        } else {
            lvPullman.getItems().remove(selectedPullman);
            lvPullman.getSelectionModel().clearSelection();
        }
    }


    /**
     * Load the person data into the corresponding fields
     */
    private void loadData() {
        // Title
        tfTripName.setText(trip.getTitle());

        // Date
        dpTripDate.setValue(new java.sql.Date(trip.getDate().getTime()).toLocalDate());

        // Children
        for (GuiChild item : tableChildren.getItems()) {
            if (trip.getChildren().contains(item.getModel()))
                item.setSelected(true);
        }

        // Staff
        for (GuiStaff item : tableStaff.getItems()) {
            if (trip.getStaff().contains(item.getModel()))
                item.setSelected(true);
        }

        // Stops
        lvStops.getItems().setAll(trip.getStops());

        // Transports
        lvPullman.getItems().setAll(trip.getTransports());
    }



    public void updateTrip() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        List<Child> tripChildren = new ArrayList<>(TableUtils.getSelectedItems(tableChildren));
        List<Staff> tripStaff = new ArrayList<>(TableUtils.getSelectedItems(tableStaff));

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
            current.getChildren().clear();
            List<Child> children = new ArrayList<>();
            occupiedSeats += current.getSeats();
            double occupiedSeatsPercentage = occupiedSeats/(double)totalNumberOfSeats;
            children.addAll(TableUtils.getSelectedItems(tableChildren).subList
                    (i, (int)(occupiedSeatsPercentage*totalNumberOfChildren)));
            current.addChildren(children);
            i = (int)(occupiedSeatsPercentage*totalNumberOfChildren);
        }

        // Check data
        try {
            trip.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        List<Stop> tripStops = new ArrayList<>(lvStops.getItems());
        List<Pullman> tripPullman = new ArrayList<>(lvPullman.getItems());

        //Set new data
        trip.getStaff().clear();
        trip.addStaff(tripStaff);
        trip.getChildren().clear();
        trip.addChildren(tripChildren);
        trip.getStops().clear();
        trip.setStops(tripStops);
        trip.getTransports().clear();
        trip.addTransports(tripPullman);

        // Save trip
        connectionManager.getClient().update(trip);
    }


    /**
     * Go back to the main trips page
     */
    private void goBack() {
        setCenterFXML((BorderPane)updateTripPane.getParent(), "/views/showTrip.fxml");
    }

}