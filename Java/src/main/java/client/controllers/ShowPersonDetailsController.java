package main.java.client.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import main.java.client.gui.GuiIngredient;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShowPersonDetailsController extends AbstractController implements Initializable {

    private Person person;

    @FXML private TabPane tabPane;
    @FXML private Pane showPersonDetailsPane;
    @FXML private ImageView goBackImage;

    @FXML private ImageView imagePersonType;
    @FXML private HBox boxChildCode;
    @FXML private Label labelChildCode;
    @FXML private Label labelFiscalCode;
    @FXML private Label labelFirstName;
    @FXML private Label labelLastName;
    @FXML private Label labelBirthDate;
    @FXML private HBox boxAddress;
    @FXML private Label labelAddress;
    @FXML private HBox boxTelephone;
    @FXML private Label labelTelephone;

    @FXML Tab tabRelations;
    @FXML private Label labelParents;
    @FXML private Label labelContacts;
    @FXML private Label labelPediatrist;

    @FXML Tab tabChildren;
    @FXML private Label labelChildren;

    @FXML Tab tabIngredients;
    @FXML private ListView<GuiIngredient> lvAllergies;
    @FXML private ListView<GuiIngredient> lvIntolerances;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Go back button
        goBackImage.setOnMouseEntered(event -> showPersonDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showPersonDetailsPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());
        Tooltip.install(goBackImage, new Tooltip("Indietro"));
    }


    /**
     * Load the person data into the corresponding fields
     */
    private void loadData() {
        String separator;

        labelFiscalCode.setText(person.getFiscalCode());        // Fiscal code
        labelFirstName.setText(person.getFirstName());          // First name
        labelLastName.setText(person.getLastName());            // Last name

        // Birth date
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        labelBirthDate.setText(formatter.format(person.getBirthdate()));

        // Address
        boxAddress.setVisible(person.getAddress() != null);
        labelAddress.setText(person.getAddress());

        // Telephone
        boxTelephone.setVisible(person.getTelephone() != null);
        labelTelephone.setText(person.getTelephone());

        // Allergies
        ObservableList<GuiIngredient> allergies = TableUtils.getGuiModelsList(person.getAllergies());
        allergies.sorted(Comparator.comparing(o -> o.getModel().getName()));
        lvAllergies.setItems(allergies);

        // Intolerances
        ObservableList<GuiIngredient> intolerances = TableUtils.getGuiModelsList(person.getIntolerances());
        intolerances.sorted(Comparator.comparing(o -> o.getModel().getName()));
        lvIntolerances.setItems(intolerances);

        tabPane.getTabs().removeAll(tabRelations, tabChildren, tabIngredients);
        boxChildCode.setVisible(false);

        // Differentiation based on person type
        switch (PersonType.getPersonType(person)) {
            case CHILD:
                imagePersonType.setImage(new Image("/images/baby.png"));
                boxChildCode.setVisible(true);

                // Child id
                labelChildCode.setText(String.valueOf(((Child) person).getId()));

                // Parents
                if (!((Child) person).getParents().isEmpty()) {
                    separator = "";
                    for (Parent current : ((Child) person).getParents()) {
                        labelParents.setText(labelParents.getText() + separator + current);
                        separator = "\n";
                    }
                } else {
                    labelParents.setText("-");
                }

                // Pediatrist
                labelPediatrist.setText(((Child) person).getPediatrist().toString());

                // Contacts
                if (!((Child) person).getContacts().isEmpty()) {
                    separator = "";
                    for (Contact current : ((Child) person).getContacts()) {
                        labelContacts.setText(labelContacts.getText() + separator + current.toString());
                        separator = "\n";
                    }
                } else {
                    labelContacts.setText("-");
                }

                tabPane.getTabs().addAll(tabRelations, tabIngredients);
                break;

            case CONTACT:
                imagePersonType.setImage(new Image("/images/grandparents.png"));

                // Children
                if (!((Contact) person).getChildren().isEmpty()) {
                    separator = "";
                    for (Child current : ((Contact) person).getChildren()) {
                        labelChildren.setText(labelChildren.getText() + separator + current);
                        separator = "\n";
                    }
                } else {
                    labelChildren.setText("-");
                }
                tabPane.getTabs().addAll(tabChildren);
                break;

            case PARENT:
                imagePersonType.setImage(new Image("/images/family.png"));

                // Children
                if (!((Parent) person).getChildren().isEmpty()) {
                    separator = "";
                    for (Child current : ((Parent) person).getChildren()) {
                        labelChildren.setText(labelChildren.getText() + separator + current);
                        separator = "\n";
                    }
                } else {
                    labelChildren.setText("-");
                }
                tabPane.getTabs().addAll(tabChildren);
                break;

            case PEDIATRIST:
                imagePersonType.setImage(new Image("/images/doctor.png"));
                break;

            case STAFF:
                imagePersonType.setImage(new Image("/images/secretary.png"));
                tabPane.getTabs().add(tabIngredients);
                break;
        }

    }


    /**
     * Set the person that is going to be shown
     *
     * @param   person      person
     */
    public void setPerson(Person person) {
        this.person = person;
        loadData();
    }


    /**
     * Go back to the main anagraphic page
     */
    public void goBack() {
        setCenterFXML((BorderPane)showPersonDetailsPane.getParent(), "/views/showPerson.fxml");
    }

}