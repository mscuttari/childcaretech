package main.java.client.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import main.java.LogUtils;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.GuiPerson;
import main.java.client.layout.MyButtonTableCell;
import main.java.client.utils.TableUtils;
import main.java.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowPersonController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "ShowPersonController";

    @FXML private Pane showPersonPane;
    @FXML private ImageView goBackImage;

    @FXML private TableView<GuiPerson> tablePeople;
    @FXML private TableColumn<GuiPerson, String> columnPeopleFirstName;
    @FXML private TableColumn<GuiPerson, String> columnPeopleLastName;
    @FXML private TableColumn<GuiPerson, String> columnPeopleFiscalCode;
    @FXML private TableColumn<GuiPerson, String> columnPeopleType;
    @FXML private TableColumn<GuiPerson, Void> columnPeopleEdit;
    @FXML private TableColumn<GuiPerson, Void> columnPeopleDelete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Go back button
        goBackImage.setOnMouseEntered(event -> showPersonPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showPersonPane.getScene().setCursor(Cursor.DEFAULT));
        goBackImage.setOnMouseClicked(event -> goBack());

        // Connection
        ConnectionManager connectionManager = ConnectionManager.getInstance();

        // People table
        List<Person> people = new ArrayList<>();
        people.addAll(connectionManager.getClient().getPeople());
        @SuppressWarnings("unchecked") ObservableList<GuiPerson> peopleData = TableUtils.getGuiModelsList(people);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnPeopleType.setCellValueFactory(param -> new SimpleStringProperty(PersonType.getPersonType(param.getValue().getModel()).toString()));

        columnPeopleEdit.setCellFactory(param -> new MyButtonTableCell<>("Modifica", new Callback<GuiPerson, Object>() {
            @Override
            public Object call(GuiPerson param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updatePerson.fxml"));

                    Pane updatePersonPane = loader.load();
                    UpdatePersonController controller = loader.getController();
                    controller.setPerson(param.getModel());

                    BorderPane homePane = (BorderPane) showPersonPane.getParent();
                    homePane.setCenter(updatePersonPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

        columnPeopleDelete.setCellFactory(param -> new MyButtonTableCell<>("Elimina", param1 -> {
            connectionManager.getClient().delete(param1.getModel());
            List<Person> newPeople = connectionManager.getClient().getPeople();
            @SuppressWarnings("unchecked") ObservableList<GuiPerson> newPeopleData = TableUtils.getGuiModelsList(newPeople);

            tablePeople.setItems(newPeopleData);
            return null;
        }));

        tablePeople.setEditable(true);
        tablePeople.setItems(peopleData);
    }


    /**
     * Go back to the main anagraphic page
     */
    private void goBack() {
        setCenterFXML((BorderPane)showPersonPane.getParent(), "/views/anagraphic.fxml");
    }

}
