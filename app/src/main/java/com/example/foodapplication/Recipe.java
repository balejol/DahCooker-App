package com.example.foodapplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private String recipeName;
    private ArrayList<Ingredient> Ingredients;
    public Recipe()
    {
        recipeName = "";
        Ingredients = new ArrayList<Ingredient>();
    }

    public String GetRecipeName(){return recipeName;}
    public Ingredient GetIngredient(int i){return Ingredients.get(i);}
    public int GetAmountOfIngredients(){return Ingredients.size();}

    public void AddRecipe(String name, ArrayList<Ingredient> ingr)
    {
        recipeName = name;

        for(int i = 0; i < ingr.size(); i++)
        {
            Ingredients.add(ingr.get(i));
        }
    }
}
