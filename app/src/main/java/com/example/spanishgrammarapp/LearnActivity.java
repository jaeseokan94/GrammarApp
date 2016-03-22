package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.util.ArrayList;

public class LearnActivity extends Activity {
    private final static String EXTRA_MESSAGE = "TOPIC";
    private ArrayList<ImageButton> buttons;
    private GridLayout mainLayout; //this layout is accesses in several places
    private ArrayList<String> topics; //this was a parameter for createGUI() but the method is called in too many places

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIWrapper topicAPI = new APIWrapper(new DatabaseHelper(this));
        topics = topicAPI.getTopicList();
        createGUI(Configuration.ORIENTATION_PORTRAIT);
        setDefaultImageButtonSizes(Configuration.ORIENTATION_PORTRAIT, buttons);
        setContentView(mainLayout); //don't move this pls, it's initialise in createGUI()
    }

    private void createGUI(int orientation){
        mainLayout = new GridLayout(this);
        mainLayout.setBackground(getDrawable(R.drawable.bkg));
        if(orientation==Configuration.ORIENTATION_PORTRAIT){
            mainLayout.setRowCount(3);
            mainLayout.setColumnCount(3);
        }else if(orientation==Configuration.ORIENTATION_LANDSCAPE){
            mainLayout.setColumnCount(5);
            mainLayout.setRowCount(2);
        }
        buttons = new ArrayList<>(9); //create and populate this ArrayList because there are 2 ways these buttons can appear, this makes the code more efficient.
        for (String s : topics) {
            ImageButton button = new ImageButton(this);
            button.setTag(s); //set the tag to the topic name because our image buttons can't have text. We can add text from tag for the other buttons.
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enterTopic(v);
                }
            });
            buttons.add(button);
            mainLayout.addView(button);
        }
        if(MainActivity.currentLevel.toLowerCase().contains("b")) {
            buttons.get(0).setBackground(getDrawable(R.drawable.greetings));
            buttons.get(1).setBackground(getDrawable(R.drawable.checkin));
            buttons.get(2).setBackground(getDrawable(R.drawable.sight));
            buttons.get(3).setBackground(getDrawable(R.drawable.directions));
            buttons.get(4).setBackground(getDrawable(R.drawable.eating));
            buttons.get(5).setBackground(getDrawable(R.drawable.likes));
            buttons.get(6).setBackground(getDrawable(R.drawable.planning));
//            buttons.get(7).setBackground(getDrawable(R.drawable.shopping));
//            buttons.get(8).setBackground(getDrawable(R.drawable.dating));
        }else {
            for (ImageButton button : buttons){
                button.setBackground(getDrawable(R.drawable.button2));
            }
        }
    }


    public void enterTopic(View view){
        Intent intent = new Intent(this, TopicActivity.class);
        String message = (String) view.getTag();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(mainLayout);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("ORIENTATION_LANDSCAPE");
            createGUI(Configuration.ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("ORIENTATION_PORTRAIT");
            createGUI(Configuration.ORIENTATION_LANDSCAPE);
        }
        setDefaultImageButtonSizes(newConfig.orientation, buttons);
    }

    private void setDefaultImageButtonSizes(int orientation, ArrayList<ImageButton> buttons) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        // ---------------------------------------modified
        int screenWidth;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            screenWidth = displayMetrics.widthPixels;
        } else {
            screenWidth = displayMetrics.heightPixels;
        }

        int idealSize = (screenWidth / 3);

        for(ImageButton imageButton : buttons){
            imageButton.getLayoutParams().width = idealSize;
            imageButton.getLayoutParams().height = idealSize;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learn, menu);
        return true;
    }

    //get video code from Shubham

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
