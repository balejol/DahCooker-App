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

public class IngredientsActivity extends AppCompatActivity {

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
                Intent ingredientsIntent = new Intent(IngredientsActivity.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                return true;
            case R.id.item3:
                Intent recipeIntent = new Intent(IngredientsActivity.this, RecipesPage.class);
                startActivity(recipeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    };


    private ArrayList<IngredientItem> ingredientList;
    private IngredientsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ingredientList = new ArrayList<>();
        ingredientList.add(new IngredientItem("Vištiena", 0));
        ingredientList.add(new IngredientItem("Kiauliena", 0));
        ingredientList.add(new IngredientItem("Salota", 5));
        ingredientList.add(new IngredientItem("Kiaušiniai", 7));

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

        for (int i = 0; i < ingredientList.size(); i++) {
            int quantity = adapter.getQuantity(i);
            ingredientList.get(i).setQuantity(quantity);
        }

        Toast.makeText(this, "Information saved successfully", Toast.LENGTH_SHORT).show();

    }
}
