package main.java.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Client extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));

        Scene scene = new Scene(root, 700, 350);
        scene.getStylesheets().add(getClass().getResource("/bootstrap3.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Child Care Tech");
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(350);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}