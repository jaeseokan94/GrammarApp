package com.example.spanishgrammarapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class PracticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        // ---------------------------------------modified
        setDefaultImageButtonSizes(Configuration.ORIENTATION_PORTRAIT);
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_revise, menu);
            return true;
        }
    public void enterTopic(View view){
        Intent intent = new Intent(this, SubtopicsActivity.class);
        intent.putExtra(MainActivity.TOPIC, (String) view.getTag());
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);

        // ---------------------------------------modified
        setContentView(R.layout.activity_learn);
        setDefaultImageButtonSizes(newConfig.orientation);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("ORIENTATION_PORTRAIT");
        }
    }

    private void setDefaultImageButtonSizes(int orientation) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        // ---------------------------------------modified
        int screenWidth;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            screenWidth= displayMetrics.widthPixels;
        }else{
            screenWidth = displayMetrics.heightPixels;
        }

        int idealSize = (screenWidth / 3) - 32;
        findViewById(R.id.button_greetings).getLayoutParams().width = idealSize;
        findViewById(R.id.button_greetings).getLayoutParams().height = idealSize;
        findViewById(R.id.button_checking_in).getLayoutParams().width = idealSize;
        findViewById(R.id.button_checking_in).getLayoutParams().height = idealSize;
        findViewById(R.id.button_sightseeing).getLayoutParams().width = idealSize;
        findViewById(R.id.button_sightseeing).getLayoutParams().height = idealSize;
        findViewById(R.id.button_directions).getLayoutParams().width = idealSize;
        findViewById(R.id.button_directions).getLayoutParams().height = idealSize;
        findViewById(R.id.button_eating).getLayoutParams().width = idealSize;
        findViewById(R.id.button_eating).getLayoutParams().height = idealSize;
        findViewById(R.id.button_likes).getLayoutParams().width = idealSize;
        findViewById(R.id.button_likes).getLayoutParams().height = idealSize;
        findViewById(R.id.button_planning).getLayoutParams().width = idealSize;
        findViewById(R.id.button_planning).getLayoutParams().height = idealSize;
        findViewById(R.id.button_shopping).getLayoutParams().width = idealSize;
        findViewById(R.id.button_shopping).getLayoutParams().height = idealSize;
        findViewById(R.id.button_dating).getLayoutParams().width = idealSize;
        findViewById(R.id.button_dating).getLayoutParams().height = idealSize;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
