package main.java.models;

public enum DishType {

    PRIMO("Primo"),
    SECONDO("Secondo"),
    CONTORNO("Contorno"),
    DOLCE("Dolce");

    private String name;

    DishType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}