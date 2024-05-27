package com.example.foodapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.media.MediaPlayer;

public class IngredientsActivity extends AppCompatActivity {

    // - - - - MENU - - -
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
            // iš Ingredients į Main
            case R.id.item1:
                Intent mainIntent = new Intent(IngredientsActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
               return true;
            // iš Ingredients į Add recipe
            case R.id.item3:
                Intent recipeIntent = new Intent(IngredientsActivity.this, AddRecipePage.class);
                startActivity(recipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Ingredients į My recipes
            case R.id.item4:
                Intent myrecipeIntent = new Intent(IngredientsActivity.this, RecipesPage.class);
                startActivity(myrecipeIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Ingredients į Favourite recipes
            case R.id.item5:
                Intent favouriteIntent = new Intent(IngredientsActivity.this, FavoriteRecipesPage.class);
                startActivity(favouriteIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            // iš Ingredients į Settings
            case R.id.item6:
                Intent settingsIntent = new Intent(IngredientsActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            default:
                return super.onOptionsItemSelected(item);
        }
    };


    private MediaPlayer mediaPlayer;
    private ArrayList<IngredientItem> ingredientList;
    private IngredientsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        ingredientList = new ArrayList<>();
        ingredientList.add(new IngredientItem("Vištiena", 0));
        ingredientList.add(new IngredientItem("Kiauliena", 0));
        ingredientList.add(new IngredientItem("Salota", 0));
        ingredientList.add(new IngredientItem("Kiaušiniai", 0));

        RecyclerView recyclerViewIngredients = findViewById(R.id.recyclerViewIngredients);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(this));

        adapter = new IngredientsAdapter(ingredientList);
        recyclerViewIngredients.setAdapter(adapter);
    }

    public void onAddCustomIngredientClicked(View view) {
        showAddCustomIngredientDialog();
    }

    private void showAddCustomIngredientDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Custom Ingredient");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_custom_ingredient, null);
        EditText editTextIngredientName = view.findViewById(R.id.editTextIngredientName);
        EditText editTextIngredientQuantity = view.findViewById(R.id.editTextIngredientQuantity);
        builder.setView(view);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                String ingredientName = editTextIngredientName.getText().toString().trim();
                String quantityString = editTextIngredientQuantity.getText().toString().trim();

                if (!ingredientName.isEmpty() && !quantityString.isEmpty()) {
                    int quantity = Integer.parseInt(quantityString);

                    ingredientList.add(new IngredientItem(ingredientName, quantity));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Custom ingredient added: " + ingredientName, Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Please enter both ingredient name and quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onProceedClicked(View view) {

        //paleidzia sfx
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        for (int i = 0; i < ingredientList.size(); i++) {
            int quantity = adapter.getQuantity(i);
            ingredientList.get(i).setQuantity(quantity);
        }

        Toast.makeText(this, "Information saved successfully", Toast.LENGTH_SHORT).show();

    }
}
