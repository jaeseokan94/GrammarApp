package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TopicActivity extends AppCompatActivity {
    private String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

//        This is how we tell what topic we've entered.
        Intent intent = getIntent();
        String topic2 = intent.getStringExtra("TOPIC");

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(topic2);
    }
}
