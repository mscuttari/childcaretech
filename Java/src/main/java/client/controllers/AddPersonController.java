package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import main.java.client.ParentTable;


import java.net.URL;
import java.util.ResourceBundle;

public class AddPersonController implements Initializable {

    @FXML private MenuButton personType;
    @FXML private Pane parentsPane;

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

    //@FXML private VBox parentsBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tabPane.getTabs().remove(tabLoginData);

        /*
        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        //Parents tab
        List<Parent> parents = connectionManager.getClient().getParents();
        for(Parent current : parents){
            CheckBox checkBox = new CheckBox(current.toString());
            parentsBox.getChildren().add(checkBox);
        }
        */

        ObservableList<ParentTable> parents = FXCollections.observableArrayList();

        parents.add( new ParentTable( "Mario", "Rossi", false));
        parents.add( new ParentTable( "Maria", "Neri", false));
        parents.add( new ParentTable( "Luca", "Grigi", false));
        for (int i = 0; i < 100; i++) parents.add(new ParentTable("nome"+(i+3), "Cognome"+(i+3), false));

        TableView<ParentTable> table = new TableView<ParentTable>();
        table.setEditable(true);


        TableColumn<ParentTable,Boolean> c1 = new TableColumn<ParentTable,Boolean>("Genitore");
        c1.setCellValueFactory(new PropertyValueFactory<ParentTable,Boolean>("check"));
        c1.setCellFactory(column -> new CheckBoxTableCell());
        table.getColumns().add(c1);

        TableColumn<ParentTable,String> c2 = new TableColumn<ParentTable,String>("Nome");
        c2.setCellValueFactory(new PropertyValueFactory<ParentTable,String>("name"));
        table.getColumns().add(c2);

        TableColumn<ParentTable,String> c3 = new TableColumn<ParentTable,String>("Cognome");
        c3.setCellValueFactory(new PropertyValueFactory<ParentTable,String>("surname"));
        table.getColumns().add(c3);

        table.setItems(parents);

        parentsPane.getChildren().addAll(table);
        table.prefWidthProperty().bind(parentsPane.widthProperty());
        table.prefHeightProperty().bind(parentsPane.heightProperty());
    }

    public void childrenChoice() {
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        children.setVisible(true);
        personType.setText("Bambino");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabParents, tabPediatrist, tabAllergies, tabIntollerances, tabContacts);
    }

    public void parentsChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        parents.setVisible(true);
        personType.setText("Genitore");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
    }

    public void responsibleChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        responsible.setVisible(true);
        personType.setText("Personale");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabAllergies, tabIntollerances, tabContacts, tabLoginData);

    }

    public void contactsChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        contacts.setVisible(true);
        personType.setText("Contatto");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);

    }

    public void pediatristChoice(){
        for (Node current : stackPane.getChildren()) {
            current.setVisible(false);
        }
        pediatrist.setVisible(true);
        personType.setText("Pediatra");

        int size = tabPane.getTabs().size();
        tabPane.getTabs().remove(1, size);
        tabPane.getTabs().addAll(tabAllergies, tabIntollerances);

    }
}