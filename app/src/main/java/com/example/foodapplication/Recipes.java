package com.example.foodapplication;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Recipes
{
    ArrayList<Recipe> Recipes;

    public Recipes()
    {
        Recipes = new ArrayList<>();
    }

    public void AddRecipe(Recipe rec)
    {
        Recipes.add(rec);
    }
    public int GetN(){return Recipes.size();}

    public Recipe GetRecipe(int i) { return Recipes.get(i);}
    public String GetName(int i)
    {
        return Recipes.get(i).GetRecipeName();
    }

    public void Remove (int i)
    {
        Recipes.remove(i);
    }
}
