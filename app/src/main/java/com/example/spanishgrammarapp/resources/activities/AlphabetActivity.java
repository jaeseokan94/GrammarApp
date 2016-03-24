package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Letter;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Alphabet of chosen language, or Numbers or Days is created and shown here depending on the String
 * Extra. Letters are dynamically created and can be clicked to listen to the pronunciation.
 */
public class AlphabetActivity extends Activity {
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        APIWrapper apiWrapper = new APIWrapper(database);
        ArrayList<Letter> letters = new ArrayList<>();
        letters = apiWrapper.getLetters(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));

        GridView gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new LetterAdapter(this, letters));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_item_another_word))
                                .getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * Plays audio recording of letter being pronounced
     * @param view clicked letter_layout
     */
    public void playSound(View view) {
        Log.d("MainActivity","resource sound played" + (String)view.getTag());
        try {
            MediaPlayer player = new MediaPlayer();
            player.setDataSource((String) view.getTag());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(MainActivity.DIALECT, getIntent().getStringExtra(MainActivity.DIALECT));
        startActivity(intent);
    }
}
