package main.java.client.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class addPerson {

    ObservableList<String> addPersonList = FXCollections.observableArrayList("Bambino", "Pediatra");

    @FXML private ChoiceBox addPersonBox;
    @FXML private TextField txtCodBambino;
    @FXML private ImageView returnBack;

    @FXML
    private void initialize(){
        addPersonBox.setValue(("Bambino"));
        addPersonBox.setItems(addPersonList);
    }

    public void keyReleaseProperty(){
        boolean isDisable = addPersonBox.getValue().equals(("Pediatra"));
        txtCodBambino.setDisable(isDisable);
        if(txtCodBambino.isDisable()) {
            txtCodBambino.setPromptText("NON ASSEGNATO");
            txtCodBambino.setText("");
        }else
            txtCodBambino.setPromptText("");
    }

    public void returnBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/anagraphic.fxml"));
            Stage actual = (Stage) returnBack.getScene().getWindow();
            actual.setScene(new Scene(root, 600, 400));
            actual.show();
        } catch (IOException e) {
            System.out.println("Errore nel tornare indietro");
        }
    }

}
