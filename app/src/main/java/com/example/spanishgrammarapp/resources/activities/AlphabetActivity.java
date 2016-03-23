package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Letter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Alphabet of chosen language, or Numbers or Days is created and shown here depending on the String
 * Extra. Letters are dynamically created and can be clicked to listen to the pronunciation.
 */
public class AlphabetActivity extends Activity {
    private MediaPlayer player;
    private String resource;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                ResourcesActivity.DIALECT, resource);

        //Set up
        TextView tvTitle = (TextView) findViewById(R.id.tv_alphabet_title);
        tvTitle.setText(resource + " - " + ResourcesActivity.DIALECT + " accent");

        TextView tvInstructions = (TextView) findViewById(R.id.tv_alphabet_instructions);
        tvInstructions.setText(instructions);

        //TODO put if statement or change getLetters to only return Language
        //Get set of Letters from API

        APIWrapper apiWrapper = new APIWrapper(database);

        ArrayList<Letter> letters = apiWrapper.getLetters(MainActivity.currentLanguage, ResourcesActivity.DIALECT);

        //Dynamically create letters (with pronunciation and audioURL as tag) as views
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.alphabet_layout);

        View letterView;
       for(Letter letter: letters) {
            letterView = getLayoutInflater().inflate(R.layout.letter_layout, mainLayout, false);
            letterView.setBackgroundColor(Color.YELLOW);
            mainLayout.addView(letterView);
            TextView tvLetter = (TextView)letterView.findViewById(R.id.letter);
            TextView tvPronunciation = (TextView)letterView.findViewById(R.id.pronunciation_guide);

            tvLetter.setText(letter.getLetter());
            tvPronunciation.setText(letter.getPronunciation());
            letterView.setTag(letter.getAudioURL());
        }
    }

    /**
     * Plays audio recording of letter being pronounced
     * @param view clicked letter_layout
     */
    public void playSound(View view) {
        Log.d("MainActivity","resource sound played" + (String)view.getTag());
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
