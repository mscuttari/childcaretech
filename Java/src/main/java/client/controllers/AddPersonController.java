package main.java.client.controllers;

import javafx.event.EventHandler;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.layout.MyCheckBoxTableCell;
import main.java.client.layout.MyTableViewSelectionModel;
import main.java.models.*;

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
    @FXML private TableView<Pediatrist> tablePediatrist;
    @FXML private TableColumn<Parent, Boolean> columnPediatristSelected;
    @FXML private TableColumn<Parent, String> columnPediatristFirstName;
    @FXML private TableColumn<Parent, String> columnPediatristLastName;
    @FXML private TableColumn<Parent, String> columnPediatristFiscalCode;

    @FXML private Tab tabContacts;
    @FXML private TableView<Contact> tableContacts;
    @FXML private TableColumn<Parent, Boolean> columnContactsSelected;
    @FXML private TableColumn<Parent, String> columnContactsFirstName;
    @FXML private TableColumn<Parent, String> columnContactsLastName;
    @FXML private TableColumn<Parent, String> columnContactsFiscalCode;

    @FXML private Tab tabAllergies;
    @FXML private TextField tfAddAllergies;
    @FXML private ListView<Ingredient> lwAllergies;
    @FXML private Button buttonAddAllergies;
    @FXML private Button buttonRemoveSelectedAllergies;
    @FXML private Label labelErrorAllergies;

    @FXML private Tab tabIntollerances;
    @FXML private TextField tfAddIntollerances;
    @FXML private ListView<Ingredient> lwIntollerances;
    @FXML private Button buttonAddIntollerances;
    @FXML private Button buttonRemoveSelectedIntollerances;
    @FXML private Label labelErrorIntollerances;

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


        // Pediatrist tab
        List<Pediatrist> pediatrists = connectionManager.getClient().getPediatrists();
        ObservableList<Pediatrist> pediatristData = FXCollections.observableArrayList(pediatrists);

        columnPediatristSelected.setCellFactory(param -> new MyCheckBoxTableCell<>());
        columnPediatristFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPediatristLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPediatristFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePediatrist.setEditable(true);
        tablePediatrist.setFocusTraversable(false);
        tablePediatrist.setSelectionModel(new MyTableViewSelectionModel<>(tablePediatrist));
        tablePediatrist.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tablePediatrist.setItems(pediatristData);


        // Contacts tab
        List<Contact> contacts = connectionManager.getClient().getContacts();
        ObservableList<Contact> contactsData = FXCollections.observableArrayList(contacts);

        columnContactsSelected.setCellFactory(param -> new MyCheckBoxTableCell<>());
        columnContactsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnContactsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnContactsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableContacts.setEditable(true);
        tableContacts.setFocusTraversable(false);
        tableContacts.setSelectionModel(new MyTableViewSelectionModel<>(tableContacts));
        tableContacts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableContacts.setItems(contactsData);


        // Allergies tab

        // Add allergies button
        buttonAddAllergies.setOnAction(event -> addAllergies());

        // Add Allergies on enter key press
        EventHandler<KeyEvent> keyPressEventInAddAllergiesTextField = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addAllergies();
        };

        tfAddAllergies.setOnKeyPressed(keyPressEventInAddAllergiesTextField);

        // Remove allergies button
        buttonRemoveSelectedAllergies.setOnAction(event -> removeSelectedAllergies());

        // Set multiple selection model
        lwAllergies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        // Intollerances tab

        // Add intollerances button
        buttonAddIntollerances.setOnAction(event -> addIntollerances());

        // Add intollerances on enter key press
        EventHandler<KeyEvent> keyPressEventInAddIntollerancesTextField = event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIntollerances();
        };

        tfAddIntollerances.setOnKeyPressed(keyPressEventInAddIntollerancesTextField);

        // Remove intollerances button
        buttonRemoveSelectedIntollerances.setOnAction(event -> removeSelectedIntollerances());

        // Set multiple selection model
        lwIntollerances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


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

    public void addAllergies() {
        if(!tfAddAllergies.getText().isEmpty()){
            String allergyName = tfAddAllergies.getText().trim().toLowerCase();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(allergyName);
            lwAllergies.getItems().add(ingredient);
            tfAddAllergies.setText("");
            labelErrorAllergies.setText("");
        }

    }


    public void removeSelectedAllergies() {

        if(!lwAllergies.getSelectionModel().isEmpty()) {
            lwAllergies.getItems().removeAll(lwAllergies.getSelectionModel().getSelectedItems());
            lwAllergies.getSelectionModel().clearSelection();
            labelErrorAllergies.setText("");
        }
        else if(lwAllergies.getItems().isEmpty()){
            labelErrorAllergies.setText("Non ci sono allergie nella lista");
        }
        else{
            labelErrorAllergies.setText("Non ci sono allergie selezionate");
        }

    }

    public void addIntollerances() {
        if(!tfAddIntollerances.getText().isEmpty()){
            String intolleranceName = tfAddIntollerances.getText().trim().toLowerCase();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(intolleranceName);
            lwIntollerances.getItems().add(ingredient);
            tfAddIntollerances.setText("");
            labelErrorIntollerances.setText("");
        }

    }


    public void removeSelectedIntollerances() {

        if(!lwIntollerances.getSelectionModel().isEmpty()) {
            lwIntollerances.getItems().removeAll(lwIntollerances.getSelectionModel().getSelectedItems());
            lwIntollerances.getSelectionModel().clearSelection();
            labelErrorIntollerances.setText("");
        }
        else if(lwIntollerances.getItems().isEmpty()){
            labelErrorIntollerances.setText("Non ci sono intolleranze nella lista");
        }
        else{
            labelErrorIntollerances.setText("Non ci sono intolleranze selezionate");
        }

    }

}