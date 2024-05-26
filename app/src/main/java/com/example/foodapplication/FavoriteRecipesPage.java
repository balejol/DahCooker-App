package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class FavoriteRecipesPage extends AppCompatActivity
{
    private MediaPlayer mediaPlayer;
    private MediaPlayer cancelSFX;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        switch (item.getItemId()) {
            // iš Favourite recipes į Main
            case R.id.item1:
                Intent mainIntent = new Intent(FavoriteRecipesPage.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Favourite recipes į Ingredients
            case R.id.item2:
                Intent ingredientsIntent = new Intent(FavoriteRecipesPage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Favourite recipes į Add recipe
            case R.id.item3:
                Intent recipeIntent = new Intent(FavoriteRecipesPage.this, AddRecipePage.class);
                startActivity(recipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Favourite recipes į My recipes
            case R.id.item4:
                Intent myrecipeIntent = new Intent(FavoriteRecipesPage.this, RecipesPage.class);
                startActivity(myrecipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Favourite recipes į Settings
            case R.id.item6:
                Intent settingsIntent = new Intent(FavoriteRecipesPage.this, SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    Button backButton;
    TextView tw_emptyInformation;
    //String previousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes_page);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
        cancelSFX = MediaPlayer.create(this, R.raw.cancleclickf);

        backButton = (Button) findViewById(R.id.backButtonRecipes);
        tw_emptyInformation = findViewById(R.id.oops);
        LinearLayout recipeLayout = (LinearLayout) findViewById(R.id.recipeListLayout);
        CheckIfEmpty();

        //previousPage = getIntent().getStringExtra("PreviousPage");
        //Toast.makeText(FavoriteRecipesPage.this, "from - " + previousPage, Toast.LENGTH_LONG).show();

        for(int i = 0; i < RecipesPage.RecipesList.GetN(); i++)
        {
            if(RecipesPage.RecipesList.GetRecipe(i).IsFavorite())
            {
                LinearLayout oneRecipeLayout = new LinearLayout(getBaseContext());
                oneRecipeLayout.setOrientation(LinearLayout.VERTICAL);
                oneRecipeLayout.setMinimumWidth(400);
                oneRecipeLayout.setGravity(Gravity.CENTER);
                oneRecipeLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
                oneRecipeLayout.setBackgroundResource(R.drawable.box_shape_yellow);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 10, 0, 10);
                oneRecipeLayout.setLayoutParams(params);
                oneRecipeLayout.setPadding(50, 50, 50, 50);

                //recipe name
                TextView tw_recipeName = new TextView(getBaseContext());
                tw_recipeName.setText(RecipesPage.RecipesList.GetName(i));
                tw_recipeName.setTextSize(25);
                tw_recipeName.setTextColor(Color.BLACK);
                tw_recipeName.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                tw_recipeName.setGravity(Gravity.CENTER);
                oneRecipeLayout.addView(tw_recipeName);
                LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                nameParams.setMargins(0, 0, 0, 30);
                tw_recipeName.setLayoutParams(nameParams);

                int id = i;

                //Nuotrauka
                ImageView recipeImage = new ImageView(getBaseContext());
                Drawable recipeImageDrawable = new BitmapDrawable(getResources(), RecipesPage.RecipesList.GetRecipe(i).GetRecipeImage());
                recipeImage.setImageDrawable(recipeImageDrawable);
                recipeImage.setOnClickListener(view -> ShowRecipeDetail(id));
                recipeImage.setAdjustViewBounds(true);
                oneRecipeLayout.addView(recipeImage);

                //mygtukų layout
                LinearLayout oneRecipeButtonsLayout = new LinearLayout(getBaseContext());
                oneRecipeButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
                oneRecipeButtonsLayout.setGravity(Gravity.CENTER);
                oneRecipeButtonsLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
                LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                buttonsParams.setMargins(0, 30, 0, 0);
                buttonsParams.gravity = Gravity.CENTER_HORIZONTAL;
                oneRecipeButtonsLayout.setLayoutParams(buttonsParams);

                //favourite button
                Button ingFavoriteButton = new Button(getBaseContext());
                ingFavoriteButton.setOnClickListener(view -> UnfavoriteARecipe(ingFavoriteButton, id));
                ingFavoriteButton.setBackgroundColor(Color.rgb(255, 127, 127));
                ingFavoriteButton.setText("FAVORITE");
                ingFavoriteButton.setTextColor(Color.WHITE);
                ingFavoriteButton.setGravity(Gravity.CENTER);
                //ingFavoriteButton.setBackgroundResource(R.drawable.button_color);
                ingFavoriteButton.setBackgroundColor(Color.RED);
                oneRecipeButtonsLayout.addView(ingFavoriteButton);

                oneRecipeLayout.addView(oneRecipeButtonsLayout);
                recipeLayout.addView(oneRecipeLayout);
            }
        }

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                Intent intent = new Intent(getBaseContext(), RecipesPage.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //leidzia pašalinti mėgstamą receptą iš mėgstamiausių puslapio
    private void UnfavoriteARecipe(final Button btt, final int id)
    {
        //paleidzia sfx
        if (cancelSFX != null) {
            cancelSFX.start();
        }
        if(RecipesPage.RecipesList.GetRecipe(id).IsFavorite())
        {
            RecipesPage.RecipesList.GetRecipe(id).IsFavorite(false);
            LinearLayout recipes = (LinearLayout) findViewById((R.id.recipeListLayout));
            LinearLayout recipeLine = (LinearLayout) btt.getParent().getParent();
            recipes.removeView(recipeLine);
        }
        else
        {
            Toast.makeText(FavoriteRecipesPage.this, "This recipe is already unfavorited", Toast.LENGTH_LONG).show();
        }
    }

    private void CheckIfEmpty()
    {
        if(RecipesPage.RecipesList.GetN() == 0)
        {
            tw_emptyInformation.setVisibility(View.VISIBLE);
        }
        else
        {
            tw_emptyInformation.setVisibility(View.GONE);
        }
    }

    private void ShowRecipeDetail(final int id)
    {
        Intent RecipeDetailIntent = new Intent (FavoriteRecipesPage.this, RecipeInformationPage.class);
        RecipeDetailIntent.putExtra("RecipeId", id);
        startActivity(RecipeDetailIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}