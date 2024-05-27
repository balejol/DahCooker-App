package com.example.foodapplication;

public class IngredientItem {
    private String id;
    private String name;
    private String quantity;
    private String measurement;

    public IngredientItem() {
        // Default constructor required for calls to DataSnapshot.getValue(IngredientItem.class)
    }

    public IngredientItem(String id, String name, String quantity, String measurement) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
