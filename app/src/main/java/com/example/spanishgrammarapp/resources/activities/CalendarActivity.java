package com.example.spanishgrammarapp.resources.activities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class CalendarActivity extends AppCompatActivity {
    private String resource;
    private MediaPlayer player;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO implement layout
        setContentView(R.layout.activity_calendar);

        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                ResourcesActivity.DIALECT, resource);

        //Set up
        TextView dayTitle = (TextView) findViewById(R.id.day_title);
        dayTitle.setText(resource + " - " + ResourcesActivity.DIALECT + " accent");

        TextView dayInstructions = (TextView) findViewById(R.id.day_instructions);
        dayInstructions.setText(instructions);

        APIWrapper apiWrapper = new APIWrapper(database);

        ArrayList<Numb> numbs = apiWrapper.getNumbs(MainActivity.currentLanguage, ResourcesActivity.DIALECT);

        //Dynamically create letters (with pronunciation and audioURL as tag) as views
        LinearLayout dayLayout = (LinearLayout) findViewById(R.id.calendar_layout);


        View dayView;
        for(Numb numb: numbs) {
            dayView = getLayoutInflater().inflate(R.layout.calendar_layout, dayLayout, false);

            dayView.setBackgroundColor(Color.YELLOW);
            dayLayout.addView(dayView);
            //TextView tvDay = (TextView)dayView.findViewById(R.id.day);
            Button tvDay = (Button)dayView.findViewById(R.id.day);
            TextView tvPronunciation = (TextView)dayView.findViewById(R.id.pronunciation_guide);

            tvDay.setText(numb.getNumber());
            tvPronunciation.setText(numb.getPronunciation());
            dayView.setTag(numb.getAudioURL());
        }
    }

    public void playSound(View view) {
        Log.d("MainActivity", "resource sound played" + (String) view.getTag());
        try {
            player = new MediaPlayer();
            player.setDataSource((String)view.getTag());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();
    }
}
