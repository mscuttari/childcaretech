package main.java.client.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.models.PersonType;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPersonController implements Initializable {

    @FXML private ComboBox<PersonType> cbPersonType;
    @FXML private ImageView imagePersonType;
    @FXML private Tab tabParents;
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
    }

}