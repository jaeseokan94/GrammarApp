package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TopicActivity extends AppCompatActivity {
    public final static String CURRENT_TOPIC = "CURRENT_TOPIC";
    private String currentTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        //        This is how we tell what topic we've entered
        currentTopic = getIntent().getStringExtra("TOPIC");

        TextView textView = (TextView) findViewById(R.id.tv_topic);
        textView.setText(currentTopic);
    }

    public void enterSituationalVideo(View view){
        Intent intent = new Intent(this, SituationalVideoActivity.class);
        intent.putExtra(CURRENT_TOPIC, currentTopic );
        startActivity(intent);
    }
}
