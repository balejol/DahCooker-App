package com.example.foodapplication;

import java.util.ArrayList;
import java.util.List;

public class Recipes {
    private List<Recipe> recipeList;

    public Recipes() {
        this.recipeList = new ArrayList<>();
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void addRecipe(Recipe recipe) {
        recipeList.add(recipe);
    }

    public Recipe getRecipe(int index) {
        return recipeList.get(index);
    }

    public int size() {
        return recipeList.size();
    }
}
