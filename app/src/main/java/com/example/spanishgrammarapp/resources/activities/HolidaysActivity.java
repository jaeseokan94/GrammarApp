package com.example.spanishgrammarapp.resources.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Holiday;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class HolidaysActivity extends Activity {
    private DatabaseHelper database;
    GridView gridView;

    APIWrapper apiWrapper = new APIWrapper(database);
    //ArrayList<Holiday> holidays = apiWrapper.getHolidays(MainActivity.currentLanguage, ResourcesActivity.DIALECT);
    ArrayList<Holiday> holidays = new ArrayList<Holiday>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.holiday_layout);

        gridView = (GridView) findViewById(R.id.gridView_holiday);

        gridView.setAdapter(new ImageAdapter(this, holidays));
        System.out.print(holidays);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        ((TextView) v.findViewById(R.id.grid_holiday_label))
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