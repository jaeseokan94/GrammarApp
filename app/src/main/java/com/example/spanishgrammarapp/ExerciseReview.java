package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ExerciseReview extends Activity {

    private Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

        TableLayout tableLayout = new TableLayout(this);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        tableLayout.setLayoutParams(tableParams);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(tableLayout, layoutParams);

        TableRow tableRow = new TableRow(this);
        TableLayout.LayoutParams rowParams =
                new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        rowParams.width=screenWidth;
        tableRow.setLayoutParams(rowParams);

        int questionNumber = 1;
        TextView header1 = new TextView(this); //1st header
        header1.setText("#");
        header1.setBackgroundColor(Color.parseColor("#739DA5A8"));
        header1.setTextSize(20);
        header1.setTypeface(font);
        tableRow.addView(header1);
        TextView header2 = new TextView(this); //2nd header
        header2.setText("Question");
        header2.setBackgroundColor(Color.parseColor("#73DAE6EB"));
        header2.setTextSize(20);
        header2.setTypeface(font);
        tableRow.addView(header2);
        TextView header3 = new TextView(this); //3rd header
        header3.setText("Correct");
        header3.setTextSize(20);
        header3.setTypeface(font);
        tableRow.addView(header3);
        tableLayout.addView(tableRow);
        for(Question q : getExercise().getQuestions()){
            TableRow tableRowQ = new TableRow(this);
            tableRowQ.setLayoutParams(rowParams);

            TextView number = new TextView(this);
            number.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            number.setText(questionNumber + ")");
            number.setTextSize(20);
            number.setTypeface(font);
            tableRowQ.addView(number);

            TextView question = new TextView(this);
            if(questionNumber%2==0){ //alternate colours for rows
                question.setBackgroundColor(Color.parseColor("#73DAE6EB"));
            }else{
                question.setBackgroundColor(Color.parseColor("#739DA5A8"));
            }
            question.setLayoutParams(new TableRow.LayoutParams((int) (screenWidth * 0.65), TableRow.LayoutParams.WRAP_CONTENT));
            question.setTextSize(20);
            question.setTypeface(font);
            question.setText(q.getQuestion());
            tableRowQ.addView(question);

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(this);
            TableRow.LayoutParams imageParams = new TableRow.LayoutParams(70,70);
            imageView.setLayoutParams(imageParams);
            if(q.isCompleted()){
                imageView.setBackground(getDrawable(R.drawable.check));
            }else{
                imageView.setBackground(getDrawable(R.drawable.cross));
            }
            linearLayout.addView(imageView);
            tableRowQ.addView(linearLayout);

            tableLayout.addView(tableRowQ);
            ++questionNumber;
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
