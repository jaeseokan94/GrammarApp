package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class SubtopicsActivity extends AppCompatActivity implements View.OnClickListener {

    private String topic;
    private String SUBTOPIC;
    private String level;
    private String language;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //자바 코드로 만들꺼라서 우선 주석처리 XML코드 LinearLayout subtopicLayout = (LinearLayout) findViewById(R.id.main_layout_subtopic_id);setContentView(R.layout.activity_subtopics);

        // RelativeLayout 생성
        RelativeLayout  activity_subtopic= new RelativeLayout(this);


        // RelativeLayout width, height 설정
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        // RelativeLayout에 width, height 설정 적용
        activity_subtopic.setLayoutParams(params);

        APIWrapper subtopicAPI = new APIWrapper(db);
        //This will pass subtopicList from Database
        System.out.println(MainActivity.currentLanguage);
        ArrayList<String> subtopics = subtopicAPI.getSubtopicList(MainActivity.currentLanguage, MainActivity.currentLevel, topic);
        System.out.println("SUBTOPIC URL TEST ~~~~~~~~");
        System.out.println(subtopics);

        for (String subtopic: subtopics) {
            Button button = new Button(this);
            button.setText(subtopic);

            button.setTag(subtopic);
            button.setOnClickListener(this);
            activity_subtopic.addView(button);

            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

            button.setLayoutParams(buttonParams);
        }
        // This is how we tell what topic we've entered.
        topic = getIntent().getStringExtra("TOPIC");


        //자바 코드로 만들꺼라서 우선 주석처리 XML코드 LinearLayout subtopicLayout = (LinearLayout) findViewById(R.id.main_layout_subtopic_id)
    }



//    @Override
//    public void onClick(View v) {
//        String subtopic = ((TextView) v).getText().toString();
//        Exercise exercise = CMSconnector.getExercise(getBaseContext(), topic, SUBTOPIC);
//        Intent intent = new Intent(this, ExercisesActivity.class);  //create a new intent
//        intent.putExtra(MainActivity.QUESTIONS, exercise); //pass in the exercise (populated)
//        intent.putExtra(SUBTOPIC, subtopic);
//        startActivity(intent);
//    }

    /**This method ensures the correct behaviour of the app when the back button is pressed.*/
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PracticeActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
            Intent intent = new Intent(this, ExercisesActivity.class); //create a new intent
            intent.putExtra(MainActivity.TOPIC, topic);
            intent.putExtra(MainActivity.SUBTOPIC, v.getTag().toString());
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
