package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class AddTripController extends AbstractController implements Initializable {

    private Trip trip = new Trip();
    private int stopCounter = 1;

    @FXML private Pane addTripPane;
    @FXML private ImageView tripImageView;
    @FXML private ImageView goBackImage;

    @FXML private TabPane tabPane;

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
    @FXML private ListView<GuiStop> lvStops;
    @FXML private Button buttonAddStop;
    @FXML private Button buttonRemoveSelectedStops;

    @FXML private TextField tfPullmanId;
    @FXML private TextField tfPullmanSeats;
    @FXML private ComboBox<SeatsAssignmentType> cbSeatsAssignment;
    @FXML private ListView<GuiPullman> lvPullman;
    @FXML private Button buttonAddPullman;
    @FXML private Button buttonRemoveSelectedPullman;


    @FXML private Tab tabSeatsAssignment;

    @FXML private TableView<GuiPullman> tableSAPullman;
    @FXML private TableColumn<GuiPullman, String> columnSAPullmanCode;
    @FXML private TableColumn<GuiPullman, Integer> columnSAPullmanSeats;

    @FXML private TableView<GuiChild> tableSAChildren;
    @FXML private TableColumn<GuiChild, Boolean> columnSAChildrenSelected;
    @FXML private TableColumn<GuiChild, String> columnSAChildrenFirstName;
    @FXML private TableColumn<GuiChild, String> columnSAChildrenLastName;
    @FXML private TableColumn<GuiChild, String> columnSAChildrenFiscalCode;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Save button
        tripImageView.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        tripImageView.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));


        // Go back button
        goBackImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));
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

        // Force the number field to be numeric only
        tfStopNumber.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getText();

            if (text.matches("[0-9]*"))
                return change;

            return null;
        }));


        // Add stop on enter key press
        EventHandler<KeyEvent> stopKeyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addStop();
        };

        tfStopName.setOnKeyPressed(stopKeyPressEvent);
        tfStopProvince.setOnKeyPressed(stopKeyPressEvent);
        tfStopNation.setOnKeyPressed(stopKeyPressEvent);
        tfStopNumber.setOnKeyPressed(stopKeyPressEvent);


        // Pullman tab
        lvPullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        buttonRemoveSelectedPullman.setOnAction(event -> removeSelectedPullman());
        buttonAddPullman.setOnAction(event -> addPullman());


        // Add pullman on enter key press
        EventHandler<KeyEvent> PullmanKeyPressEvent = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addPullman();
        };

        tfPullmanId.setOnKeyPressed(PullmanKeyPressEvent);
        tfPullmanSeats.setOnKeyPressed(PullmanKeyPressEvent);

        // Seats assignment type
        cbSeatsAssignment.getItems().addAll(SeatsAssignmentType.values());

        cbSeatsAssignment.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) return;
            tabPane.getTabs().remove(tabSeatsAssignment);
            tfPullmanId.setDisable(false);
            tfPullmanSeats.setDisable(false);
            lvPullman.setDisable(false);
            buttonAddPullman.setDisable(false);
            buttonRemoveSelectedPullman.setDisable(false);

            switch (newValue) {
                case AUTOMATIC:
                    tripImageView.setImage(new Image("/images/save-data.png"));
                    tripImageView.setOnMouseClicked(event -> saveTrip());
                    break;

                case MANUAL:
                    tabPane.getTabs().add(tabSeatsAssignment);
                    tripImageView.setImage(new Image("/images/seat.png"));
                    tripImageView.setOnMouseClicked(event -> showSeatsAssignmentTab());
                    break;

                case UNNECESSARY:
                    tripImageView.setImage(new Image("/images/save-data.png"));
                    tripImageView.setOnMouseClicked(event -> saveTrip());
                    tfPullmanId.setDisable(true);
                    tfPullmanSeats.setDisable(true);
                    lvPullman.setDisable(true);
                    buttonAddPullman.setDisable(true);
                    buttonRemoveSelectedPullman.setDisable(true);
                    break;
            }
        });

        cbSeatsAssignment.getSelectionModel().selectFirst();
        tabPane.getTabs().remove(tabSeatsAssignment);
    }


    /**
     * Add stop to the stops list
     */
    private void addStop() {
        // Get data
        String placeName = tfStopName.getText().trim();
        String placeProvince = tfStopProvince.getText().trim();
        String placeNation = tfStopNation.getText().trim();
        Integer stopNumber = tfStopNumber.getText().isEmpty() ? stopCounter : Integer.valueOf(tfStopNumber.getText().trim());

        // Limit the max stop number to the next available slot
        if (stopNumber > stopCounter + 1)
            stopNumber = stopCounter;

        // Create stop
        Place place = new Place(placeName, placeProvince, placeNation);
        Stop stop = new Stop(trip, place, stopNumber);

        // Check data validity
        try {
            stop.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Check if the number already exists
        boolean numberAlreadyExists = false;

        for (GuiStop guiStop : lvStops.getItems()) {
            if (guiStop.getModel().getNumber().equals(stop.getNumber())) {
                numberAlreadyExists = true;
                break;
            }
        }

        if (numberAlreadyExists) {
            for (GuiStop guiStop : lvStops.getItems()) {
                if (guiStop.getModel().getNumber() >= stop.getNumber())
                    guiStop.numberProperty().setValue(guiStop.numberProperty().getValue() + 1);
            }
        }

        // Add stop
        GuiStop guiStop = new GuiStop(stop);
        lvStops.getItems().add(guiStop);
        lvStops.getItems().setAll(lvStops.getItems().sorted(Comparator.comparing(o -> o.getModel().getNumber())));

        // Reset fields
        tfStopName.setText("");
        tfStopProvince.setText("");
        tfStopNation.setText("");
        tfStopNumber.setText("");

        // Increment stop counter
        stopCounter = getHighestStopNumber() + 1;

        // Assign the focus to the place name textfield
        tfStopName.requestFocus();
    }


    /**
     * Remove the selected stop
     */
    private void removeSelectedStops() {
        GuiStop selectedItem = lvStops.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showErrorDialog("Nessuna fermata selezionata");

        } else {
            lvStops.getItems().remove(selectedItem);
            lvStops.getSelectionModel().clearSelection();

            // Adjust stops numbers
            List<GuiStop> guiStops = new ArrayList<>(lvStops.getItems());

            for (GuiStop followingItem : guiStops) {
                if (selectedItem.numberProperty().getValue() < followingItem.numberProperty().getValue()) {
                    followingItem.numberProperty().setValue(followingItem.numberProperty().getValue() - 1);
                }
            }

            stopCounter = getHighestStopNumber() + 1;
        }
    }


    /**
     * Get the highest stop number in the ListView
     *
     * @return  stop number
     */
    private int getHighestStopNumber() {
        int highestStopNumber = 1;

        for (GuiStop guiStop1 : lvStops.getItems()) {
            if (guiStop1.numberProperty().getValue() > highestStopNumber)
                highestStopNumber = guiStop1.numberProperty().getValue();
        }

        return highestStopNumber;
    }


    /**
     * Add pullman to the pullman list
     */
    private void addPullman() {
        // Create pullman
        String pullmanId = tfPullmanId.getText();
        Integer pullmanSeats = tfPullmanSeats.getText().isEmpty() ? null : Integer.valueOf(tfPullmanSeats.getText().trim());
        Pullman pullman = new Pullman(trip, pullmanId, pullmanSeats);

        // Check data
        try {
            pullman.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Add pullman
        GuiPullman guiPullman = new GuiPullman(pullman);
        lvPullman.getItems().add(guiPullman);

        // Reset fields
        tfPullmanId.setText("");
        tfPullmanSeats.setText("");
    }


    /**
     * Remove selected pullman from the pullman list
     */
    private void removeSelectedPullman() {
        GuiPullman selectedPullman = lvPullman.getSelectionModel().getSelectedItem();

        if (selectedPullman == null) {
            showErrorDialog("Nessun pullman selezionato");
        } else {
            lvPullman.getItems().remove(selectedPullman);
            lvPullman.getSelectionModel().clearSelection();
        }
    }


    /**
     * Switch to seats assignments tab
     */
    private void showSeatsAssignmentTab() {

        if (!preSaveTrip()) { return; }

        // Disable all tabs except the seats assignment one
        for(Tab current : tabPane.getTabs()){
            current.setDisable(true);
        }

        tabSeatsAssignment.setDisable(false);
        tabPane.getSelectionModel().select(tabSeatsAssignment);


        // Show the save icon
        tripImageView.setImage(new Image("/images/save-data.png"));
        tripImageView.setOnMouseClicked(event -> saveTrip());


        // Pullmans list
        ObservableList<GuiPullman> pullmans = FXCollections.observableArrayList(lvPullman.getItems());

        columnSAPullmanCode.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnSAPullmanSeats.setCellValueFactory(new PropertyValueFactory<>("seats"));
        tableSAPullman.setEditable(true);
        tableSAPullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableSAPullman.setItems(pullmans);
        tableSAPullman.getSelectionModel().selectFirst();


        // Children tab
        List<Child> children = new ArrayList<>(trip.getChildren());
        ObservableList<GuiChild> childrenData = TableUtils.getGuiModelsList(children);

        columnSAChildrenSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnSAChildrenSelected));
        columnSAChildrenSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnSAChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnSAChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnSAChildrenFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableSAChildren.setEditable(true);
        tableSAChildren.setItems(childrenData);

        tableSAPullman.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            oldSelection.getModel().setChildren(TableUtils.getSelectedItems(tableSAChildren));
            tableSAChildren.setItems(TableUtils.getGuiModelsList(new ArrayList<>(trip.getChildren())));
            for (GuiChild current : tableSAChildren.getItems()){
                if (newSelection.getModel().getChildren().contains(current.getModel())){
                    current.setSelected(true);
                }
            }
        });
    }


    /**
     * Check that each field is filled in correctly
     *
     * return true if the data are valid
     * return false otherwise
     */
    private boolean preSaveTrip() {

        // Data
        String title = tfTripName.getText().trim();
        Date date = dpTripDate.getValue() == null ? null : Date.from(dpTripDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        trip.setTitle(title);
        trip.setDate(date);
        trip.setSeatsAssignmentType(cbSeatsAssignment.getValue());
        trip.setPullmans(TableUtils.getModelsList(lvPullman.getItems()));
        trip.setChildren(TableUtils.getSelectedItems(tableChildren));
        trip.setStaff(TableUtils.getSelectedItems(tableStaff));

        // Check data
        try {
            trip.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return false;
        }

        return true;
    }


    /**
     * Save trip in the database
     */
    private void saveTrip() {
        // Basic Data
        String title = tfTripName.getText();
        Date date = dpTripDate.getValue() == null ? null : Date.from(dpTripDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        trip.setTitle(title);                                               // Title
        trip.setDate(date);                                                 // Date
        trip.setSeatsAssignmentType(cbSeatsAssignment.getValue());          // Seats assignment method

        trip.setChildren(TableUtils.getSelectedItems(tableChildren));       // Children
        trip.setStaff(TableUtils.getSelectedItems(tableStaff));             // Staff
        trip.setStops(TableUtils.getModelsList(lvStops.getItems()));        // Stops
        trip.setPullmans(TableUtils.getModelsList(lvPullman.getItems()));   // Pullmans

        // Check data
        try {
            trip.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Seats assignment method
        switch (trip.getSeatsAssignmentType()) {
            case AUTOMATIC:
                ArrayList<Child> children = new ArrayList<>(trip.getChildren());

                for (Pullman pullman : trip.getPullmans()){
                    if (children.isEmpty()) break;      // All children have been assigned

                    if (pullman.getSeats() <= children.size()) {
                        pullman.setChildren(children.subList(0, pullman.getSeats()));
                        children.removeAll(pullman.getChildren());
                    } else {
                        pullman.setChildren(children);
                        break;
                    }
                }

                break;

            case MANUAL:
                tableSAPullman.getSelectionModel().getSelectedItem().getModel().setChildren(TableUtils.getSelectedItems(tableSAChildren));
                Set<Child> childrenInPullman = new HashSet<>();
                for(Pullman currentPullman : trip.getPullmans()){
                    for(Child currentChild : currentPullman.getChildren()){
                        if(!childrenInPullman.add(currentChild)){
                            showErrorDialog("Il bambino "+currentChild+" è stato aggiunto a più pullman, "+
                            currentPullman.toString()+" è uno di essi");
                            return;
                        }
                    }
                }
                if(!childrenInPullman.containsAll(trip.getChildren())){
                    showErrorDialog("Non tutti i bambini sono stati assegnati ad un pullman");
                    return;
                }
                List<Child> childrenUnsubscribed = new ArrayList<>(childrenInPullman);
                childrenInPullman.removeAll(trip.getChildren());
                if(!childrenInPullman.isEmpty()){
                    for(Pullman current : trip.getPullmans()){
                        current.getChildren().removeAll(childrenUnsubscribed);
                    }
                }
                break;

            case UNNECESSARY:
                lvPullman.getItems().clear();
                break;
        }

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Save trip
        if (!connectionManager.getClient().create(trip)) {
            showErrorDialog("Non è stato possibile inserire la gita");
            return;
        }

        // Go back to the menu
        goBack();
    }


    /**
     * Go back to the add / show trips page
     */
    public void goBack() {
        setCenterFXML((BorderPane)addTripPane.getParent(), "/views/tripAdministration.fxml");
    }

}