package main.java.models;

public enum PersonType {

    CHILD("Bambino"),
    CONTACT("Contatto"),
    PARENT("Genitore"),
    PEDIATRIST("Pediatra"),
    STAFF("Staff");

    private String name;

    PersonType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
