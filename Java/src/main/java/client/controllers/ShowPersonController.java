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
import java.util.Optional;
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
    @FXML private TableColumn<GuiPerson, Void> columnPeopleShowDetails;
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
        people.addAll(connectionManager.getClient().getChildren());
        people.addAll(connectionManager.getClient().getStaff());
        people.addAll(connectionManager.getClient().getContacts());
        people.addAll(connectionManager.getClient().getParents());
        people.addAll(connectionManager.getClient().getPediatrists());
        @SuppressWarnings("unchecked") ObservableList<GuiPerson> peopleData = TableUtils.getGuiModelsList(people);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));
        columnPeopleType.setCellValueFactory(param -> new SimpleStringProperty(PersonType.getPersonType(param.getValue().getModel()).toString()));

        columnPeopleShowDetails.setCellFactory(param -> new MyButtonTableCell<>("Visualizza dettagli", new Callback<GuiPerson, Object>() {
            @Override
            public Object call(GuiPerson param) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showPersonDetails.fxml"));

                    Pane showPersonDetailsPane = loader.load();
                    ShowPersonDetailsController controller = loader.getController();
                    controller.setPerson(param.getModel());

                    BorderPane homePane = (BorderPane) showPersonPane.getParent();
                    homePane.setCenter(showPersonDetailsPane);

                } catch (IOException e) {
                    LogUtils.e(TAG, e.getMessage());
                }

                return null;
            }
        }));

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
            if (param1.getModel() instanceof Staff) {

                if (((Staff) param1.getModel()).getUsername().equals(ConnectionManager.getInstance().getClient().getUsername())) {
                    showErrorDialog("Non è possibile eliminare sè stessi");
                }

            } else if (param1.getModel().isDeletable()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "Vuoi davvero eliminare la persona?\n" +
                        "(la procedura è irreversibile)",
                        ButtonType.NO, ButtonType.YES);

                alert.setTitle("Conferma operazione");
                alert.setHeaderText(null);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.YES) {
                    deleteData(connectionManager, param1);
                }

            } else {
                showErrorDialog("Questa persona non può essere eliminata");
            }

            return null;
        }));

        tablePeople.setEditable(true);
        tablePeople.setItems(peopleData);
    }


    /**
     * Delete data and update table data
     */
    private void deleteData(ConnectionManager connectionManager, GuiPerson param) {
        connectionManager.getClient().delete(param.getModel());
        List<Person> newPeople = new ArrayList<>();
        newPeople.addAll(connectionManager.getClient().getChildren());
        newPeople.addAll(connectionManager.getClient().getStaff());
        newPeople.addAll(connectionManager.getClient().getContacts());
        newPeople.addAll(connectionManager.getClient().getParents());
        newPeople.addAll(connectionManager.getClient().getPediatrists());
        @SuppressWarnings("unchecked") ObservableList<GuiPerson> newPeopleData = TableUtils.getGuiModelsList(newPeople);

        tablePeople.setItems(newPeopleData);
    }


    /**
     * Go back to the main anagraphic page
     */
    private void goBack() {
        setCenterFXML((BorderPane)showPersonPane.getParent(), "/views/anagraphic.fxml");
    }

}
