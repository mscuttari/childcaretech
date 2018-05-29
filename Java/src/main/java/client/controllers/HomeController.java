package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.LogUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends AbstractController implements Initializable {

    // Debug
    private static final String TAG = "HomeController";

    @FXML private BorderPane borderPane;
    @FXML private Button buttonAnagraphic;
    @FXML private Button buttonCanteen;
    @FXML private Button buttonTrip;
    @FXML private ImageView imageLogout;
    @FXML private Label staffUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Anagraphic
        buttonAnagraphic.setOnAction(event -> showAnagraphic());
        buttonAnagraphic.setOnMouseEntered(event -> borderPane.getScene().setCursor(Cursor.HAND));
        buttonAnagraphic.setOnMouseExited(event -> borderPane.getScene().setCursor(Cursor.DEFAULT));

        // Canteen
        buttonCanteen.setOnAction(event -> showCanteen());
        buttonCanteen.setOnMouseEntered(event -> borderPane.getScene().setCursor(Cursor.HAND));
        buttonCanteen.setOnMouseExited(event -> borderPane.getScene().setCursor(Cursor.DEFAULT));

        // Trip
        buttonTrip.setOnAction(event -> showTrip());
        buttonTrip.setOnMouseEntered(event -> borderPane.getScene().setCursor(Cursor.HAND));
        buttonTrip.setOnMouseExited(event -> borderPane.getScene().setCursor(Cursor.DEFAULT));

        // Set initial panel
        setCenterFXML(borderPane, "/views/anagraphic.fxml");

        // Logout
        imageLogout.setOnMouseClicked(event -> logout());
        imageLogout.setOnMouseEntered(event -> borderPane.getScene().setCursor(Cursor.HAND));
        imageLogout.setOnMouseExited(event -> borderPane.getScene().setCursor(Cursor.DEFAULT));
        Tooltip.install(imageLogout, new Tooltip("Logout"));
    }


    /**
     * Show anagraphic page
     */
    private void showAnagraphic(){
        setCenterFXML(borderPane, "/views/anagraphic.fxml");
    }


    /**
     * Show canteen page
     */
    private void showCanteen(){
        setCenterFXML(borderPane, "/views/canteen.fxml");
    }


    /**
     * Show trips page
     */
    private void showTrip(){
        setCenterFXML(borderPane, "/views/trip.fxml");

    }


    /**
     * Logout
     */
    private void logout(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));

            Stage stage = new Stage();
            stage.setTitle("Child Care Tech");
            stage.setScene(new Scene(root, 700, 350));
            stage.setMinWidth(700);
            stage.setMinHeight(350);
            stage.show();

            borderPane.getScene().getWindow().hide();

        } catch (IOException e) {
            LogUtils.e(TAG, e.getMessage());
        }
    }


    /**
     * Set the currently logged in staff username
     *
     * @param   username    username
     */
    public void setStaffUsername(String username){
        staffUsername.setText(username);
    }

}
