package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
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
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiContact;
import main.java.client.gui.GuiParent;
import main.java.client.gui.GuiPediatrist;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.time.ZoneId;
import java.util.*;

public class UpdatePersonController extends AbstractController implements Initializable {

    private Person person;

    @FXML private Pane paneRoot;
    @FXML private ImageView imagePersonType;
    @FXML private ImageView updatePersonImage;
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

    @FXML private Tab tabIntolerances;
    @FXML private TextField txAddIntolerances;
    @FXML private ListView<Ingredient> lvIntolerances;
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

        // Update button
        updatePersonImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        updatePersonImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));
        updatePersonImage.setOnMouseClicked(event -> updatePerson());


        // Go back button
        goBackImage.setOnMouseEntered(event -> tabPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> tabPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());


        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();


        // Parents
        List<Parent> parents = connectionManager.getClient().getParents();
        ObservableList<GuiParent> parentsData = TableUtils.getGuiModelsList(parents);

        columnParentsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnParentsSelected));
        columnParentsSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnParentsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnParentsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnParentsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableParents.setEditable(true);
        tableParents.setItems(parentsData);


        // Pediatrists
        List<Pediatrist> pediatrists = connectionManager.getClient().getPediatrists();
        ObservableList<GuiPediatrist> pediatristData = TableUtils.getGuiModelsList(pediatrists);

        columnPediatristSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnPediatristSelected));
        columnPediatristSelected.setCellValueFactory(param -> param.getValue().selectedProperty());

        columnPediatristFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPediatristLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPediatristFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePediatrist.setEditable(true);
        tablePediatrist.setItems(pediatristData);


        // Contacts
        List<Contact> contacts = connectionManager.getClient().getContacts();
        ObservableList<GuiContact> contactsData = TableUtils.getGuiModelsList(contacts);

        columnContactsSelected.setCellFactory(CheckBoxTableCell.forTableColumn(columnContactsSelected));
        columnContactsSelected.setCellValueFactory(param -> param.getValue().selectedProperty());
        columnContactsFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnContactsLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnContactsFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tableContacts.setEditable(true);
        tableContacts.setItems(contactsData);


        // Allergies
        lvAllergies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonRemoveSelectedAllergies.setOnAction(event -> removeSelectedAllergies());
        buttonAddAllergy.setOnAction(event -> addAllergies());


        // Add allergy on enter key press
        txAddAllergy.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addAllergies();
        });


        // Intolerances
        lvIntolerances.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        buttonAddIntolerances.setOnAction(event -> addIntolerances());
        buttonRemoveSelectedIntolerances.setOnAction(event -> removeSelectedIntolerances());


        // Add intolerance on enter key press
        txAddIntolerances.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER)
                addIntolerances();
        });


        // LoginData tab

        //Username confirmation
        tfUsername.textProperty().addListener((obs, oldText, newText) -> usernameConfirmation());
        tfUsernameConfirmation.textProperty().addListener((obs, oldText, newText) -> usernameConfirmation());

        //Password confirmation
        tfPassword.textProperty().addListener((obs, oldText, newText) -> passwordConfirmation());
        tfPasswordConfirmation.textProperty().addListener((obs, oldText, newText) -> passwordConfirmation());

        tabPane.getTabs().remove(1, tabPane.getTabs().size());
    }


    /**
     * Set the person that is going to be modified
     *
     * @param   person      person
     */
    public void setPerson(Person person) {
        this.person = person;
        loadData();
    }


    /**
     * Add the specified ingredient to the allergies list
     */
    private void addAllergies() {
        if (!txAddAllergy.getText().isEmpty()){
            // Add the ingredient to the listview
            String ingredientName = txAddAllergy.getText().toLowerCase().trim();
            Ingredient ingredient = new Ingredient(ingredientName);
            lvAllergies.getItems().add(ingredient);

            // Reset the input field
            txAddAllergy.setText("");
        }

    }


    /**
     * Remove the selected allergies from the list
     */
    private void removeSelectedAllergies() {
        if(!lvAllergies.getSelectionModel().isEmpty()) {
            lvAllergies.getItems().removeAll(lvAllergies.getSelectionModel().getSelectedItems());
            lvAllergies.getSelectionModel().clearSelection();

        } else if(lvAllergies.getItems().isEmpty()){
            showErrorDialog("Non ci sono allergie nella lista");
        } else{
            showErrorDialog("Non ci sono allergie selezionate");
        }
    }


    /**
     * Add the specified ingredient to the intolerances list
     */
    private void addIntolerances() {
        if (!txAddIntolerances.getText().isEmpty()){
            String ingredientName = txAddIntolerances.getText().toLowerCase().trim();
            Ingredient ingredient = new Ingredient(ingredientName);
            lvIntolerances.getItems().add(ingredient);
            txAddIntolerances.setText("");
        }
    }


    /**
     * Remove the selected intolerances from the list
     */
    private void removeSelectedIntolerances() {
        if(!lvIntolerances.getSelectionModel().isEmpty()) {
            lvIntolerances.getItems().removeAll(lvIntolerances.getSelectionModel().getSelectedItems());
            lvIntolerances.getSelectionModel().clearSelection();

        } else if(lvIntolerances.getItems().isEmpty()){
            showErrorDialog("Non ci sono intolleranze nella lista");
        } else{
            showErrorDialog("Non ci sono intolleranze selezionate");
        }
    }


    /**
     * Username confirm check
     */
    private void usernameConfirmation(){
        if (tfUsername.getText().isEmpty()){
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
     * Password confirm check
     */
    private void passwordConfirmation(){
        if (tfPassword.getText().isEmpty()){
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
     * Load the person data into the corresponding fields
     */
    private void loadData() {
        tfFiscalCode.setText(person.getFiscalCode());           // Fiscal code
        tfFirstName.setText(person.getFirstName());             // First name
        tfLastName.setText(person.getLastName());               // Last name
        tfAddress.setText(person.getAddress());                 // Address
        tfTelephone.setText(person.getTelephone());             // Telephone

        // Birth date
        dpBirthDate.setValue(new java.sql.Date(person.getBirthdate().getTime()).toLocalDate());

        // Allergies
        lvAllergies.getItems().setAll(person.getAllergies());

        // Intolerances
        lvIntolerances.getItems().setAll(person.getIntolerances());

        // Differentiation based on person type
        switch (PersonType.getPersonType(person)) {
            case CHILD:
                imagePersonType.setImage(new Image("/images/baby.png"));
                tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntolerances, tabContacts);

                try {
                    for (GuiParent item : tableParents.getItems()) {
                        if (((Child) person).getParents().contains(item.getModel()))
                            item.setSelected(true);
                    }

                    for (GuiPediatrist item : tablePediatrist.getItems()) {
                        if (((Child) person).getPediatrist().equals(item.getModel()))
                            item.setSelected(true);
                    }

                    for (GuiContact item : tableContacts.getItems()) {
                        if (((Child) person).getContacts().contains(item.getModel()))
                            item.setSelected(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case CONTACT:
                imagePersonType.setImage(new Image("/images/grandparents.png"));
                break;

            case PARENT:
                imagePersonType.setImage(new Image("/images/family.png"));
                break;

            case PEDIATRIST:
                imagePersonType.setImage(new Image("/images/doctor.png"));
                tabPane.getTabs().addAll(tabAllergies, tabIntolerances);
                break;

            case STAFF:
                imagePersonType.setImage(new Image("/images/secretary.png"));
                tabPane.getTabs().addAll(tabAllergies, tabIntolerances, tabLoginData);

                tfUsername.setText(((Staff) person).getUsername());
                tfUsernameConfirmation.setText(((Staff) person).getUsername());
                tfPassword.setText(((Staff) person).getPassword());
                tfPasswordConfirmation.setText(((Staff) person).getPassword());
                break;
        }
    }


    /**
     * Read the new data and send the update request to the server
     */
    private void updatePerson() {
        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // Basic data
        person.setFirstName(tfFirstName.getText());
        person.setLastName(tfLastName.getText());
        person.setBirthdate(Date.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        person.setAddress(tfAddress.getText());
        person.setTelephone(tfTelephone.getText());

        person.getAllergies().clear();
        person.getIntolerances().clear();

        // Specific class data
        switch (PersonType.getPersonType(person)) {
            case CHILD:
                ((Child)person).setParents(TableUtils.getSelectedItems(tableParents));      // Parents
                ((Child)person).setContacts(TableUtils.getSelectedItems(tableContacts));    // Contacts
                person.addAllergies(lvAllergies.getItems());                                // Allergies
                person.addIntolerances(lvIntolerances.getItems());                          // Intolerances

                // Pediatrist
                List<Pediatrist> selectedPediatrists = TableUtils.getSelectedItems(tablePediatrist);

                if (selectedPediatrists.size() > 1) {
                    showErrorDialog("E' possibile selezionare solo un pediatra");
                    return;
                }

                if (selectedPediatrists.size() >= 1){
                    ((Child)person).setPediatrist(selectedPediatrists.get(0));
                }

                break;

            case STAFF:
                ((Staff) person).setUsername(tfUsername.getText());
                ((Staff) person).setPassword(tfPassword.getText());
                person.addAllergies(lvAllergies.getItems());
                person.addIntolerances(lvIntolerances.getItems());
                break;
        }


        // Check data
        try {
            person.checkDataValidity();
        } catch (InvalidFieldException e) {
            showErrorDialog(e.getMessage());
            return;
        }


        // Update person
        boolean updateResult = connectionManager.getClient().update(person);


        // Go back to the people list
        if (updateResult) {
            // Information dialog
            showInformationDialog("I dati sono stati aggiornati");

            // Go back to the people list page
            goBack();
        } else {
            showErrorDialog("Salvataggio non riuscito");
        }
    }


    /**
     * Go back to the main anagraphic page
     */
    public void goBack() {
        setCenterFXML((BorderPane)paneRoot.getParent(), "/views/showPerson.fxml");
    }

}