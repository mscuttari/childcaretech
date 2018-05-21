package main.java.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML private ImageView goToLoginImage;
    @FXML private Label staffUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Left menu
        buttonAnagraphic.setOnAction(event -> showAnagraphic());    // Anagraphic
        buttonCanteen.setOnAction(event -> showCanteen());          // Canteen
        buttonTrip.setOnAction(event -> showTrip());                // Trip

        // Set initial panel
        setCenterFXML(borderPane, "/views/anagraphic.fxml");

        // Logout
        goToLoginImage.setOnMouseClicked(event -> logout());
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

    public void setStaffUsername(String username){
        staffUsername.setText(username);
    }

}
