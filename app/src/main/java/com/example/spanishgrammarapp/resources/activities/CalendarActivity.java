package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.example.spanishgrammarapp.resources.data.Numb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 */
public class CalendarActivity extends Activity {
    private String resource;
    private MediaPlayer player;
    private GridView gridView;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO implement layout
        setContentView(R.layout.activity_calendar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        APIWrapper apiWrapper = new APIWrapper(database);
        ArrayList<Numb> CalendarDay = new ArrayList<>();
        CalendarDay = apiWrapper.getNumbs(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));
        gridView = (GridView) findViewById(R.id.gridview);

        gridView.setAdapter(new WordAdapter(this, CalendarDay));

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











