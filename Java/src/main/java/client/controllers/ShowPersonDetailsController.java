package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.models.*;

import java.net.URL;
import java.util.*;

public class ShowPersonDetailsController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowPersonDetailsController";

    private Person person;

    @FXML private TabPane tabPane;
    @FXML private Pane showPersonDetailsPane;
    @FXML private ImageView goBackImage;

    @FXML private ImageView imagePersonType;
    @FXML private VBox vboxChildCode;
    @FXML private Label labelChildCode;
    @FXML private Label labelFiscalCode;
    @FXML private Label labelFirstName;
    @FXML private Label labelLastName;
    @FXML private Label labelBirthDate;
    @FXML private Label labelAddress;
    @FXML private Label labelTelephone;

    @FXML Tab tabRelations;
    @FXML private Label labelParents;
    @FXML private Label labelContacts;
    @FXML private Label labelPediatrist;

    @FXML Tab tabIngredients;
    @FXML private TextArea textAreaAllergies;
    @FXML private TextArea textAreaIntolerances;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showPersonDetailsPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showPersonDetailsPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

    }

    /**
     * Load the person data into the corresponding fields
     */
    private void loadData() {
        labelFiscalCode.setText(person.getFiscalCode());                                            // Fiscal code
        labelFirstName.setText(person.getFirstName());                                              // First name
        labelLastName.setText(person.getLastName());                                                // Last name
        labelBirthDate.setText(new java.sql.Date(person.getBirthdate().getTime()).toString());      // Birth date
        labelAddress.setText(person.getAddress());                                                  // Address
        labelTelephone.setText(person.getTelephone());                                              // Telephone

        // Allergies
        for(Ingredient current: person.getAllergies()){
            textAreaAllergies.appendText(current.toString());
            textAreaAllergies.appendText("\n");
        }

        // Intolerances
        for(Ingredient current: person.getIntolerances()){
            textAreaIntolerances.appendText(current.toString() + "\n");
        }

        tabPane.getTabs().removeAll(tabRelations, tabIngredients);
        vboxChildCode.setVisible(false);

        // Differentiation based on person type
        switch (PersonType.getPersonType(person)) {
            case CHILD:
                imagePersonType.setImage(new Image("/images/baby.png"));
                vboxChildCode.setVisible(true);

                // Child id
                labelChildCode.setText(String.valueOf(((Child) person).getId()));

                // Parents
                for (Parent current : ((Child) person).getParents()) {
                    labelParents.setText(labelParents.getText() + current.toString() + "\n");
                }

                // Pediatrist
                labelPediatrist.setText(((Child) person).getPediatrist().toString());

                // Contacts
                for (Contact current : ((Child) person).getContacts()) {
                    labelContacts.setText(labelContacts.getText() + current.toString() + "\n");
                }

                tabPane.getTabs().addAll(tabRelations, tabIngredients);
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