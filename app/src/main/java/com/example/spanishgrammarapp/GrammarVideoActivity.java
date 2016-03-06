package com.example.spanishgrammarapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by janaldoustorres on 06/03/2016.
 */
public class GrammarVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_video);

        TextView tvSubtopicTitle = (TextView) findViewById(R.id.tv_grammar_video_subtopic);
        tvSubtopicTitle.setText("Grammar Video");
    }
}
