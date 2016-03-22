package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class SubtopicsActivity extends Activity implements View.OnClickListener {

    private String topic;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);

        topic = getIntent().getStringExtra("TOPIC"); //This is how we tell what topic we've entered.

        LinearLayout subtopicLayout = (LinearLayout) findViewById(R.id.main_layout_subtopic_id);
        subtopicLayout.setOrientation(LinearLayout.VERTICAL);
        subtopicLayout.setGravity(Gravity.CENTER);
        subtopicLayout.setBackgroundColor(Color.rgb(118, 178, 197));

        APIWrapper subtopicAPI = new APIWrapper(db);
        //This will pass subtopicList from Database
        System.out.println(MainActivity.currentLanguage);
        ArrayList<String> subtopics = subtopicAPI.getSubtopicList(MainActivity.currentLanguage, MainActivity.currentLevel, topic);

        System.out.println("SUBTOPIC URL TEST ~~~~~~~~");
        System.out.println(subtopics);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (String subtopic: subtopics) {
            Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
            Button button = new Button(this);
            button.setText(subtopic);
            button.setGravity(Gravity.CENTER);
            button.setTag(subtopic);
            button.setBackground(getDrawable(R.drawable.button));
            button.setOnClickListener(this);
            button.setTypeface(font);
            subtopicLayout.addView(button, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {
        if(((Button) v).getText().equals("Vocabulary")){
            goToExercise(v);
            return;
        }
        Intent intent = new Intent(this, ExerciseSelector.class); //create a new intent
        intent.putExtra(MainActivity.TOPIC, topic);
        intent.putExtra(MainActivity.SUBTOPIC, v.getTag().toString());
        startActivity(intent); //start the activity
    }

    /*This method starts exercises activity and relays the intent data from previous activity*/
    private void goToExercise(View view){
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra(ExercisesActivity.vocab, true);
        intent.putExtra(MainActivity.TOPIC, getIntent().getStringExtra(MainActivity.TOPIC)); //forward the topic string
        intent.putExtra(MainActivity.SUBTOPIC, "Vocabulary"); //special case for Vocabulary exercise
        intent.putExtra(MainActivity.EXERCISE_NAME, ((Button) view).getText());
        startActivity(intent);
    }

    /**This method ensures the correct behaviour of the app when the back button is pressed.*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PracticeActivity.class);
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
