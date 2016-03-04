package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SubtopicsActivity extends AppCompatActivity {

    private String topic;
    private Button button;
    String subtopic_name; // this will parse subtopic_name from API, but it doenst work since I haven't made
                          // subtopicConstructor in CMSconnector a


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);

//        This is how we tell what topic we've entered.
        topic = getIntent().getStringExtra("TOPIC");

        //subtopic button title   Implement this after create subtopicConstructor class
       // button = (Button)findViewById(R.id.button);
       // button.setText(subtopic_name);




    }

    public void startExercise(View view){
//        Exercise exercise = getExercise(view.getTag().toString());
        Intent intent = new Intent(this, ExercisesActivity.class); //create a new intent
        intent.putExtra(MainActivity.QUESTIONS, (Parcelable) getExercise(view.getTag().toString(), intent)); //pass in the exercise (populated)
        intent.putExtra(MainActivity.TOPIC, topic);
        startActivity(intent); //start the activity
    }



    /*Checks the UserProgress ArrayLists for existing exercise with matching identifier before
    * creating a duplicate object for the same exercise. This is very important for serialization.*/
    private Exercise getExercise(String subtopic, Intent intent){
        String identifier = topic+"/"+subtopic;
        Exercise exercise = new Exercise(identifier);
        if(UserProgress.exercisesInProgress.size()>0) {
            for (Exercise e : UserProgress.exercisesInProgress) {
                if (e.getIdentifier().equals(identifier)) {
                    System.out.println("We did it Reddit! Exercise in Progress detected.");
                    exercise = e;
                    intent.putExtra("TEMPFIX", "progress" );
                }
            }
        }else if(UserProgress.completedExercises.size()>0) {
            for (Exercise e : UserProgress.completedExercises) {
                if (e.getIdentifier().equals(identifier)) {
                    System.out.println("We did it Reddit! Completed Exercise detected.");
                    exercise = e;
                    intent.putExtra("TEMPFIX", "completed" );
                }
            }
        }else{
            intent.putExtra("TEMPFIX", "" );
            System.out.println("Populating new exercise");
            CMSconnector connector = new CMSconnector(exercise, topic); //pass that empty Exercise to the CMSconnector
            connector.constructExercise(); //the connector populates it with data from the DB
        }
            return exercise; //create a new Exercise, a set of questions (empty)
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
