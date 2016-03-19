package com.example.spanishgrammarapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.ArrayList;

public class ExerciseSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);
        scrollView.setBackground(getDrawable(R.drawable.bkg));

        RelativeLayout relativeLayout = new RelativeLayout(this);
        scrollView.addView(relativeLayout);

        ArrayList<String> exerciseList = new ArrayList(2); //will need to get this from CMS
        exerciseList.add("Test 1");
        exerciseList.add("Test 2");
        exerciseList.add("Test 3");
        exerciseList.add("Test 4");
        exerciseList.add("Test 5");
        exerciseList.add("Test 6");
        exerciseList.add("Test 7");
        exerciseList.add("Test 8");
        exerciseList.add("Test 9");
        exerciseList.add("Test 10");
        exerciseList.add("Test 11");

        createGUI(exerciseList, relativeLayout);
    }

    private void createGUI(ArrayList<String> exerciseList, RelativeLayout relativeLayout){
        Button buttonV = new Button(this);
        buttonV.setText("Vocabulary");
        buttonV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToExercise(v);
            }
        });
        relativeLayout.addView(buttonV, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int buttonID = 10;
        buttonV.setId(buttonID);
        for (String s : exerciseList){
            Button button = new Button(this);
            button.setText(s);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, buttonID); //first time adds button below the vocab button
            relativeLayout.addView(button, layoutParams);
            ++buttonID; //after adding below the id, we increase it
            button.setId(buttonID); //and set the id of current button to new value
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToExercise(v);
                }
            });
        }
    }

    /*This method starts exercises activity and relays the intent data from previous activity*/
    private void goToExercise(View view){
        boolean isVocab = false;
        if(((Button) view).getText().equals("Vocabulary")){
            isVocab = true;
        }
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra(ExercisesActivity.vocab, isVocab);
        intent.putExtra(MainActivity.TOPIC, getIntent().getStringExtra(MainActivity.TOPIC)); //forward the topic string
        intent.putExtra(MainActivity.SUBTOPIC, getIntent().getStringExtra(MainActivity.SUBTOPIC)); //forward the subtopic string
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_selector, menu);
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
