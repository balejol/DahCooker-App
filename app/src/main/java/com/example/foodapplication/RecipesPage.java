package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
public class RecipesPage extends AppCompatActivity
{
    Button _button;

    //Receptų sąrašas
    public static Recipes RecipesList = new Recipes();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        _button = (Button) findViewById(R.id.button);

        LinearLayout recipeLayout = (LinearLayout) findViewById(R.id.linearLayout);

        for(int i = 0; i < RecipesList.GetN(); i++) {
            TextView recipeTitle = new TextView(this);
            recipeTitle.setText("RECIPE - " + RecipesList.GetName(i));
            recipeTitle.setGravity(Gravity.CENTER);
            recipeLayout.addView(recipeTitle);

            for (int j = 0; j < RecipesList.GetRecipe(i).GetAmountOfIngredients(); j++)
            {
                TextView ingredients = new TextView(this);
                ingredients.setText(RecipesList.GetRecipe(i).GetIngredient(j) + "\n");
                ingredients.setGravity(Gravity.LEFT);
                recipeLayout.addView(ingredients);
            }
        }

        _button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), AddRecipePage.class);
                intent.putExtra("Data", "Please enter information about a recipe");
                startActivity(intent);
            }
        });
    }
}