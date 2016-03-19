package com.example.spanishgrammarapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.example.spanishgrammarapp.resources.activities.CalendarActivity;
import com.example.spanishgrammarapp.resources.activities.AlphabetActivity;
import com.example.spanishgrammarapp.resources.activities.DialectActivity;
import com.example.spanishgrammarapp.resources.activities.HolidaysActivity;
import com.example.spanishgrammarapp.resources.activities.SeasonsAndMonthsActivity;

public class ResourcesActivity extends AppCompatActivity {
    public static String DIALECT = "";
    public static String RESOURCE_NAME = "Resource";
    public static String ALPHABET = "Alphabet";
    public static String NUMBERS = "Numbers";
    public static String DAYS = "Days";
    public static String CALENDAR = "Calendar";
    public static String HOLIDAYS = "Holidays";
    public static String SEASONS_MONTHS = "Seasons and Months";
    public static String TIME = "Time";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        DIALECT = getIntent().getStringExtra(DialectActivity.DIALECT);
    }

    public void showAlphabetActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, ALPHABET);
        startActivity(intent);
    }

    public void showNumbersActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, NUMBERS);
        startActivity(intent);
    }

    public void showDaysActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, DAYS);
        startActivity(intent);
    }

    public void showCalendarActivity(View v){
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra(RESOURCE_NAME, CALENDAR);
        startActivity(intent);
    }

    public void showHolidaysActivity(View v){
        Intent intent = new Intent(this, HolidaysActivity.class);
        intent.putExtra(RESOURCE_NAME, HOLIDAYS);
        startActivity(intent);
    }

    public void showSeasonsAndMonthsActivity(View v) {
        Intent intent = new Intent(this, SeasonsAndMonthsActivity.class);
        intent.putExtra(RESOURCE_NAME, SEASONS_MONTHS);
        startActivity(intent);
    }

    public void showTimeActivity(View v) {
        Intent intent = new Intent(this, SeasonsAndMonthsActivity.class);
        intent.putExtra(RESOURCE_NAME, TIME);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
        return true;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_resources);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("ORIENTATION_LANDSCAPE");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("ORIENTATION_PORTRAIT");
        }
    }
}
