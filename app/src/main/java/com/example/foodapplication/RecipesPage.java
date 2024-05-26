package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.media.MediaPlayer;

import java.util.ArrayList;
public class RecipesPage extends AppCompatActivity
{
    private MediaPlayer mediaPlayer;
    private MediaPlayer cancelClick;
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
            // iš My recipes į Main
            case R.id.item1:
                Intent mainIntent = new Intent(RecipesPage.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš My recipes į Ingredients
            case R.id.item2:
                Intent ingredientsIntent = new Intent(RecipesPage.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš My recipes į Add recipe
            case R.id.item3:
                Intent recipeIntent = new Intent(RecipesPage.this, AddRecipePage.class);
                startActivity(recipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš My recipes į Favourite recipes
            case R.id.item5:
                Intent favouriteIntent = new Intent(RecipesPage.this, FavoriteRecipesPage.class);
                startActivity(favouriteIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš My recipes į Settings
            case R.id.item6:
                Intent settingsIntent = new Intent(RecipesPage.this, SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            default:
                return super.onOptionsItemSelected(item);
        }
    };

    Button bt_addRecipe;
    Button backButton;
    TextView tw_emptyInformation;

    //Receptų sąrašas (masyvas)
    public static Recipes RecipesList = new Recipes();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);
        cancelClick = MediaPlayer.create(this, R.raw.cancleclickf);

        bt_addRecipe = (Button) findViewById(R.id.button);
        backButton = (Button) findViewById(R.id.backButtonRecipes);
        tw_emptyInformation = findViewById(R.id.oops);
        LinearLayout recipeLayout = (LinearLayout) findViewById(R.id.recipeListLayout);
        CheckIfEmpty();

        for(int i = 0; i < RecipesList.GetN(); i++)
        {
            //recipe layout
            LinearLayout oneRecipeLayout = new LinearLayout(getBaseContext());
            oneRecipeLayout.setOrientation(LinearLayout.VERTICAL);
            oneRecipeLayout.setMinimumWidth(400);
            oneRecipeLayout.setGravity(Gravity.CENTER);
            oneRecipeLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
            oneRecipeLayout.setBackgroundResource(R.drawable.box_shape_yellow);
            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 10);
            oneRecipeLayout.setLayoutParams(params);
            oneRecipeLayout.setPadding(50, 50, 50, 50);

            //recipe name
            TextView tw_recipeName = new TextView(getBaseContext());
            tw_recipeName.setText(RecipesList.GetName(i));
            tw_recipeName.setTextSize(25);
            tw_recipeName.setTextColor(Color.BLACK);
            tw_recipeName.setWidth(LayoutParams.MATCH_PARENT);
            tw_recipeName.setGravity(Gravity.CENTER);
            oneRecipeLayout.addView(tw_recipeName);
            LinearLayout.LayoutParams nameParams = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            nameParams.setMargins(0, 0, 0, 30);
            tw_recipeName.setLayoutParams(nameParams);

            int id = i;

            //Nuotrauka
            ImageView recipeImage = new ImageView(getBaseContext());
            Drawable recipeImageDrawable = new BitmapDrawable(getResources(), RecipesList.GetRecipe(i).GetRecipeImage());
            recipeImage.setImageDrawable(recipeImageDrawable);
            recipeImage.setOnClickListener(view -> ShowRecipeDetail(id));
            recipeImage.setAdjustViewBounds(true);
            oneRecipeLayout.addView(recipeImage);

            //button layout
            LinearLayout oneRecipeButtonsLayout = new LinearLayout(getBaseContext());
            oneRecipeButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
            oneRecipeButtonsLayout.setGravity(Gravity.CENTER);
            oneRecipeButtonsLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            buttonsParams.setMargins(0, 30, 0, 0);
            buttonsParams.gravity = Gravity.CENTER_HORIZONTAL;
            oneRecipeButtonsLayout.setLayoutParams(buttonsParams);

            //remove button
            Button ingRemoveButton = new Button(getBaseContext());
            ingRemoveButton.setOnClickListener(RemoveRecipe);
            ingRemoveButton.setBackgroundColor(Color.rgb(255, 127, 127));
            ingRemoveButton.setText("REMOVE");
            ingRemoveButton.setTextColor(Color.WHITE);
            ingRemoveButton.setGravity(Gravity.CENTER);
            //ingRemoveButton.setLayoutParams(new LayoutParams(300, 100));
            ingRemoveButton.setBackgroundResource(R.drawable.button_color);
            oneRecipeButtonsLayout.addView(ingRemoveButton);

            //favourite button
            Button ingFavoriteButton = new Button(getBaseContext());
            IsItFavorite(ingFavoriteButton, i);
            ingFavoriteButton.setOnClickListener(view -> FavoriteARecipe(ingFavoriteButton, id));
            //ingFavoriteButton.setBackgroundColor(Color.rgb(255, 127, 127));
            ingFavoriteButton.setText("FAVORITE");
            ingFavoriteButton.setTextColor(Color.WHITE);
            ingFavoriteButton.setGravity(Gravity.CENTER);
            //ingFavoriteButton.setBackgroundResource(R.drawable.button_color);
            oneRecipeButtonsLayout.addView(ingFavoriteButton);

            //adding layouts together
            oneRecipeLayout.addView(oneRecipeButtonsLayout);
            recipeLayout.addView(oneRecipeLayout);
        }

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
/*
                Intent intent2 = new Intent(RecipesPage.this, MainActivity.class);
                try {
                    Class<?> c = Class.forName(previousPage);
                    Intent intent = new Intent(getBaseContext(), c);
                    startActivity(intent);
                } catch (ClassNotFoundException ignored) {
                    startActivity(intent2);
                }*/
            }
        });

        bt_addRecipe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), AddRecipePage.class);
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //kai paspaudi ant recepto foto numeta į platesnės informacijos puslapį
    private void ShowRecipeDetail(final int id)
    {
        Intent RecipeDetailIntent = new Intent (RecipesPage.this, RecipeInformationPage.class);
        RecipeDetailIntent.putExtra("RecipeId", id);
        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        startActivity(RecipeDetailIntent);
    }

    //pagal tai, ar paspausta "favorite" nuspalvina mygtuką atitinkamai
    private void IsItFavorite(Button btt, int id)
    {
        if(RecipesList.GetRecipe(id).IsFavorite())
        {
            btt.setBackgroundColor(Color.RED);
        }
        else if(!RecipesList.GetRecipe(id).IsFavorite())
        {
            btt.setBackgroundColor(Color.BLUE);
        }
    }

    //paspaudus "favorite" atitinkamai pasikeičia mygtukas
    private void FavoriteARecipe(final Button btt, final int id)
    {
        if(!RecipesList.GetRecipe(id).IsFavorite())
        {
            //galima kitaip vizualizuoti, kad pažymėjo
            btt.setBackgroundColor(Color.RED);
            RecipesList.GetRecipe(id).IsFavorite(true);
        }
        else if(RecipesList.GetRecipe(id).IsFavorite())
        {
            btt.setBackgroundColor(Color.BLUE);
            RecipesList.GetRecipe(id).IsFavorite(false);
        }
    }

    //jei nėra sukurtų receptų rodomas informacinis langas, jei yra - jis yra paslėpiamas
    private void CheckIfEmpty()
    {
        if(RecipesList.GetN() == 0)
        {
            tw_emptyInformation.setVisibility(View.VISIBLE);
        }
        else
        {
            tw_emptyInformation.setVisibility(View.GONE);
        }
    }

    //pašalinamas receptas
    View.OnClickListener RemoveRecipe = new View.OnClickListener()
    {
        @Override
        public void onClick(View btt)
        {
            //paleidzia sfx
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            AlertDialog dialog = new AlertDialog.Builder(RecipesPage.this)

                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to remove this recipe?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int whichButton) {
                            //paleidzia sfx
                            if (cancelClick != null) {
                                cancelClick.start();
                            }
                            Toast.makeText(RecipesPage.this, "Recipe removed", Toast.LENGTH_SHORT).show();
                            LinearLayout recipes = (LinearLayout) findViewById((R.id.recipeListLayout));
                            LinearLayout recipeLine = (LinearLayout) btt.getParent().getParent();
                            recipes.removeView(recipeLine);
                            TextView name = (TextView) recipeLine.getChildAt(0);
                            String removeName = (String) name.getText();

                            for (int i = 0; i < RecipesList.GetN(); i++) {
                                if (RecipesList.GetRecipe(i).GetRecipeName().equals(removeName)) {
                                    RecipesList.Remove(i);
                                    CheckIfEmpty();
                                }
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
            //paleidzia sfx
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.button_color);
        }

    };
}