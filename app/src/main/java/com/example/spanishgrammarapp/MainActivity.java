package com.example.spanishgrammarapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.KeyData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String QUESTIONS = "QUESTIONS";
    public final static String TOPIC = "TOPIC";
    public final static String SUBTOPIC = "SUBTOPIC";
    public final static String QUESTION_TYPE = "QUESTION_TYPE";
    public final static String QUESTION_TEXT = "QUESTION";
    public final static String ANSWERS = "ANSWERS";
    public final static String CORRECT_ANSWER = "CORRECT_ANSWER";
    public static UserProgress userProgress;
    public static String LANGUAGE = "Spanish";
    public String level="B";
    public String currentLanguage ;
    public String currentLevel;


    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userProgress = new UserProgress(this.getBaseContext());
        userProgress.loadProgress();
        
     //   Intent intent = getIntent();
     //  language = intent.getStringExtra("lang");
       // currentLanguage = getIntent().getStringExtra("CURRENT_LANGUAGE");
        //TextView textView = (TextView) findViewById(R.id.testing);
       // textView.setText(currentLanguage);

        currentLevel = getIntent().getStringExtra("CURRENT_LEVEL");
        currentLanguage =getIntent().getStringExtra("CURRENT_LANGUAGE");
        //TextView textView2 = (TextView) findViewById(R.id.testing2);
       // textView2.setText(currentLevel);
      //  currentLevel    = getIntent().getStringExtra("CURRENT_LANGUAGE");



        // App will download API and save it into database when it is first open.
        database = new DatabaseHelper(this.getBaseContext());
        APIWrapper downloadAPI = new APIWrapper(database);
        downloadAPI.apiQuestions();
        //downloadAPI.getSubtopics(language, level, TOPIC);
        database.getAllKey();
        List<KeyData> list = database.getAllKey(); // getAllquestions list Test purpose
        System.out.println(list);
        
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

    public void recordingActivity(View view){
        Intent intent = new Intent(this, RecordingToolActivity.class);
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
