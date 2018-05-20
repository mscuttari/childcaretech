package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import main.java.LogUtils;
import main.java.client.InvalidFieldException;
import main.java.client.connection.ConnectionManager;
import main.java.client.gui.*;
import main.java.client.utils.TableUtils;
import main.java.models.*;
import main.java.models.Menu;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowDetailsAlternativeMenuController implements Initializable {

    // Debug
    private static final String TAG = "ShowDetailsAlternativeMenuController";

    private AlternativeMenu alternativeMenu;

    @FXML private Pane showDetailsAlternativeMenuPane;
    @FXML private TextField tfMenuName;
    @FXML private ImageView goBackImage;

    @FXML private TableView<Dish> tableDish;
    @FXML private TableColumn<Dish, String> columnDishName;
    @FXML private TableColumn<Dish, String> columnDishType;

    @FXML private TableView<Person> tablePeople;
    @FXML private TableColumn<Person, String> columnPeopleFirstName;
    @FXML private TableColumn<Person, String> columnPeopleLastName;
    @FXML private TableColumn<Person, String> columnPeopleFiscalCode;

    public ShowDetailsAlternativeMenuController(AlternativeMenu alternativeMenu){
        this.alternativeMenu = alternativeMenu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // go back button cursor
        goBackImage.setOnMouseEntered(event -> showDetailsAlternativeMenuPane.getScene().setCursor(Cursor.HAND));
        goBackImage.setOnMouseExited(event -> showDetailsAlternativeMenuPane.getScene().setCursor(Cursor.DEFAULT));

        //go back image
        goBackImage.setOnMouseClicked(event -> goBack());

        //Menu name
        tfMenuName.setText(alternativeMenu.getName());

        // Dish table
        List<Dish> dishes = new ArrayList<>(alternativeMenu.getDishes());
        ObservableList<Dish> dishesData = FXCollections.observableArrayList(dishes);

        columnDishName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDishType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableDish.setEditable(false);
        tableDish.setItems(dishesData);

        // People table
        List<Person> people = new ArrayList<>(alternativeMenu.getPeople());
        ObservableList<Person> peopleData = FXCollections.observableArrayList(people);

        columnPeopleFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnPeopleLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnPeopleFiscalCode.setCellValueFactory(new PropertyValueFactory<>("fiscalCode"));

        tablePeople.setEditable(false);
        tablePeople.setItems(peopleData);

    }


    public void goBack() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showAlternativeMenu.fxml"));

            ShowAlternativeMenuController showAlternativeMenuController = new ShowAlternativeMenuController(alternativeMenu.getRegularMenu());
            loader.setController(showAlternativeMenuController);

            Pane showAlternativeMenuPane = loader.load();
            BorderPane homePane = (BorderPane) showDetailsAlternativeMenuPane.getParent();
            homePane.setCenter(showAlternativeMenuPane);

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }

    }
}