package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
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
            case R.id.item2:
                Intent ingredientsIntent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(ingredientsIntent);
                return true;
            case R.id.item3:
                Intent recipeIntent = new Intent(MainActivity.this, AddRecipePage.class);
                startActivity(recipeIntent);
                return true;
            case R.id.item4:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
          /*  case R.id.subitem1:
                Toast.makeText(this, "Sub Item 1 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Sub Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick);

        Intent intent = new Intent(this, MusicService.class);
        startService(intent);

        Button button1 = findViewById(R.id.ingredients); // Find the "Ingredients" button by its ID
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        Button button2 = findViewById(R.id.add_recipe); // Find the "Add Recipe" button by its ID
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paleidzia sfx
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }

                Intent intent = new Intent(MainActivity.this, RecipesPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
