package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.layout.MyButtonTableCell;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPersonController implements Initializable{

    // Debug
    private static final String TAG = "ShowPersonController";

    @FXML private TableView<Person> tablePeople;
    @FXML private TableColumn<Person, String> columnPeopleFirstName;
    @FXML private TableColumn<Person, String> columnPeopleLastName;
    @FXML private TableColumn<Person, String> columnPeopleFiscalCode;
    @FXML private TableColumn<Person, String> columnPeopleType;
    @FXML private TableColumn<Person, Void> columnPeopleEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Table
        List<Person> people = connectionManager.getClient().getPeople();
        ObservableList<Person> parentsData = FXCollections.observableArrayList(people);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnPeopleType.setCellValueFactory(param -> new SimpleStringProperty(PersonType.getPersonType(param.getValue())));
        columnPeopleEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<Person, Object>() {

            @Override
            public Object call(Person param) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updatePerson.fxml"));

                    UpdatePersonController updatePersonController = new UpdatePersonController(param);
                    loader.setController(updatePersonController);

                    Pane updatePersonPane = loader.load();
                    BorderPane homePane = (BorderPane) tablePeople.getParent();
                    homePane.setCenter(updatePersonPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        tablePeople.setEditable(true);
        tablePeople.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tablePeople.setItems(parentsData);
    }


}
