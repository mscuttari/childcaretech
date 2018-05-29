package main.java.models;

public enum DayOfTheWeek {

    MONDAY("Lunedì"),
    TUESDAY("Martedì"),
    WEDNESDAY("Mercoledì"),
    THURSDAY("Giovedì"),
    FRIDAY("Venerdì"),
    SATURDAY("Sabato"),
    SUNDAY("Domenica");

    private String name;

    DayOfTheWeek(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
