package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.exceptions.DatabaseException;
import main.java.models.Person;
import main.java.utils.HibernateUtil;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Michele");
        person.setSurname("Scuttari");
        person.setType("child");
        person.setFiscalCode("SCTMHL96T20E897F");

        try {
            person = HibernateUtil.getInstance().create(person);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(person);

        launch(args);
    }
}
