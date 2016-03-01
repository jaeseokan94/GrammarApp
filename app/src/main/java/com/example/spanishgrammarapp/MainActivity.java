package com.example.spanishgrammarapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final static String QUESTIONS = "QUESTIONS";
    public final static String TOPIC = "TOPIC";
    public final static String QUESTION_TYPE = "QUESTION_TYPE";
    public final static String QUESTION_TEXT = "QUESTION";
    public final static String ANSWERS = "ANSWERS";
    public final static String CORRECT_ANSWER = "CORRECT_ANSWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
//        relativeLayout.setBackground(getDrawable(R.drawable.bkg));
//        UserProgress up1 = new UserProgress(this.getBaseContext());
//        up1.saveProgress();
    }

    public void learnActivity(View view){
        Intent intent = new Intent(this, LearnActivity.class);
        startActivity(intent);
    }

    public void practiceActivity(View view){
        Intent intent = new Intent(this, PracticeActivity.class);
        startActivity(intent);
    }

    public void reviseActivity(View view){
        Intent intent = new Intent(this, ReviseActivity.class);
        startActivity(intent);
    }

    public void resourcesActivity(View view){
        Intent intent = new Intent(this, ResourcesActivity.class);
        startActivity(intent);
    }

    public void glossaryActivity(View view){
        Intent intent = new Intent(this, GlossaryActivity.class);
        startActivity(intent);
    }

    public void credits(View view){
        Dialog creditsDialog = new Dialog(this);
        creditsDialog.setTitle("Credits");
        TextView credits = new TextView(this);
        credits.setText(R.string.creditsDialog);
        creditsDialog.addContentView(credits,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        creditsDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
