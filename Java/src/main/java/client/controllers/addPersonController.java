package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class addPersonController implements Initializable {

    @FXML
    private MenuButton personChoice;
    @FXML private StackPane stackPane;
    @FXML private ImageView children;
    @FXML private ImageView parents;
    @FXML private ImageView responsible;
    @FXML private ImageView contacts;
    @FXML private ImageView pediatrist;
    @FXML private Tab tabParents;
    @FXML private Tab tabPediatrist;
    @FXML private Tab tabAllergies;
    @FXML private Tab tabIntollerances;
    @FXML private Tab tabContacts;
    @FXML private Tab tabLoginData;
    @FXML private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        personChoice.setText("Bambino");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntollerances, tabContacts);
    }

    public void childrenChoice() {
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        children.setVisible(true);
        personChoice.setText("Bambino");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntollerances, tabContacts);

    }

    public void parentsChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        parents.setVisible(true);
        personChoice.setText("Genitore");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
    }

    public void responsibleChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        responsible.setVisible(true);
        personChoice.setText("Personale");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabAllergies, tabIntollerances, tabContacts, tabLoginData);

    }

    public void contactsChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        contacts.setVisible(true);
        personChoice.setText("Contatto");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);

    }

    public void pediatristChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        pediatrist.setVisible(true);
        personChoice.setText("Pediatra");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabAllergies, tabIntollerances);

    }
}