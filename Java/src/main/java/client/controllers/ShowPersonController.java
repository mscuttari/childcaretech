package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPersonController implements Initializable{

    // Debug
    private static final String TAG = "ShowPersonController";

    @FXML private Pane showPersonPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Person> tablePeople;
    @FXML private TableColumn<Person, String> columnPeopleFirstName;
    @FXML private TableColumn<Person, String> columnPeopleLastName;
    @FXML private TableColumn<Person, String> columnPeopleFiscalCode;
    @FXML private TableColumn<Person, String> columnPeopleType;
    @FXML private TableColumn<Person, Void> columnPeopleEdit;
    @FXML private TableColumn<Person, Void> columnPeopleDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showPersonPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showPersonPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Person> people = new ArrayList<>();

        people.addAll(connectionManager.getClient().getChildren());
        people.addAll(connectionManager.getClient().getParents());
        people.addAll(connectionManager.getClient().getContacts());
        people.addAll(connectionManager.getClient().getPediatrists());
        people.addAll(connectionManager.getClient().getStaff());

        ObservableList<Person> peopleData = FXCollections.observableArrayList(people);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnPeopleType.setCellValueFactory(param -> new SimpleStringProperty(PersonType.getPersonType(param.getValue()).toString()));
        columnPeopleEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Person, Object>() {

            @Override
            public Object call(Person param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updatePerson.fxml"));

                    Pane updatePersonPane = loader.load();
                    UpdatePersonController controller = loader.getController();
                    controller.setPerson(param);

                    BorderPane homePane = (BorderPane) showPersonPane.getParent();
                    homePane.setCenter(updatePersonPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));
        columnPeopleDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {
            //delete
            connectionManager.getClient().delete(param1);

            try {
                Pane newPaneShowPerson = FXMLLoader.load(getClass().getResource("/views/showPerson.fxml"));
                BorderPane homePane = (BorderPane) showPersonPane.getParent();
                homePane.setCenter(newPaneShowPerson);
            } catch (IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }

            return null;
        }));

        tablePeople.setEditable(true);
        tablePeople.setItems(peopleData);
    }

    public void goBack() {
        try {
            Pane anagraphicPane = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            BorderPane homePane = (BorderPane) showPersonPane.getParent();
            homePane.setCenter(anagraphicPane);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


}
