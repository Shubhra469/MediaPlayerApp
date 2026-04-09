package com.example.mediaplayerapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AudioActivity extends AppCompatActivity {

    Button openFile, play, pause, stop, restart;
    TextView status;
    MediaPlayer mediaPlayer;
    Uri audioUri;

    private final ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    audioUri = uri;
                    status.setText("File selected");

                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        openFile = findViewById(R.id.openFile);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        restart = findViewById(R.id.restart);
        status = findViewById(R.id.status);

        openFile.setOnClickListener(v -> getContent.launch("audio/*"));

        play.setOnClickListener(v -> {
            if (audioUri == null) {
                status.setText("Select file first");
                return;
            }

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, audioUri);
            }

            if (mediaPlayer != null) {
                mediaPlayer.start();
                status.setText("Playing...");
            }
        });

        pause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                status.setText("Paused");
            }
        });

        stop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                status.setText("Stopped");
            }
        });

        restart.setOnClickListener(v -> {
            if (audioUri == null) {
                status.setText("Select file first");
                return;
            }

            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            mediaPlayer = MediaPlayer.create(this, audioUri);

            if (mediaPlayer != null) {
                mediaPlayer.start();
                status.setText("Restarted");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}