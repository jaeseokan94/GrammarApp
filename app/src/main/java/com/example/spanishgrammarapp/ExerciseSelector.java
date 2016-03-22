package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class ExerciseSelector extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);
        scrollView.setBackground(getDrawable(R.drawable.bkg));

        RelativeLayout relativeLayout = new RelativeLayout(this);
        scrollView.addView(relativeLayout);

        APIWrapper apiWrapper = new APIWrapper(new DatabaseHelper(this));
        ArrayList<Exercise> exerciseList = apiWrapper.getExercisesList(getIntent().getStringExtra(MainActivity.TOPIC), getIntent().getStringExtra(MainActivity.SUBTOPIC)); //will need to get this from CMS

        createGUI(exerciseList, relativeLayout);
        if(getIntent().getBooleanExtra(MainActivity.NO_QUESTIONS, false)){
            Toast toast = Toast.makeText(this, "No questions are available for this exercise at this moment", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * @param exerciseList the collection of Exercise objects that we want to create buttons for.
     * @param relativeLayout the layout that we want to add these buttons to*/
    private void createGUI(ArrayList<Exercise> exerciseList, RelativeLayout relativeLayout){
        int buttonID = 10;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.BELOW, buttonID); //first time adds button below the vocab button
        for (Exercise s : exerciseList){
            Button button = new Button(this);
            button.setText(s.getName());
            button.setTag(s.getId());
            if(buttonID==10){
                relativeLayout.addView(button, layoutParams);
            }else{
                relativeLayout.addView(button); //the first button does not go below anything
            }
            button.setId(buttonID++); //and set the id of current button to new value
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
        Intent intent = new Intent(this, ExercisesActivity.class);
        intent.putExtra(MainActivity.TOPIC, getIntent().getStringExtra(MainActivity.TOPIC)); //forward the topic string
        intent.putExtra(MainActivity.SUBTOPIC, getIntent().getStringExtra(MainActivity.SUBTOPIC)); //forward the subtopic string
        intent.putExtra(MainActivity.EXERCISE_NAME, ((Button) view).getText());
        intent.putExtra(MainActivity.EXERCISE_ID, view.getTag().toString());
        startActivity(intent);
    }
    /**This method ensures the correct behaviour of the app when the back button is pressed.*/
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SubtopicsActivity.class);
        intent.putExtra(MainActivity.TOPIC ,getIntent().getStringExtra(MainActivity.TOPIC));
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
