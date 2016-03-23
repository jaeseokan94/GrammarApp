package com.example.spanishgrammarapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.resources.activities.DialectActivity;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends Activity {

    public final static String TOPIC = "TOPIC";
    public final static String SUBTOPIC = "SUBTOPIC";
    public final static String EXERCISE_NAME = "EXERCISE_NAME";
    public final static String EXERCISE_ID = "EXERCISE_ID";
    public final static String NO_QUESTIONS = "NO_QUESTIONS";

    public static UserProgress userProgress;
    public static String currentLanguage ;
    public static String currentLevel;

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        mainLayout.setBackgroundColor(Color.rgb(118, 178, 197));

        userProgress = new UserProgress(this.getBaseContext());
        userProgress.loadProgress();
        setDefaultImageButtonSizes(Configuration.ORIENTATION_PORTRAIT);

        System.out.println("CURRENT LEVEL TEST " + currentLevel + " Current Lang "+ currentLanguage);

        // App will download API and save it into database when it is first open.
        database = new DatabaseHelper(this.getBaseContext());
        
        System.out.println(database.getAllKey());
        System.out.println("TESTING  " + database.getLevelList("Korean"));
        System.out.println("TESTING  " + database.getLevelList("Spanish"));
        System.out.println("TESTING REAL "+database.getSubTopicList("Spanish","b","Greeting"));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        // ---------------------------------------modified
        setContentView(R.layout.activity_main);
        setDefaultImageButtonSizes(newConfig.orientation);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("ORIENTATION_PORTRAIT");
        }
    }

    private void setDefaultImageButtonSizes(int orientation) {
        // ---------------------------------------modified
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            int screenHeight;

            int ideaWidth1;
            int ideaHeight1;
            int ideaWidth2;
            int ideaHeight2;
            screenHeight = displayMetrics.heightPixels;
            ideaHeight1 = screenHeight / 6;
            ideaWidth1 = ideaHeight1 * 20 / 7;
            ideaHeight2 = screenHeight / 10;
            ideaWidth2 = ideaHeight2 * 20 / 7;
            findViewById(R.id.button_learn).getLayoutParams().width = ideaWidth1;
            findViewById(R.id.button_learn).getLayoutParams().height = ideaHeight1;
            findViewById(R.id.button_practice).getLayoutParams().width = ideaWidth1;
            findViewById(R.id.button_practice).getLayoutParams().height = ideaHeight1;
            findViewById(R.id.button_revise).getLayoutParams().width = ideaWidth1;
            findViewById(R.id.button_revise).getLayoutParams().height = ideaHeight1;

            findViewById(R.id.button_resources).getLayoutParams().width = ideaWidth2;
            findViewById(R.id.button_resources).getLayoutParams().height = ideaHeight2;
            findViewById(R.id.button_glossary).getLayoutParams().width = ideaWidth2;
            findViewById(R.id.button_glossary).getLayoutParams().height = ideaHeight2;
            findViewById(R.id.button_credits).getLayoutParams().width = ideaWidth2;
            findViewById(R.id.button_credits).getLayoutParams().height = ideaHeight2;
        }
    }
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
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
        Intent intent = new Intent(this, DialectActivity.class);
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

    /**This method ensures the correct behaviour of the app when the back button is pressed.*/
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LevelActivity.class));
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
