package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
public class RecipesPage extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Intent ingredientsIntent = new Intent(RecipesPage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                return true;
            case R.id.item3:
                Intent recipeIntent = new Intent(RecipesPage.this, RecipesPage.class);
                startActivity(recipeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };



    Button _button;
    Button backButton;

    //Receptų sąrašas
    public static Recipes RecipesList = new Recipes();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        _button = (Button) findViewById(R.id.button);
        backButton = (Button) findViewById(R.id.backButtonRecipes);

        LinearLayout recipeLayout = (LinearLayout) findViewById(R.id.recipeListLayout);

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

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        _button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), AddRecipePage.class);
                //intent.putExtra("Data", "Please enter information about a recipe");
                startActivity(intent);
            }
        });
    }
}