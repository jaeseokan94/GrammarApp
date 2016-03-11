package com.example.spanishgrammarapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.ToggleButton;
import android.widget.VideoView;

/**
 * Created by janaldoustorres on 03/03/2016.
 */
public class SituationalVideoActivity extends AppCompatActivity  {
    //private static final String urlCMS = "https://lang-it-up.herokuapp.com/";
    /**
     * Current topic of this situational video
     */
    private String current_topic;
    /**
     * Url of situational video with transcript
     */
    private Uri uri_with_transcript;
    /**
     * Url of situational video without transcript
     */
    private Uri uri_without_transcript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_video);

        //get current topic from intent
        current_topic = getIntent().getStringExtra(TopicActivity.CURRENT_TOPIC);

        //get situational video url
       // Uri uri[] = APIWrapper.getSituationalVideoURLs(current_topic);
        //uri_without_transcript = uri[0];
        //uri_with_transcript = uri[1];

        //View dynamic setup
        final VideoView video_player_view = (VideoView) findViewById(R.id.vv_situational_video);

        video_player_view.setVideoURI(uri_with_transcript);
        video_player_view.requestFocus();
        video_player_view.start();

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(video_player_view);
        video_player_view.setMediaController(vidControl);

        ToggleButton toggleButton_transcript = (ToggleButton) findViewById(R.id.toggleButton_with_transcript);
        toggleButton_transcript.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //with transcript
                    video_player_view.stopPlayback();
                    video_player_view.setVideoURI(uri_with_transcript);
                    video_player_view.requestFocus();
                    video_player_view.start();
                } else {
                    //without transcript
                    video_player_view.stopPlayback();
                    video_player_view.setVideoURI(uri_without_transcript);
                    video_player_view.requestFocus();
                    video_player_view.start();
                }
            }
        });
    }
}
