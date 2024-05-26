package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;


public class RecipeInformationPage extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        //paleidzia sfx
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//        }

//        switch (item.getItemId()) {
//            case R.id.item1:
//                //Toast.makeText(this, "Item 1 selected", Toast.LENGTH_SHORT).show();
//                Intent mainIntent = new Intent(RecipeInformationPage.this, MainActivity.class);
//                startActivity(mainIntent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            case R.id.item2:
//                Intent ingredientsIntent = new Intent(RecipeInformationPage.this, IngredientsActivity.class);
//                startActivity(ingredientsIntent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            case R.id.item3:
//                Intent myRecipesIntent = new Intent(RecipeInformationPage.this, RecipesPage.class);
//                startActivity(myRecipesIntent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            case R.id.item4:
//                Intent favoriteRecipeIntent = new Intent (RecipeInformationPage.this, FavoriteRecipesPage.class);
//                startActivity(favoriteRecipeIntent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            case R.id.item5:
//                Intent addRecipeIntent = new Intent(RecipeInformationPage.this, AddRecipePage.class);
//                startActivity(addRecipeIntent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_information_page);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        int id = getIntent().getIntExtra("RecipeId", -1);

        TextView tv_RecipeName = findViewById(R.id.tvRecipeName);
        ImageView iv_RecipeImage = findViewById(R.id.RecipeImage);
        TextView tv_Ingredients = findViewById(R.id.tvListOfIngredients);
        TextView tv_Preparation = findViewById(R.id.tvPreparationInstructions);
        TextView tv_Notes = findViewById(R.id.tvNotesInformation);
        Button bt_Back = findViewById(R.id.backButton);
        Button bt_Save = findViewById(R.id.editButton);
        bt_Save.setOnClickListener(view -> EditRecipe(id));

        if(id == -1)
        {
            Toast.makeText(RecipeInformationPage.this, "No recipe was picked", Toast.LENGTH_LONG).show();
            return;
        }

        tv_RecipeName.setText(RecipesPage.RecipesList.GetRecipe(id).GetRecipeName());

        Drawable recipeImageDrawable = new BitmapDrawable(getResources(), RecipesPage.RecipesList.GetRecipe(id).GetRecipeImage());
        iv_RecipeImage.setImageDrawable(recipeImageDrawable);

        for(int i = 0; i < RecipesPage.RecipesList.GetRecipe(id).GetAmountOfIngredients(); i++)
        {
            int number = i+1;
            String ingredientLine =  number + ") " +  RecipesPage.RecipesList.GetRecipe(id).GetIngredient(i).GetName()
                    + " - " + RecipesPage.RecipesList.GetRecipe(id).GetIngredient(i).GetAmount()
                    + " " + RecipesPage.RecipesList.GetRecipe(id).GetIngredient(i).GetMeasurement() + "\n";
            tv_Ingredients.setText(tv_Ingredients.getText() + ingredientLine);
        }

        if(RecipesPage.RecipesList.GetRecipe(id).GetRecipePreparation().equals(""))
        {
            tv_Preparation.setText("There are no preparation steps for this recipe");
        }
        else
        {
            tv_Preparation.setText(RecipesPage.RecipesList.GetRecipe(id).GetRecipePreparation());
        }

        if(RecipesPage.RecipesList.GetRecipe(id).GetRecipeNotes().equals(""))
        {
            tv_Notes.setText("There are no notes for this recipe");
        }
        else
        {
            tv_Notes.setText(RecipesPage.RecipesList.GetRecipe(id).GetRecipeNotes());
        }

        bt_Back.setOnClickListener(new View.OnClickListener()
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

    //nuvedama į redagavimo puslapį
    private void EditRecipe(final int id)
    {
        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        Intent editPageIntent = new Intent(RecipeInformationPage.this, EditRecipePage.class);
        editPageIntent.putExtra("RecipeId", id);
        startActivity(editPageIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}