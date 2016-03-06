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
    //private static final String urlCMS = "https://lang-it-up.herokuapp.com/";
    /**
     * Current topic of this situational video
     */
    private String current_topic;
    /**
     * Url of situational video with transcript
     */
    private String url_with_transcript;
    /**
     * Url of situational video without transcript
     */
    private String url_without_transcript;



    /**
     * gets JSON uri of situational video  transcript and without transcript
     * @param topic_name
     * @return uri of situational video with transcript
     */
    private String getJsonUri(String topic_name) {
        //TODO code here for getting url for situational video from JSONParser
        //String url is temporary
        String url_with_transcript = "https://lang-it-up.herokuapp.com/media/listening_comprehension/U01-E05.mp3";
        return url_with_transcript;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situational_video);

        //get current topic from intent
        current_topic = getIntent().getStringExtra(TopicActivity.CURRENT_TOPIC);

        //get situational video url
        String uri = getJsonUri(current_topic);
        Uri vidUri = Uri.parse(uri);

        //View dynamic setup
        VideoView video_player_view = (VideoView) findViewById(R.id.vv_situational_video);

        video_player_view.setVideoURI(vidUri);
        video_player_view.requestFocus();
        video_player_view.start();

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(video_player_view);
        video_player_view.setMediaController(vidControl);
    }
}
