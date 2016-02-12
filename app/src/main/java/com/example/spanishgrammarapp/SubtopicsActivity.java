package com.example.spanishgrammarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class SubtopicsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);

//        This is how we tell what topic we've entered.
        String topic = getIntent().getStringExtra("TOPIC");
    }

    public void startExercise(View view){
        //this small block of code is for testing purposes only. It will be removed later with the introduction of the API for CMS.
        ArrayList<String> answers = new ArrayList<>();
        answers.add("Because. And that's all I say.");
        answers.add("Because reasons");
        Question q = new Question("mc", "There is a 'd' in Fridge but not in Refrigerator, why?", answers.get(0) , answers);

        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra("questionType", q.getQuestionType());
        intent.putExtra("question", q.getQuestion());
        intent.putExtra("cAnswer", q.getCorrectAnswer());
        intent.putExtra("answers", q.getAnswers());
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
