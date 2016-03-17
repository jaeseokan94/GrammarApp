package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.example.spanishgrammarapp.resources.activities.CalendarActivity;
import com.example.spanishgrammarapp.resources.activities.AlphabetActivity;

public class ResourcesActivity extends AppCompatActivity {
    public static String DIALECT = "Mexican";
    public static String RESOURCE_NAME = "Resource";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
    }

    public void showAlphabetActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, "Alphabet");
        startActivity(intent);
    }

    public void showNumbersActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, "Numbers");
        startActivity(intent);
    }

    public void showDaysActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        intent.putExtra(RESOURCE_NAME, "Days");
        startActivity(intent);
    }

    public void showCalendarActivity(View v){
        Intent intent = new Intent(this, CalendarActivity.class);
        intent.putExtra(RESOURCE_NAME, "Calendar");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
        return true;
    }
}
