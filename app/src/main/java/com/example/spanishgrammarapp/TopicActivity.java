package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TopicActivity extends AppCompatActivity {
    public final static String CURRENT_TOPIC = "CURRENT_TOPIC";
    private String currentTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //Get current topic from intent
        currentTopic = getIntent().getStringExtra("TOPIC");

        //Set up
        TextView textView = (TextView) findViewById(R.id.tv_topic);
        textView.setText(currentTopic);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout_id);

        ArrayList<String> subtopics = JSONParser.getSubtopics(currentTopic);
        for (String subtopicTitle: subtopics) {
            Button button = new Button(this);
            button.setText(subtopicTitle);
            
            mainLayout.addView(button);
        }
    }

    /**
     * Run when subtopic button is clicked
     * @param view clicked button
     */
    public void enterSituationalVideo(View view){
        Intent intent = new Intent(this, SituationalVideoActivity.class);
        intent.putExtra(CURRENT_TOPIC, currentTopic);
        startActivity(intent);
    }
}
