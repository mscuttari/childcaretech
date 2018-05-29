package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiChild;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class InsertPresencesController extends AbstractController implements Initializable {

    @FXML private Pane insertPresencesPane;
    @FXML private ImageView imageSavePresences;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Trip> tableTrips;
    @FXML private TableColumn<Trip, String> columnTripsTitle;
    @FXML private TableColumn<Trip, String> columnTripsDate;

    @FXML private TableView<Pullman> tablePullman;
    @FXML private TableColumn<Pullman, String> columnPullmanId;

    @FXML private TableView<GuiChild> tableChildren;
    @FXML private TableColumn<GuiChild, Boolean> columnChildrenSelected;
    @FXML private TableColumn<GuiChild, String> columnChildrenFirstName;
    @FXML private TableColumn<GuiChild, String> columnChildrenLastName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Save button
        imageSavePresences.setOnMouseEntered(event -> insertPresencesPane.getScene().setCursor(Cursor.HAND));
        imageSavePresences.setOnMouseExited(event -> insertPresencesPane.getScene().setCursor(Cursor.DEFAULT));
        imageSavePresences.setOnMouseClicked(event -> savePresences());
        Tooltip.install(imageSavePresences, new Tooltip("Salva"));

        // Go back button
        goBackImage.setOnMouseEntered(event -> insertPresencesPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> insertPresencesPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table trip
        List<Trip> trips = connectionManager.getClient().getTrips();
        ObservableList<Trip> tripsData = FXCollections.observableArrayList(trips);

        columnTripsTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        columnTripsDate.setCellValueFactory(param -> {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return new SimpleStringProperty(formatter.format(param.getValue().getDate()));
        });

        tableTrips.setEditable(true);
        tableTrips.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableTrips.setItems(tripsData);

        columnPullmanId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePullman.setEditable(true);
        tablePullman.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        columnChildrenSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnChildrenSelected));
        columnChildrenSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnChildrenFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnChildrenLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableTrips.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            tablePullman.setVisible(true);
            if (newSelection != null && newSelection.getSeatsAssignmentType() != SeatsAssignmentType.UNNECESSARY) {
                List<Pullman> pullman = (List<Pullman>) tableTrips.getSelectionModel().getSelectedItem().getPullmans();
                ObservableList<Pullman> pullmanData = FXCollections.observableArrayList(pullman);
                tablePullman.setItems(pullmanData);
            }
            else if (newSelection.getSeatsAssignmentType() == SeatsAssignmentType.UNNECESSARY){
                tablePullman.setVisible(false);
                List<Child> children = (List<Child>) tableTrips.getSelectionModel().getSelectedItem().getChildren();
                ObservableList<GuiChild> childrenData = TableUtils.getGuiModelsList(children);
                tableChildren.setItems(childrenData);
                tableChildren.setEditable(true);
            }
        });

        tablePullman.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<Child> children = (List<Child>) tableTrips.getSelectionModel().getSelectedItem().getChildren();
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
        } else if (TableUtils.getSelectedItems(tableChildren).isEmpty()) {
            showErrorDialog("Nessun bambino è selezionato");
            return;
        }

        switch (tableTrips.getSelectionModel().getSelectedItem().getSeatsAssignmentType()){
            case UNNECESSARY:
                List<Child> notPresentChildren = new ArrayList<>(tableTrips.getSelectionModel().getSelectedItem().getChildren());
                notPresentChildren.removeAll(TableUtils.getSelectedItems(tableChildren));
                if(notPresentChildren.isEmpty() ){
                    showErrorDialog("Tutti i bambini sono presenti");
                }
                else {
                    showPresencesErrorDialog(notPresentChildren, null, tableTrips.getSelectionModel().getSelectedItem().getSeatsAssignmentType());
                    return;
                }
                return;
        }

        if (tablePullman.getSelectionModel().getSelectedItem() == null) {
            showErrorDialog("Nessun pullman è selezionato");
            return;
        }

        List<Child> assignedChildren = (List<Child>) tablePullman.getSelectionModel().getSelectedItem().getChildren();

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
            showErrorDialog("Tutti i bambini sono presenti e non c'è nessun bambino di un altro pullman");
        }
        else {
           showPresencesErrorDialog(notPresentChildren, wrongPullmanChildren, tableTrips.getSelectionModel().getSelectedItem().getSeatsAssignmentType());
           return;
        }
    }


    private static void showPresencesErrorDialog(List<Child> notPresentChildren, List<Child> wrongPullmanChildren, SeatsAssignmentType seatsAssignmentType) {

        List<String> strings = new LinkedList<>();
        strings.add("I bambini non presenti alla tappa sono:");
        for(Child current: notPresentChildren){
            strings.add(current.toString());
        }
        if(seatsAssignmentType != SeatsAssignmentType.UNNECESSARY){
            strings.add("\nI bambini che hanno sbagliato pullman sono:");
            for(Child current : wrongPullmanChildren){
                strings.add(current.toString());
            }
        }
        String message = String.join("\n", strings);
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();

    }


    /**
     * Go back to the trips page
     */
    public void goBack() {
        setCenterFXML((BorderPane) insertPresencesPane.getParent(), "/views/seatsAssignment.fxml");
    }

}