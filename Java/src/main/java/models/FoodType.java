package main.java.models;

public enum FoodType {

    PRIMO("Primo"),
    SECONDO("Secondo"),
    CONTORNO("Contorno"),
    DOLCE("Dolce");

    private String name;

    FoodType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}