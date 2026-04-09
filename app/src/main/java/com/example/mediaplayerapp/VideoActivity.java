package com.example.mediaplayerapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    EditText url;
    Button openUrl, play, pause, stop, restart;
    VideoView videoView;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        url = findViewById(R.id.url);
        openUrl = findViewById(R.id.openUrl);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        restart = findViewById(R.id.restart);
        videoView = findViewById(R.id.videoView);

        openUrl.setOnClickListener(v -> {
            String link = url.getText().toString();

            if(link.isEmpty()){
                Toast.makeText(this, "Enter URL", Toast.LENGTH_SHORT).show();
                return;
            }

            videoUri = Uri.parse(link);
            videoView.setVideoURI(videoUri);
        });

        play.setOnClickListener(v -> videoView.start());

        pause.setOnClickListener(v -> videoView.pause());

        stop.setOnClickListener(v -> {
            if(videoView != null){
                videoView.stopPlayback();
            }
        });

        restart.setOnClickListener(v -> {
            if(videoUri != null){
                videoView.setVideoURI(videoUri);
                videoView.start();
            }
        });
    }
}