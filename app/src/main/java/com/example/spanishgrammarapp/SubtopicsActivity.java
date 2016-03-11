package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class SubtopicsActivity extends AppCompatActivity implements View.OnClickListener {

    private String topic;
    private String SUBTOPIC;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);

//        This is how we tell what topic we've entered.
        topic = getIntent().getStringExtra("TOPIC");
        TextView textView = (TextView) findViewById(R.id.tv_topic);
        textView.setText(topic);

        LinearLayout subtopicLayout = (LinearLayout) findViewById(R.id.main_layout_subtopic_id);


        //This will pass subtopicList from Database
        ArrayList<String> subtopics = CMSconnector.getSubtopics(getBaseContext(), topic);

        for (String subtopic: subtopics) {
            Button button = new Button(this);
            button.setText(subtopic);
            button.setOnClickListener(this);
            subtopicLayout.addView(button);
        }


    }

    @Override
    public void onClick(View v) {
        String subtopic = ((TextView) v).getText().toString();
        Exercise exercise = CMSconnector.getExercise(getBaseContext(), topic, SUBTOPIC);
        Intent intent = new Intent(this, ExercisesActivity.class);  //create a new intent
        intent.putExtra(SUBTOPIC, subtopic);
        intent.putExtra(MainActivity.QUESTIONS, exercise); //pass in the exercise (populated)
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subtopics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
