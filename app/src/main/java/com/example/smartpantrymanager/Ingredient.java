package com.example.smartpantrymanager;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private String unit;
    private String expiryDate;
    public Ingredient() {
    }

    public Ingredient(int id, String name, int quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public Ingredient(String name, int quantity, String unit, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpiryDate(){
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate){
        this.expiryDate = expiryDate;
    }
}