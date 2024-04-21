package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private MusicService musicService;
    private boolean isServiceBound = false;

    //MediaPlayer musicPlayer;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
            musicService = binder.getService();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceBound = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button backButton = findViewById(R.id.settBackButton); // Find the "recipeHistory" button by its ID

        // Bind to MusicService
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        //musicPlayer=MediaPlayer.create(this,R.raw.aaa);
        //musicPlayer.setLooping(true);

        Switch musicSwitch = findViewById(R.id.switch1);
        musicSwitch.setChecked(true);

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked)
                {
                    //musicPlayer.start();
                    //playMusic();
                    if (musicService != null) {
                        musicService.startMusic();
                    }
                }
                else
                {
                    //musicPlayer.stop();
                    //stopMusic();
                    if (musicService != null) {
                        musicService.stopMusic();
                    }
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    /*
    private MediaPlayer createMediaPlayer() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.aaa);
        mediaPlayer.setLooping(true);
        return mediaPlayer;
    }

    private void playMusic() {
        if (musicPlayer == null) {
            musicPlayer = createMediaPlayer();
        }
        musicPlayer.start();
    }

    private void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    private MediaPlayer musicPlayer;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

     */
}