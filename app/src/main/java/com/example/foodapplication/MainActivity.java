package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.d("MusicService", "logtest");
        // Start MusicService
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);

        mediaPlayer=MediaPlayer.create(this,R.raw.uisfxtst);

        Button button1 = findViewById(R.id.button1); // Find the "Ingredients" button by its ID
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.button2); // Find the "Add Recipe" button by its ID
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecipesPage.class);
                startActivity(intent);
            }
        });

        Button recipeHistory = findViewById(R.id.recipeHistory); // Find the "recipeHistory" button by its ID

        recipeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
                Intent intent = new Intent(MainActivity.this, RecipeHistoryActivity.class);
                startActivity(intent);
            }
        });

        Button settingsButton = findViewById(R.id.settingsButton); // Find the "recipeHistory" button by its ID

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mediaPlayer.start();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}