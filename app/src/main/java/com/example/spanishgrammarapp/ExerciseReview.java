package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class ExerciseReview extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        setContentView(scrollView);
        scrollView.setBackground(getDrawable(R.drawable.bkg));
        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        scrollView.addView(relativeLayout, layoutParams);

        setContentView(scrollView);
        createGUI(relativeLayout);
    }

    private void createGUI(RelativeLayout relativeLayout){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        GridLayout gridLayout = new GridLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(gridLayout, layoutParams);
        gridLayout.setColumnCount(3);
        TableLayout tableLayout = new TableLayout(this);
        TableLayout.LayoutParams layoutParams1 =  new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        GridLayout.Spec columnSpec1 = GridLayout.spec(0, 1.0f);
//        GridLayout.Spec rowSpec1 = GridLayout.spec(GridLayout.UNDEFINED);
//        GridLayout.LayoutParams gridParams1 = new GridLayout.LayoutParams(rowSpec1, columnSpec1);
//        gridParams1.width = screenWidth/8;
//
//        GridLayout.Spec columnSpec2 = GridLayout.spec(1, 5.0f);
//        GridLayout.Spec rowSpec2 = GridLayout.spec(GridLayout.UNDEFINED);
//        GridLayout.LayoutParams gridParams2 = new GridLayout.LayoutParams(rowSpec2, columnSpec2);
//        gridParams2.width = 6*(screenWidth/8);
//
//        GridLayout.Spec columnSpec3 = GridLayout.spec(2, 1.0f);
//        GridLayout.Spec rowSpec3 = GridLayout.spec(GridLayout.UNDEFINED);
//        GridLayout.LayoutParams gridParams3 = new GridLayout.LayoutParams(rowSpec3, columnSpec3);
//        gridParams3.width = screenWidth/8;

        int questionNumber = 1;
        TextView header1 = new TextView(this); //1st header
        header1.setText("# | ");
        header1.setTextSize(20);
        gridLayout.addView(header1);
        TextView header2 = new TextView(this); //2nd header
        header2.setText("Question |");
        header2.setTextSize(20);
        gridLayout.addView(header2);
        TextView header3 = new TextView(this); //3rd header
        header3.setText("Correct");
        header3.setTextSize(20);
        gridLayout.addView(header3);
        for(Question q : getExercise().getQuestions()){
            TextView number = new TextView(this);
            number.setText(questionNumber + "");
            number.setTextSize(20);
            gridLayout.addView(number);

            TextView question = new TextView(this);
            question.setTextSize(20);
            question.setText(q.getQuestion());
            gridLayout.addView(question);

            TextView textView = new TextView(this);
            textView.setText(q.isCompleted() + "");
            textView.setTextSize(20);
            gridLayout.addView(textView);
        }
    }

    private Exercise getExercise(){
        for (Exercise e : UserProgress.completedExercises) {
            if (e.getIdentifier().equals(getIntent().getStringExtra(ExercisesActivity.IDENTIFIER))) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ExerciseSelector.class);
        intent.putExtra(MainActivity.TOPIC, getIntent().getStringExtra(MainActivity.TOPIC));
        intent.putExtra(MainActivity.SUBTOPIC, getIntent().getStringExtra(MainActivity.SUBTOPIC));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_review, menu);
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
