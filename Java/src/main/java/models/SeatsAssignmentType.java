package main.java.models;

public enum SeatsAssignmentType {

    AUTOMATIC("Automatico"),
    MANUAL("Manuale"),
    UNNECESSARY("Non necessario");

    private String name;

    SeatsAssignmentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}