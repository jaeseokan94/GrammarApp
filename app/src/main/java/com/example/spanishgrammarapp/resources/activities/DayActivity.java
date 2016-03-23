package com.example.spanishgrammarapp.resources.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Letter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Alphabet of chosen language, or Numbers or Days is created and shown here depending on the String
 * Extra. Letters are dynamically created and can be clicked to listen to the pronunciation.
 */
public class DayActivity extends AppCompatActivity {
    private GridView gridView;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        APIWrapper apiWrapper = new APIWrapper(database);
        ArrayList<Day> days = new ArrayList<>();
        days = apiWrapper.getDays(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));


        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new DayAdapter(this, days));

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

}

