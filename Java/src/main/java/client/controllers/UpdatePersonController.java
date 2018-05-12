package main.java.client.controllers;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiContact;
import main.java.client.gui.GuiParent;
import main.java.client.gui.GuiPediatrist;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class UpdatePersonController implements Initializable {

    // Debug
    private static final String TAG = "UpdatePersonController";

    private Person person;

    @FXML private Pane updatePersonPane;
    @FXML private ImageView imagePersonType;
    @FXML private ImageView updatePersonImage;
    @FXML private ImageView deletePersonImage;

    @FXML private TabPane tabPane;

    @FXML TextField tfFiscalCode;
    @FXML TextField tfFirstName;
    @FXML TextField tfLastName;
    @FXML DatePicker dpBirthdate;
    @FXML TextField tfAddress;
    @FXML TextField tfTelephone;

    @FXML private Tab tabParents;
    @FXML private TableView<GuiParent> tableParents;
    @FXML private TableColumn<GuiParent, Boolean> columnParentsSelected;
    @FXML private TableColumn<GuiParent, String> columnParentsFirstName;
    @FXML private TableColumn<GuiParent, String> columnParentsLastName;
    @FXML private TableColumn<GuiParent, String> columnParentsFiscalCode;

    @FXML private Tab tabPediatrist;
    @FXML private TableView<GuiPediatrist> tablePediatrist;
    @FXML private TableColumn<GuiPediatrist, Boolean> columnPediatristSelected;
    @FXML private TableColumn<GuiPediatrist, String> columnPediatristFirstName;
    @FXML private TableColumn<GuiPediatrist, String> columnPediatristLastName;
    @FXML private TableColumn<GuiPediatrist, String> columnPediatristFiscalCode;

    @FXML private Tab tabContacts;
    @FXML private TableView<GuiContact> tableContacts;
    @FXML private TableColumn<GuiContact, Boolean> columnContactsSelected;
    @FXML private TableColumn<GuiContact, String> columnContactsFirstName;
    @FXML private TableColumn<GuiContact, String> columnContactsLastName;
    @FXML private TableColumn<GuiContact, String> columnContactsFiscalCode;

    @FXML private Tab tabAllergies;
    @FXML private TextField txAddAllergy;
    @FXML private ListView<Ingredient> lvAllergies;
    @FXML private Button buttonAddAllergy;
    @FXML private Button buttonRemoveSelectedAllergies;
    @FXML private Label labelErrorAllergies;

    @FXML private Tab tabIntollerances;
    @FXML private TextField txAddIntollerances;
    @FXML private ListView<Ingredient> lvIntollerances;
    @FXML private Button buttonAddIntollerances;
    @FXML private Button buttonRemoveSelectedIntollerances;
    @FXML private Label labelErrorIntollerances;

    @FXML private Tab tabLoginData;
    @FXML private TextField tfUsername;
    @FXML private TextField tfUsernameConfirmation;
    @FXML private Label labelUsername;
    @FXML private PasswordField tfPassword;
    @FXML private PasswordField tfPasswordConfirmation;
    @FXML private Label labelPassword;

    public UpdatePersonController(Person person){
        this.person = person;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // update person image
        updatePersonImage.setOnMouseClicked(event -> updatePerson());

        //delete person image
        deletePersonImage.setOnMouseClicked(event -> deletePerson());

        tabPane.getTabs().remove(1, tabPane.getTabs().size());

        switch (PersonType.getPersonType(person)) {
            case "Bambino":
                imagePersonType.setImage(new Image("/images/baby.png"));
                tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntollerances, tabContacts);
                break;

            case "Contatto":
                imagePersonType.setImage(new Image("/images/grandparents.png"));
                break;

            case "Genitore":
                imagePersonType.setImage(new Image("/images/family.png"));
                break;

            case "Pediatra":
                imagePersonType.setImage(new Image("/images/doctor.png"));
                tabPane.getTabs().addAll(tabAllergies, tabIntollerances);
                break;

            case "Staff":
                imagePersonType.setImage(new Image("/images/secretary.png"));
                tfUsername.setText(((Staff) person).getUsername());
                tfUsernameConfirmation.setText(((Staff) person).getUsername());
                tfPassword.setText(((Staff) person).getPassword());
                tfPasswordConfirmation.setText(((Staff) person).getPassword());
                tabPane.getTabs().addAll(tabAllergies, tabIntollerances, tabContacts, tabLoginData);
                break;
        }


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Type tab
        tfFiscalCode.setText(person.getFiscalCode());
        tfFirstName.setText(person.getFirstName());
        tfLastName.setText(person.getLastName());
        tfAddress.setText(person.getAddress());
        tfTelephone.setText(person.getTelephone());
        if (person.getBirthdate() != null) {
            dpBirthdate.setValue(new java.sql.Date(person.getBirthdate().getTime()).toLocalDate());
        }

        // Parents tab
        List<Parent> parents = connectionManager.getClient().getParents();
        ObservableList<GuiParent> parentsData = TableUtils.getGuiModelsList(parents);

        columnParentsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnParentsSelected));
        columnParentsSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnParentsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnParentsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnParentsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableParents.setEditable(true);
        tableParents.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableParents.setItems(parentsData);

        // Pediatrist tab
        List<Pediatrist> pediatrists = connectionManager.getClient().getPediatrists();
        ObservableList<GuiPediatrist> pediatristData = TableUtils.getGuiModelsList(pediatrists);

        columnPediatristSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnPediatristSelected));
        columnPediatristSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnPediatristFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPediatristLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPediatristFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePediatrist.setEditable(true);
        tablePediatrist.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tablePediatrist.setItems(pediatristData);


        // Contacts tab
        List<Contact> contacts = connectionManager.getClient().getContacts();
        ObservableList<GuiContact> contactsData = TableUtils.getGuiModelsList(contacts);

        columnContactsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnContactsSelected));
        columnContactsSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnContactsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnContactsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnContactsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableContacts.setEditable(true);
        tableContacts.setItems(contactsData);

        List<Contact> personContacts = (List<Contact>) person.getContacts();
        for (GuiContact item : tableContacts.getItems()){
            if(personContacts.contains(item.getModel()))
                item.setSelected(true);
        }

        // Allergies tab
        lvAllergies.getItems().setAll(person.getAllergies());
        lvAllergies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonRemoveSelectedAllergies.setOnAction(event -> removeSelectedAllergies());
        buttonAddAllergy.setOnAction(event -> addAllergies());

        // Add allergy on enter key press
        txAddAllergy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addAllergies();
        });


        // Intollerances tab
        lvIntollerances.getItems().setAll(person.getIntollerances());
        lvIntollerances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonAddIntollerances.setOnAction(event -> addIntollerances());
        buttonRemoveSelectedIntollerances.setOnAction(event -> removeSelectedIntollerances());

        // Add intollerance on enter key press
        txAddIntollerances.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIntollerances();
        });


        // LoginData tab

        //Username confirmation
        tfUsername.textProperty().addListener((obs, oldText, newText) -> {
            usernameConfirmation();
        });
        tfUsernameConfirmation.textProperty().addListener((obs, oldText, newText) -> {
            usernameConfirmation();
        });

        //Password confirmation
        tfPassword.textProperty().addListener((obs, oldText, newText) -> {
            passwordConfirmation();
        });
        tfPasswordConfirmation.textProperty().addListener((obs, oldText, newText) -> {
            passwordConfirmation();
        });

    }

    public void addAllergies() {
        if(!txAddAllergy.getText().isEmpty()){
            String allergyName = txAddAllergy.getText().trim();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(allergyName);
            lvAllergies.getItems().add(ingredient);
            txAddAllergy.setText("");
            labelErrorAllergies.setText("");
        }

    }


    public void removeSelectedAllergies() {

        if(!lvAllergies.getSelectionModel().isEmpty()) {
            lvAllergies.getItems().removeAll(lvAllergies.getSelectionModel().getSelectedItems());
            lvAllergies.getSelectionModel().clearSelection();
            labelErrorAllergies.setText("");
        }
        else if(lvAllergies.getItems().isEmpty()){
            labelErrorAllergies.setText("Non ci sono allergie nella lista");
        }
        else{
            labelErrorAllergies.setText("Non ci sono allergie selezionate");
        }

    }

    public void addIntollerances() {
        if(!txAddIntollerances.getText().isEmpty()){
            String intolleranceName = txAddIntollerances.getText().trim();
            Ingredient ingredient = new Ingredient();
            ingredient.setName(intolleranceName);
            lvIntollerances.getItems().add(ingredient);
            txAddIntollerances.setText("");
            labelErrorIntollerances.setText("");
        }

    }


    public void removeSelectedIntollerances() {

        if(!lvIntollerances.getSelectionModel().isEmpty()) {
            lvIntollerances.getItems().removeAll(lvIntollerances.getSelectionModel().getSelectedItems());
            lvIntollerances.getSelectionModel().clearSelection();
            labelErrorIntollerances.setText("");
        }
        else if(lvIntollerances.getItems().isEmpty()){
            labelErrorIntollerances.setText("Non ci sono intolleranze nella lista");
        }
        else{
            labelErrorIntollerances.setText("Non ci sono intolleranze selezionate");
        }

    }

    public void usernameConfirmation(){
        if(tfUsername.getText().isEmpty()){
            labelUsername.setText(("Il campo USERNAME è vuoto"));
            labelUsername.setTextFill(Color.BLUE);
        }
        else if (tfUsername.getText().equals(tfUsernameConfirmation.getText())) {
            labelUsername.setText("Il campo USERNAME è confermato");
            labelUsername.setTextFill(Color.GREEN);
        } else {
            labelUsername.setText("Il campo USERNAME non è confermato");
            labelUsername.setTextFill(Color.RED);
        }
    }

    public void passwordConfirmation(){
        if(tfPassword.getText().isEmpty()){
            labelPassword.setText(("Il campo PASSWORD è vuoto"));
            labelPassword.setTextFill(Color.BLUE);
        }
        else if (tfPassword.getText().equals(tfPasswordConfirmation.getText())) {
            labelPassword.setText("Il campo PASSWORD è confermato");
            labelPassword.setTextFill(Color.GREEN);
        } else {
            labelPassword.setText("Il campo PASSWORD non è confermato");
            labelPassword.setTextFill(Color.RED);
        }
    }

    public void updatePerson() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();
