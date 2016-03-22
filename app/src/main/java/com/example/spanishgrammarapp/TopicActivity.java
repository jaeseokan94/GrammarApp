package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class TopicActivity extends Activity implements OnClickListener {
    public final static String CURRENT_TOPIC = "CURRENT_TOPIC";
    public final static String SUBTOPIC = "SUBTOPIC";
    private String currentTopic;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //Get current topic from intent
        currentTopic = getIntent().getStringExtra("TOPIC");

        //Set up
        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        TextView textView = (TextView) findViewById(R.id.tv_topic);
        textView.setText(currentTopic);
        textView.setTypeface(font);
        textView.setTextSize(35f);

        TextView textView1 = ((TextView) findViewById(R.id.btn_situational_video));
        textView1.setTypeface(font);

        TextView textView2 = ((TextView) findViewById(R.id.tv_situational_video_title));
        textView2.setTypeface(font);

        TextView textView3 = ((TextView) findViewById(R.id.tv_subtopic_title));
        textView3.setTypeface(font);



        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout_id);
        mainLayout.setBackgroundColor(Color.rgb(118, 178, 197));
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        // Get language level topic from proper variable
        String language = "Spanish";
        String level = "b";
        String topic = "Greeting";

        APIWrapper subtopicAPI = new APIWrapper(db);
        //This will pass subtopicList from Database
        ArrayList<String> subtopics = subtopicAPI.getSubtopicList(language, level, topic);



        for (String subtopicTitle: subtopics) {
            Button button = new Button(this);
            button.setText(subtopicTitle);
            button.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        String subtopicTitle = ((TextView) v).getText().toString();

        Intent intent = new Intent(this, GrammarVideoActivity.class);
        intent.putExtra(CURRENT_TOPIC, currentTopic);
        intent.putExtra(SUBTOPIC, subtopicTitle);
        startActivity(intent);
    }
}
