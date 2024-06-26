package com.example.foodapplication;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new MusicBinder();

    private SharedPreferences sharedPreferences;

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MusicService", "Service onCreate");
        mediaPlayer = MediaPlayer.create(this, R.raw.picosmuzika);
        mediaPlayer.setLooping(true);

        //startMusic(); //sitas padaro kad iskart grotu po to kai zinosiu kjp issaugot values reikes sita pataisyt

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Check the saved state of the switch
        boolean isMusicSwitchOn = sharedPreferences.getBoolean("music_switch_state", true);

        if (isMusicSwitchOn) {
            startMusic();
        }
    }

    public void startMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
