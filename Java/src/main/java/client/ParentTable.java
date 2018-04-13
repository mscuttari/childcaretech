package main.java.client;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParentTable {

    private StringProperty myName;
    private StringProperty mySurname;
    private BooleanProperty myCheck;

    public ParentTable(String name, String surname, boolean checked) {
        myName = new SimpleStringProperty(name);
        mySurname = new SimpleStringProperty(surname);
        myCheck = new SimpleBooleanProperty(checked);
    }

    public StringProperty nameProperty() { return myName; }

    public StringProperty surnameProperty() { return mySurname; }

    public BooleanProperty checkProperty() { return myCheck; }
}
