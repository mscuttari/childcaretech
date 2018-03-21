package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.exceptions.DatabaseException;
import main.java.models.Person;
import main.java.utils.HibernateUtil;

import java.util.List;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Person person = new Person();
        person.setName("Marco");
        person.setSurname("Di Marzo");
        person.setType("child");
        person.setFiscalCode("DMRMCA96T");

        try {
            person = HibernateUtil.getInstance().create(person);
        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(person);

        try {
            List<Person> people = HibernateUtil.getInstance().getAll(Person.class);

            for (Person p : people) {
                System.out.println(p);
            }

        } catch (DatabaseException e) {
            System.err.println(e.getMessage());
        }

        //launch(args);
    }
}
