package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.layout.MyCheckBoxTableCell;
import main.java.client.layout.MyTableViewSelectionModel;
import main.java.models.Parent;
import main.java.models.PersonType;

import java.net.URL;
import java.util.*;

public class AddPersonController implements Initializable {

    @FXML private ComboBox<PersonType> cbPersonType;
    @FXML private ImageView imagePersonType;

    @FXML private Tab tabParents;
    @FXML private TableView<Parent> tableParents;
    @FXML private TableColumn<Parent, Boolean> columnParentsSelected;
    @FXML private TableColumn<Parent, String> columnParentsFirstName;
    @FXML private TableColumn<Parent, String> columnParentsLastName;
    @FXML private TableColumn<Parent, String> columnParentsFiscalCode;

    @FXML private Tab tabPediatrist;
    @FXML private Tab tabAllergies;
    @FXML private Tab tabIntollerances;
    @FXML private Tab tabContacts;
    @FXML private Tab tabLoginData;
    @FXML private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Person type
        cbPersonType.getItems().addAll(PersonType.values());

        cbPersonType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) return;
            tabPane.getTabs().remove(1, tabPane.getTabs().size());

            switch (newValue) {
                case CHILD:
                    imagePersonType.setImage(new Image("/images/baby.png"));
                    tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntollerances, tabContacts);
                    break;

                case CONTACT:
                    imagePersonType.setImage(new Image("/images/grandparents.png"));
                    break;

                case PARENT:
                    imagePersonType.setImage(new Image("/images/family.png"));
                    break;

                case PEDIATRIST:
                    imagePersonType.setImage(new Image("/images/doctor.png"));
                    tabPane.getTabs().addAll(tabAllergies, tabIntollerances);
                    break;

                case STAFF:
                    imagePersonType.setImage(new Image("/images/secretary.png"));
                    tabPane.getTabs().addAll(tabAllergies, tabIntollerances, tabContacts, tabLoginData);
                    break;
            }
        });

        cbPersonType.getSelectionModel().selectFirst();
        tabPane.getTabs().remove(tabLoginData);


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();


        // Parents tab
        List<Parent> parents = connectionManager.getClient().getParents();
        ObservableList<Parent> parentsData = FXCollections.observableArrayList(parents);

        columnParentsSelected.setCellFactory(param -> new MyCheckBoxTableCell<>());
        columnParentsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnParentsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnParentsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableParents.setEditable(true);
        tableParents.setFocusTraversable(false);
        tableParents.setSelectionModel(new MyTableViewSelectionModel<>(tableParents));
        tableParents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableParents.setItems(parentsData);

        // Avvia il programma, seleziona le voci, aspetta 5 secondi e leggi il log
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ObservableList<Parent> selected = tableParents.getSelectionModel().getSelectedItems();
                for (Parent parent : selected) {
                    LogUtils.e("Parent", parent.getFirstName() + ", " + parent.getLastName());
                }
            }
        }, 5000);
    }

}