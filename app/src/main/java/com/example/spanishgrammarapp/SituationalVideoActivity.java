package com.example.spanishgrammarapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by janaldoustorres on 03/03/2016.
 */
public class SituationalVideoActivity extends AppCompatActivity  {
    private static final String urlCMS = "https://lang-it-up.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_video);
        
            String uri = urlCMS + "media/FACE_PALM.mp4";
            Uri vidUri = Uri.parse(uri);

            VideoView video_player_view = (VideoView) findViewById(R.id.vv_situational_video);

            video_player_view.setVideoURI(vidUri);
            video_player_view.requestFocus();
            video_player_view.start();

            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(video_player_view);
            video_player_view.setMediaController(vidControl);
    }
}
