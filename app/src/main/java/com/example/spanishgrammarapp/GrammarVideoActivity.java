package com.example.spanishgrammarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by janaldoustorres on 06/03/2016.
 */
public class GrammarVideoActivity extends AppCompatActivity {
    private String subtopicTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_video);

        subtopicTitle = getIntent().getStringExtra(TopicActivity.SUBTOPIC);

        TextView tvSubtopicTitle = (TextView) findViewById(R.id.tv_grammar_video_subtopic);
        tvSubtopicTitle.setText(subtopicTitle);


    }
}