/*
        switch (PersonType.getPersonType(person)) {
            case "Bambino":
                ((Child)person).setParents((Collection<Parent>) tableParents.getSelectionModel().getSelectedItems());
                break;
            case "Genitore":
                break;
            case "Staff":
                ((Staff)person).setUsername(tfUsername.getText());
                ((Staff)person).setPassword(tfPassword.getText());
        }*/

        person.setFiscalCode(tfFiscalCode.getText());
        person.setFirstName(tfFirstName.getText());
        person.setLastName(tfLastName.getText());
        person.setBirthdate(Date.from(dpBirthdate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        person.setAddress(tfAddress.getText());
        person.setTelephone(tfTelephone.getText());
        //person.setIntollerances((Collection<Ingredient>)lvIntollerances.getSelectionModel().getSelectedItems());
        //person.setAllergies((Collection<Ingredient>) lvAllergies.getSelectionModel().getSelectedItems());
        //person.setContacts((Collection<Contact>) tableContacts.getSelectionModel().getSelectedItems());

        connectionManager.getClient().update(person);

    }

    public void deletePerson() {

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //delete
        connectionManager.getClient().delete(person);

        try {
            TableView tablePeople = FXMLLoader.load(getClass().getResource("/views/showPerson.fxml"));
            BorderPane homePane = (BorderPane) updatePersonPane.getParent();
            homePane.setCenter(tablePeople);
        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }

}