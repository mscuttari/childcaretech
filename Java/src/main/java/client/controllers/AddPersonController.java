package main.java.client.controllers;

import javafx.beans.binding.Bindings;
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
import javafx.scene.paint.Color;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiContact;
import main.java.client.gui.GuiParent;
import main.java.client.gui.GuiPediatrist;
import main.java.client.utils.TableUtils;
import main.java.client.layout.RadioButtonTableCell;
import main.java.client.layout.RadioSelectionModel;
import main.java.models.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class AddPersonController implements Initializable {

    // Debug
    private static final String TAG = "AddPersonController";

    @FXML private ComboBox<PersonType> cbPersonType;
    @FXML private ImageView imagePersonType;
    @FXML private ImageView addPersonImage;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Person type
        cbPersonType.getItems().addAll(PersonType.values());

        // Save button cursor
        addPersonImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        addPersonImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));

        // Save button click
        addPersonImage.setOnMouseClicked(event -> savePerson());

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

        SingleSelectionModel<GuiPediatrist> model = new RadioSelectionModel<>(tablePediatrist.itemsProperty());
        columnPediatristSelected.setCellFactory(c -> new RadioButtonTableCell<>(model));
        columnPediatristSelected.setCellValueFactory(c -> Bindings.equal(c.getValue().selectedProperty(), model.selectedItemProperty()));

        columnPediatristFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPediatristLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPediatristFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

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


        // Intollerances tab
        lvIntollerances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonAddIntollerances.setOnAction(event -> addIntollerance());
        buttonRemoveSelectedIntollerances.setOnAction(event -> removeSelectedIntollerances());

        // Add intollerance on enter key press
        txAddIntollerances.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIntollerance();
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
        tfPassword.textProperty().addListener((obs, oldText, newText) -> { passwordConfirmation(); });
        tfPasswordConfirmation.textProperty().addListener((obs, oldText, newText) -> { passwordConfirmation(); });
    }


    /**
     * Add allergy to the allergies list
     */
    private void addAllergy() {
        if (!txAddAllergy.getText().isEmpty()) {
            String allergyName = txAddAllergy.getText().trim();
            Ingredient ingredient = new Ingredient(allergyName);
            lvAllergies.getItems().add(ingredient);
            txAddAllergy.setText("");
        }
    }


    /**
     * Remove the selected allergies
     */
    private void removeSelectedAllergies() {
        List<Ingredient> selectedAllergies = lvAllergies.getSelectionModel().getSelectedItems();

        if (selectedAllergies.isEmpty()) {
            showErrorDialog("Nessuna allergia selezionata");
        } else {
            lvAllergies.getItems().removeAll(selectedAllergies);
            lvAllergies.getSelectionModel().clearSelection();
        }
    }


    /**
     * Add intollerance to the intollerances list
     */
    private void addIntollerance() {
        if (!txAddIntollerances.getText().isEmpty()) {
            String intolleranceName = txAddIntollerances.getText().trim();
            Ingredient ingredient = new Ingredient(intolleranceName);
            lvIntollerances.getItems().add(ingredient);
            txAddIntollerances.setText("");
        }

    }


    /**
     * Remove selected intollerances from the intollerances list
     */
    private void removeSelectedIntollerances() {
        List<Ingredient> selectedIntollerances = lvIntollerances.getSelectionModel().getSelectedItems();

        if (selectedIntollerances.isEmpty()) {
            showErrorDialog("Nessuna intolleranza selezionata");
        } else {
            lvIntollerances.getItems().removeAll(selectedIntollerances);
            lvIntollerances.getSelectionModel().clearSelection();
        }
    }


    public void usernameConfirmation() {
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

    public void passwordConfirmation() {
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
                child.setParents(TableUtils.getSelectedItems(tableParents));
                child.setPediatrist(TableUtils.getFirstSelectedItem(tablePediatrist));
                person = child;
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
                break;
        }


        boolean ingredientExists = false;
        List<Ingredient> allIngredients = connectionManager.getClient().getIngredients();
        List<Ingredient> intollerances = new ArrayList<>();
        for (Ingredient listViewItem : lvIntollerances.getItems()) {
            for(Ingredient databaseListItem : allIngredients){
                if(Objects.equals(listViewItem.getName(), databaseListItem.getName())) {
                    intollerances.add(databaseListItem);
                    ingredientExists = true;
                    break;
                }
            }

            if (!ingredientExists) {
                intollerances.add(listViewItem);
            }
            ingredientExists = false;
        }
        person.setIntollerances(intollerances);

        boolean allergyExists = false;
        List<Ingredient> allergies = new ArrayList<>();
        for (Ingredient listViewItem : lvAllergies.getItems()) {
            for(Ingredient databaseListItem : allIngredients){
                if(Objects.equals(listViewItem.getName(), databaseListItem.getName())) {
                    allergies.add(databaseListItem);
                    allergyExists = true;
                    break;
                }
            }

            if (!allergyExists) {
                allergies.add(listViewItem);
            }
            allergyExists = false;
        }
        person.setAllergies(allergies);

        person.setContacts(TableUtils.getSelectedItems(tableContacts));

        // Check data
        try {
            person.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        // Save person
        connectionManager.getClient().create(person);
    }


    /**
     * Show error dialog
     *
     * @param   message     error message
     */
    private static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
