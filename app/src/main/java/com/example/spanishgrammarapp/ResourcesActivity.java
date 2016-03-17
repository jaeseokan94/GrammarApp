package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.example.spanishgrammarapp.resources.activities.AlphabetActivity;

public class ResourcesActivity extends AppCompatActivity {
    public static String DIALECT = "Mexican";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
    }

    public void showAlphabetActivity(View v){
        Intent intent = new Intent(this, AlphabetActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
        return true;
    }
}
