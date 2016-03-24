package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.util.ArrayList;


public class NumberActivity extends Activity {
    private DatabaseHelper database;
    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        APIWrapper apiWrapper = new APIWrapper(database);
        ArrayList<Numb> numbs = new ArrayList<>();
        numbs = apiWrapper.getNumbs(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));

        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new WordAdapter(this, numbs));

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
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(MainActivity.DIALECT, getIntent().getStringExtra(MainActivity.DIALECT));
        startActivity(intent);
    }
}