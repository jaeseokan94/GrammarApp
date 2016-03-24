package com.example.spanishgrammarapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.ToggleButton;
import android.widget.VideoView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by janaldoustorres on 03/03/2016.
 */
public class SituationalVideoActivity extends Activity {
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

    APIWrapper apiWrapper;
    ArrayList<String> videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_video);

        //get current topic from intent
        current_topic = getIntent().getStringExtra(TopicActivity.CURRENT_TOPIC);

        apiWrapper=new APIWrapper(new DatabaseHelper(this));
        videoURL = apiWrapper.apiSituationalVideoURLs(current_topic);

        //View dynamic setup
        final VideoView video_player_view = (VideoView) findViewById(R.id.vv_situational_video);

        video_player_view.setVideoURI(Uri.parse(videoURL.get(1)));
        video_player_view.requestFocus();
        video_player_view.start();

        final MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(video_player_view);
        video_player_view.setMediaController(vidControl);

        ToggleButton toggleButton_transcript = (ToggleButton) findViewById(R.id.toggleButton_with_transcript);

        toggleButton_transcript.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //with transcript
                    video_player_view.stopPlayback();
                    video_player_view.setVideoURI(Uri.parse(videoURL.get(1)));
                    video_player_view.requestFocus();
                    video_player_view.start();
                } else {
                    //without transcript
                    video_player_view.stopPlayback();
                    video_player_view.setVideoURI(Uri.parse(videoURL.get(2)));
                    video_player_view.requestFocus();
                    video_player_view.start();
                }
            }
        });
    }
}
