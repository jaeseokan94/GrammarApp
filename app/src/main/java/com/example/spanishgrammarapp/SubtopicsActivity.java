package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.spanishgrammarapp.Data.DatabaseHelper;

public class SubtopicsActivity extends AppCompatActivity {

    private String topic;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);

//        This is how we tell what topic we've entered.
        topic = getIntent().getStringExtra("TOPIC");
    }

    public void startExercise(View view){
        //Exercise exercise = new Exercise(); //create a new Exercise, a set of questions (empty)
        //CMSconnector connector = new CMSconnector(exercise, topic, view.getTag().toString(), this.getBaseContext()); //pass that empty Exercise to the CMSconnector
        //connector.constructExercise(); //the connector populates it with data from the DB
        String subtopic = view.getTag().toString();
        Exercise exercise = CMSconnector.getExercise(getBaseContext(), topic, subtopic);
        Intent intent = new Intent(this, ExercisesActivity.class); //create a new intent
        intent.putExtra(MainActivity.QUESTIONS, exercise); //pass in the exercise (populated)
        startActivity(intent); //start the activity
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
