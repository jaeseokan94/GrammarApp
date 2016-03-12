package com.example.spanishgrammarapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.spanishgrammarapp.Data.APIWrapper;

/**
 * Created by janaldoustorres on 06/03/2016.
 */
public class GrammarVideoActivity extends AppCompatActivity {
    private String topicTitle;
    private String subtopicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_video);

        //get intent
        topicTitle = getIntent().getStringExtra(TopicActivity.CURRENT_TOPIC);
        subtopicTitle = getIntent().getStringExtra(TopicActivity.SUBTOPIC);

        //get uri of grammar video
        Uri uri_grammar_video = APIWrapper.getGrammarVideoUri(topicTitle, subtopicTitle);

        //setup
        TextView tvSubtopicTitle = (TextView) findViewById(R.id.tv_grammar_video_subtopic);
        tvSubtopicTitle.setText(subtopicTitle);

        VideoView video_player_view = (VideoView) findViewById(R.id.vv_grammar_video);

        video_player_view.setVideoURI(uri_grammar_video);
        video_player_view.requestFocus();
        video_player_view.start();

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(video_player_view);
        video_player_view.setMediaController(vidControl);
    }
}
