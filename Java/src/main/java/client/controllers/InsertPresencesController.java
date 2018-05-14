package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiChild;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class InsertPresencesController implements Initializable {

    // Debug
    private static final String TAG = "InsertPresencesController";

    @FXML
    private AnchorPane insertPresencesPane;
    @FXML
    private ImageView imageSavePresences;

    @FXML
    private TableView<Trip> tableTrips;
    @FXML
    private TableColumn<Trip, String> columnTripsTitle;
    @FXML
    private TableColumn<Trip, Date> columnTripsDate;

    @FXML
    private TableView<Stop> tableStops;
    @FXML
    private TableColumn<Stop, String> columnStopsPlaceName;

    @FXML
    private TableView<Pullman> tablePullman;
    @FXML
    private TableColumn<Pullman, String> columnPullmanNumberplate;

    @FXML
    private TableView<GuiChild> tableChildren;
    @FXML
    private TableColumn<GuiChild, Boolean> columnChildrenSelected;
    @FXML
    private TableColumn<GuiChild, String> columnChildrenFirstName;
    @FXML
    private TableColumn<GuiChild, String> columnChildrenLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Save button cursor
        imageSavePresences.setOnMouseEntered(event -> insertPresencesPane.getScene().setCursor(Cursor.HAND));
        imageSavePresences.setOnMouseExited(event -> insertPresencesPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        imageSavePresences.setOnMouseClicked(event -> savePresences());

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

        columnStopsPlaceName.setCellValueFactory(new PropertyValueFactory<>("placeName"));
        tablePullman.setEditable(true);
        tablePullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnChildrenSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnChildrenSelected));
        columnChildrenSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableTrips.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Pullman> pullman = (List<Pullman>) tableTrips.getSelectionModel().getSelectedItem().getTransports();
                ObservableList<Pullman> pullmanData = FXCollections.observableArrayList(pullman);
                tablePullman.setItems(pullmanData);

                List<Stop> stops = (List<Stop>) tableTrips.getSelectionModel().getSelectedItem().getStops();
                ObservableList<Stop> stopsData = FXCollections.observableArrayList(stops);
                tableStops.setItems(stopsData);
            }
        });

        tablePullman.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Child> children = (List<Child>) tableTrips.getSelectionModel().getSelectedItem().getChildrenEnrollments();
                ObservableList<GuiChild> childrenData = TableUtils.getGuiModelsList(children);
                tableChildren.setItems(childrenData);
                tableChildren.setEditable(true);
            }
        });
    }

    public void savePresences() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        if (tableTrips.getSelectionModel().getSelectedItem() == null){
            showErrorDialog("Nessuna gita è selezionata");
            return;
        } else if (tablePullman.getSelectionModel().getSelectedItem() == null) {
            showErrorDialog("Nessun pullman è selezionato");
            return;
        } else if (TableUtils.getSelectedItems(tableChildren).isEmpty()) {
            showErrorDialog("Nessun bambino è selezionato");
            return;
        }
        else  if(tableStops.getSelectionModel().getSelectedItem() == null){
            showErrorDialog("Nessuna tappa è selezionata");
            return;
        }

        List<Child> assignedChildren = (List<Child>) tablePullman.getSelectionModel().getSelectedItem().getChildrenAssignments();

        List<Child> wrongPullmanChildren = new ArrayList<>();
        List<Child> presentChildren = new ArrayList<>();

        for(Child current : TableUtils.getSelectedItems(tableChildren)){
            if(assignedChildren.contains(current)){
                presentChildren.add(current);
            }
            else{
                wrongPullmanChildren.add(current);
            }
        }

        List<Child> notPresentChildren = new ArrayList<>(assignedChildren);
        notPresentChildren.removeAll(presentChildren);

        if(wrongPullmanChildren.isEmpty() && notPresentChildren.isEmpty() ){
            tableStops.getSelectionModel().getSelectedItem().setChildrenPresences(presentChildren);
            connectionManager.getClient().update(tableStops.getSelectionModel().getSelectedItem());
        }
        else {
           showPresencesErrorDialog(notPresentChildren, wrongPullmanChildren);
           return;
        }
    }

    /**
     * Show error dialog
     *
     * @param message error message
     */
    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();

    }


    private static void showPresencesErrorDialog(List<Child> notPresentChildren, List<Child> wrongPullmanChildren) {

        List<String> strings = new LinkedList<>();
        strings.add("I bambini non presenti alla tappa sono:");
        for(Child current: notPresentChildren){
            strings.add(current.toString());
        }
        strings.add("\nI bambini che hanno sbagliato pullman sono:");
        for(Child current : wrongPullmanChildren){
            strings.add(current.toString());
        }
        String message = String.join("\n", strings);
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();

    }

}