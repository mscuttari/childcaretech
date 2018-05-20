package main.java.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractController {

    /**
     * Show error dialog
     *
     * @param   message     error message
     */
    protected static void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    /**
     * Set center view of a BorderPane
     *
     * @param   borderPane      border pane to be populated
     * @param   resource        FXML view to be loaded
     */
    protected void setCenterFXML(BorderPane borderPane, String resource) {
        try {
            URL url = this.getClass().getResource(resource);
            borderPane.setCenter(FXMLLoader.load(url));
        } catch (IOException e) {
            LogUtils.e(this.getClass().getSimpleName(), e.getMessage());
            e.printStackTrace();
        }
    }

}
