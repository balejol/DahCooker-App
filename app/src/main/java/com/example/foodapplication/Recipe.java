package com.example.foodapplication;

import android.graphics.Bitmap;
import android.net.Uri;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Recipe
{
    private int id;
    private String recipeName;
    private List<Ingredient> Ingredients;
    private String preparation;
    private String notes;
    private Bitmap image;
    private boolean isFavorite = false;

    public Recipe()
    {
        recipeName = "";
        Ingredients = new ArrayList<Ingredient>();
    }

    public String GetRecipeName(){return recipeName;}
    public Bitmap GetRecipeImage(){return image;}
    public String GetRecipePreparation(){return preparation;}
    public String GetRecipeNotes(){return notes;}
    public Boolean IsFavorite(){return isFavorite;}
    public Ingredient GetIngredient(int i){return Ingredients.get(i);}
    public int GetAmountOfIngredients(){return Ingredients.size();}

    public void IsFavorite(Boolean isFav)
    {
        isFavorite = isFav;
    }

    public void AddRecipe(String name, List<Ingredient> ingr, String prep, String note, Bitmap img)
    {
        recipeName = name;

        for(int i = 0; i < ingr.size(); i++)
        {
            Ingredients.add(ingr.get(i));
        }

        preparation = prep;
        notes = note;
        image = img;
    }

    public void ChangeRecipe(String name, List<Ingredient> ingr, String prep, String note, Bitmap img)
    {
        recipeName = name;

        Ingredients.clear();
        for(int i = 0; i < ingr.size(); i++)
        {
            Ingredients.add(ingr.get(i));
        }

        preparation = prep;
        notes = note;
        image = img;
    }
}
