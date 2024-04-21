package com.example.foodapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Ingredient
{
    private String name;
    private double amount;
    private String measurement;

    public Ingredient(String name, double amount, String measurement)
    {
        this.name = name;
        this.amount = amount;
        this.measurement = measurement;
    }
    public void PutName(String name) {this.name = name;}
    public void PutAmount(double amount){this.amount = amount;}
    public void PutMeasurement(String measure){measurement = measure;}
    public String GetName(){return name;}
    public double GetAmount(){return amount;}
    public String GetMeasurement(){return measurement;}
}
