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

public class SubtopicsActivity extends AppCompatActivity  {

    private String topic;
    private String SUBTOPIC;
    private String level;
    private String language;
    private Intent goToExercise;
    private RelativeLayout activity_subtopics;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtopics);
        goToExercise = new Intent(this, MainActivity.class);
        //goToExercise.putExtra(SubtopicsActivity.SUBTOPIC, intent.getStringExtra(subto.TOPIC));


        activity_subtopics = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(activity_subtopics, params);



        // RelativeLayout에 width, height 설정 적용
        activity_subtopics.setLayoutParams(params);
        topic = getIntent().getStringExtra("TOPIC");
        //LinearLayout subtopicLayout = (LinearLayout) findViewById(R.id.main_layout_subtopic_id);
        RelativeLayout.LayoutParams ButtonParams = new RelativeLayout.LayoutParams();
        // RelativeLayout의 차일드 View이기 때문에 RelativeLayout의 LayoutParams을
        // 적용해 준다.  ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT

        APIWrapper subtopicAPI = new APIWrapper(db);
        //This will pass subtopicList from Database
        System.out.println(MainActivity.currentLanguage);
        ArrayList<String> subtopics = subtopicAPI.getSubtopicList(MainActivity.currentLanguage, MainActivity.currentLevel, topic);
        System.out.println("SUBTOPIC URL TEST ~~~~~~~~");
        System.out.println(subtopics);


        Button[] SubtopicsButton = new Button[subtopics.size()];
        for (int i=0; i < subtopics.size(); i++) {
            SubtopicsButton[i] = new Button(this);
            SubtopicsButton[i].setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            SubtopicsButton[i].setText(subtopics.get(i));
            SubtopicsButton[i].setTag(subtopics.get(i));
            activity_subtopics.addView(SubtopicsButton[i]);
            SubtopicsButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(goToExercise); //create a new intent
                    intent.putExtra(MainActivity.TOPIC, topic);
                    intent.putExtra(MainActivity.SUBTOPIC, v.getTag().toString());
                    startActivity(intent); //start the activity
                }
            });
        }



//        This is how we tell what topic we've entered.


//    @Override
//    public void onClick(View v) {
//        String subtopic = ((TextView) v).getText().toString();
//        Exercise exercise = CMSconnector.getExercise(getBaseContext(), topic, SUBTOPIC);
//        Intent intent = new Intent(this, ExercisesActivity.class);  //create a new intent
//        intent.putExtra(MainActivity.QUESTIONS, exercise); //pass in the exercise (populated)
//        intent.putExtra(SUBTOPIC, subtopic);
//        startActivity(intent);
//    }
    }

    /**This method ensures the correct behaviour of the app when the back button is pressed.*/

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PracticeActivity.class);

        startActivity(intent);
    }



    public View.OnClickListener buttonListner = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

        }

    };



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
