package main.java.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import main.java.LogUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public abstract class AbstractController {

    /**
     * Show warning dialog with question
     *
     * @param   message     message
     * @return  true for "Yes" answer; false for "No" answer
     */
    protected static boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attenzione");
        alert.setHeaderText(null);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.YES;
    }


    /**
     * Show information dialog
     *
     * @param   message     message
     */
    protected static void showInformationDialog(String message) {
        showInformationDialog("Informazioni", message);
    }


    /**
     * Show information dialog
     *
     * @param   title       title
     * @param   message     message
     */
    protected static void showInformationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    /**
     * Show error dialog
     *
     * @param   message     message
     */
    protected static void showErrorDialog(String message) {
        showErrorDialog("Errore", message);
    }


    /**
     * Show error dialog
     *
     * @param   title       title
     * @param   message     message
     */
    protected static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
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
