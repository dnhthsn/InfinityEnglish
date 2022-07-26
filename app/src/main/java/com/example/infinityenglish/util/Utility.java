package com.example.infinityenglish.util;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class Utility {
    public static class Notice {
        public static void snack(View view, String data) {
            Snackbar snackbar = Snackbar.make(view, data, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public static class Player {
        public static void playAudio(String url){
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
