package main.java.client.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
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
import main.java.exceptions.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiContact;
import main.java.client.gui.GuiIngredient;
import main.java.client.gui.GuiParent;
import main.java.client.gui.GuiPediatrist;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class AddPersonController extends AbstractController implements Initializable {

    @FXML private Pane addPersonPane;
    @FXML private ComboBox<PersonType> cbPersonType;
    @FXML private ImageView imagePersonType;
    @FXML private ImageView addPersonImage;
    @FXML private ImageView goBackImage;

    @FXML private TabPane tabPane;

    @FXML private TextField tfFiscalCode;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private DatePicker dpBirthDate;
    @FXML private TextField tfAddress;
    @FXML private TextField tfTelephone;

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
    @FXML private TableColumn<GuiPediatrist, String> columnPediatristTelephone;
    @FXML private TableColumn<GuiPediatrist, Integer> columnPediatristChildren;

    @FXML private Tab tabContacts;
    @FXML private TableView<GuiContact> tableContacts;
    @FXML private TableColumn<GuiContact, Boolean> columnContactsSelected;
    @FXML private TableColumn<GuiContact, String> columnContactsFirstName;
    @FXML private TableColumn<GuiContact, String> columnContactsLastName;
    @FXML private TableColumn<GuiContact, String> columnContactsFiscalCode;

    @FXML private Tab tabAllergies;
    @FXML private TextField txAddAllergy;
    @FXML private ListView<GuiIngredient> lvAllergies;
    @FXML private Button buttonAddAllergy;
    @FXML private Button buttonRemoveSelectedAllergies;

    @FXML private Tab tabIntolerances;
    @FXML private TextField txAddIntolerances;
    @FXML private ListView<GuiIngredient> lvIntolerances;
    @FXML private Button buttonAddIntolerances;
    @FXML private Button buttonRemoveSelectedIntolerances;

    @FXML private Tab tabLoginData;
    @FXML private TextField tfUsername;
    @FXML private TextField tfUsernameConfirmation;
    @FXML private Label labelUsername;
    @FXML private PasswordField tfPassword;
    @FXML private PasswordField tfPasswordConfirmation;
    @FXML private Label labelPassword;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Person type
        cbPersonType.getItems().addAll(PersonType.values());

        // Save button
        addPersonImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        addPersonImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));
        addPersonImage.setOnMouseClicked(event -> savePerson());
        Tooltip.install(addPersonImage, new Tooltip("Salva"));

        // Go back button
        goBackImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(goBackImage, new Tooltip("Indietro"));

        goBackImage.setOnMouseClicked(event -> {
            if (showConfirmationDialog("Sei sicuro di voler tornare indietro? I dati inseriti andranno persi"))
                goBack();
        });

        cbPersonType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == newValue) return;
            tabPane.getTabs().remove(1, tabPane.getTabs().size());

            switch (newValue) {
                case CHILD:
                    imagePersonType.setImage(new Image("/images/baby.png"));
                    tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntolerances, tabContacts);
                    break;

                case CONTACT:
                    imagePersonType.setImage(new Image("/images/grandparents.png"));
                    break;

                case PARENT:
                    imagePersonType.setImage(new Image("/images/family.png"));
                    break;

                case PEDIATRIST:
                    imagePersonType.setImage(new Image("/images/doctor.png"));
                    break;

                case STAFF:
                    imagePersonType.setImage(new Image("/images/secretary.png"));
                    tabPane.getTabs().addAll(tabAllergies, tabIntolerances, tabLoginData);
                    break;
            }
        });

        cbPersonType.getSelectionModel().selectFirst();
        tabPane.getTabs().remove(tabLoginData);


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();


        // Parents tab
        List<Parent> parents = connectionManager.getClient().getParents();
        ObservableList<GuiParent> parentsData = TableUtils.getGuiModelsList(parents);

        columnParentsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnParentsSelected));
        columnParentsSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnParentsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnParentsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnParentsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableParents.setEditable(true);
        tableParents.setItems(parentsData);


        // Pediatrist tab
        List<Pediatrist> pediatrists = connectionManager.getClient().getPediatrists();
        ObservableList<GuiPediatrist> pediatristData = TableUtils.getGuiModelsList(pediatrists);

        columnPediatristSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnPediatristSelected));
        columnPediatristSelected.setCellValueFactory(param -> param.getValue().selectedProperty());

        columnPediatristFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPediatristLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPediatristFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        columnPediatristTelephone.setCellValueFactory(param -> {
            String telephone = param.getValue().getModel().getTelephone();
            return new SimpleStringProperty(telephone == null ? "-" : telephone);
        });

        columnPediatristChildren.setCellValueFactory(param -> new SimpleIntegerProperty(param.getValue().getModel().getChildren().size()).asObject());

        tablePediatrist.setEditable(true);
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


        // Allergies tab
        lvAllergies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonRemoveSelectedAllergies.setOnAction(event -> removeSelectedAllergies());
        buttonAddAllergy.setOnAction(event -> addAllergy());

        // Add allergy on enter key press
        txAddAllergy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addAllergy();
        });


        // Intolerances tab
        lvIntolerances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonAddIntolerances.setOnAction(event -> addIntolerance());
        buttonRemoveSelectedIntolerances.setOnAction(event -> removeSelectedIntolerances());

        // Add intolerance on enter key press
        txAddIntolerances.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIntolerance();
        });


        // LoginData tab

        //Username confirmation
        tfUsername.textProperty().addListener((obs, oldText, newText) -> usernameConfirmation());
        tfUsernameConfirmation.textProperty().addListener((obs, oldText, newText) -> usernameConfirmation());

        //Password confirmation
        tfPassword.textProperty().addListener((obs, oldText, newText) -> passwordConfirmation());
        tfPasswordConfirmation.textProperty().addListener((obs, oldText, newText) -> passwordConfirmation());
    }


    /**
     * Add the specified ingredient to the allergies list
     */
    private void addAllergy() {
        if (!txAddAllergy.getText().isEmpty()) {
            String allergyName = txAddAllergy.getText().toLowerCase().trim();

            Ingredient ingredient = new Ingredient(allergyName);
            GuiIngredient guiIngredient = new GuiIngredient(ingredient);

            lvAllergies.getItems().add(guiIngredient);
            txAddAllergy.setText("");
        }
    }


    /**
     * Remove the selected allergies from the list
     */
    private void removeSelectedAllergies() {
        List<GuiIngredient> selectedAllergies = lvAllergies.getSelectionModel().getSelectedItems();

        if (selectedAllergies.isEmpty()) {
            showErrorDialog("Nessuna allergia selezionata");
        } else {
            lvAllergies.getItems().removeAll(selectedAllergies);
            lvAllergies.getSelectionModel().clearSelection();
        }
    }


    /**
     * Add the specified ingredient to the intolerances list
     */
    private void addIntolerance() {
        if (!txAddIntolerances.getText().isEmpty()) {
            String intoleranceName = txAddIntolerances.getText().toLowerCase().trim();

            Ingredient ingredient = new Ingredient(intoleranceName);
            GuiIngredient guiIngredient = new GuiIngredient(ingredient);

            lvIntolerances.getItems().add(guiIngredient);
            txAddIntolerances.setText("");
        }
    }


    /**
     * Remove the selected intolerances from the list
     */
    private void removeSelectedIntolerances() {
        List<GuiIngredient> selectedIntolerances = lvIntolerances.getSelectionModel().getSelectedItems();

        if (selectedIntolerances.isEmpty()) {
            showErrorDialog("Nessuna intolleranza selezionata");
        } else {
            lvIntolerances.getItems().removeAll(selectedIntolerances);
            lvIntolerances.getSelectionModel().clearSelection();
        }
    }


    /**
     * Username confirmation check
     */
    private void usernameConfirmation() {
        if (tfUsername.getText().isEmpty()) {
            labelUsername.setText(("Il campo USERNAME è vuoto"));
            labelUsername.setTextFill(Color.BLUE);
        } else if (tfUsername.getText().equals(tfUsernameConfirmation.getText())) {
            labelUsername.setText("Il campo USERNAME è confermato");
            labelUsername.setTextFill(Color.GREEN);
        } else {
            labelUsername.setText("Il campo USERNAME non è confermato");
            labelUsername.setTextFill(Color.RED);
        }
    }


    /**
     * Password confirmation check
     */
    private void passwordConfirmation() {
        if (tfPassword.getText().isEmpty()) {
            labelPassword.setText(("Il campo PASSWORD è vuoto"));
            labelPassword.setTextFill(Color.BLUE);
        } else if (tfPassword.getText().equals(tfPasswordConfirmation.getText())) {
            labelPassword.setText("Il campo PASSWORD è confermato");
            labelPassword.setTextFill(Color.GREEN);
        } else {
            labelPassword.setText("Il campo PASSWORD non è confermato");
            labelPassword.setTextFill(Color.RED);
        }
    }


    /**
     * Save person in the database
     */
    private void savePerson() {
        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Data
        String fiscalCode = tfFiscalCode.getText().trim();
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        Date birthDate = dpBirthDate.getValue() == null ? null : Date.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        String address = tfAddress.getText().trim();
        String telephone = tfTelephone.getText().trim();
        String username = tfUsername.getText().trim();
        String password = tfPassword.getText();

        // Create person
        Person person = null;

        switch (cbPersonType.getValue()) {
            case CHILD:
                Child child = new Child(fiscalCode, firstName, lastName, birthDate, address, telephone, null);
                person = child;

                // Id
                Long childId = ConnectionManager.getInstance().getClient().createChildId();

                if (childId == null) {
                    showErrorDialog("Impossibile creare un codice identificativo per il bambino");
                    return;
                }

                child.setId(childId);

                // Pediatrist
                List<Pediatrist> selectedPediatrists = TableUtils.getSelectedItems(tablePediatrist);

                if (selectedPediatrists.size() > 1) {
                    showErrorDialog("E' possibile selezionare solo un pediatra");
                    return;
                }

                if (selectedPediatrists.size() >= 1){
                    child.setPediatrist(selectedPediatrists.get(0));
                }

                // Parents
                child.setParents(TableUtils.getSelectedItems(tableParents));

                // Contacts
                child.setContacts(TableUtils.getSelectedItems(tableContacts));

                // Allergies and intolerances
                child.setAllergies(TableUtils.getModelsList(lvAllergies.getItems()));
                child.setIntolerances(TableUtils.getModelsList(lvIntolerances.getItems()));

                break;

            case CONTACT:
                person = new Contact(fiscalCode, firstName, lastName, birthDate, address, telephone);
                break;

            case PARENT:
                person = new Parent(fiscalCode, firstName, lastName, birthDate, address, telephone);
                break;

            case PEDIATRIST:
                person = new Pediatrist(fiscalCode, firstName, lastName, birthDate, address, telephone);
                break;

            case STAFF:
                person = new Staff(fiscalCode, firstName, lastName, birthDate, address, telephone, username, password);
                person.setIntolerances(TableUtils.getModelsList(lvIntolerances.getItems()));
                person.setAllergies(TableUtils.getModelsList(lvAllergies.getItems()));

                break;
        }

        // Check data
        try {
            person.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getModelName(), e.getMessage());
            return;
        }

        // Save person
        if(!connectionManager.getClient().create(person)) {
            showErrorDialog("Non è stato possibile inserire la persona");
            return;
        }

        // Confirmation dialog
        showInformationDialog("La persona \"" + person.getFirstName() + " " + person.getLastName() + "\" è stata salvata");

        // Go back to the menu
        goBack();
    }


    /**
     * Go back to the main anagraphic page
     */
    private void goBack() {
        setCenterFXML((BorderPane)addPersonPane.getParent(), "/views/anagraphic.fxml");
    }

}
