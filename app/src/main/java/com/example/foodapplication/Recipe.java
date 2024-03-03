package com.example.foodapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private String recipeName;
    private ArrayList<String> ingredients;
    public Recipe()
    {
        recipeName = "";
        ingredients = new ArrayList<String>();
    }

    public String GetRecipeName(){return recipeName;}
    public String GetIngredient(int i){return ingredients.get(i);}
    public int GetAmountOfIngredients(){return ingredients.size();}

    public void AddRecipe(String name, ArrayList<String> ingr)
    {
        recipeName = name;

        for(int i = 0; i < ingr.size(); i++)
        {
            ingredients.add(ingr.get(i));
        }
    }
}
