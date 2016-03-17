package com.example.spanishgrammarapp.resources.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.APIWrapper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Holiday;

import java.util.ArrayList;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class HolidaysActivity extends AppCompatActivity implements View.OnClickListener{
    public static String HOLIDAY_IMG = "holidayImg";
    public static String DATE = "date";
    public static String NAME_IN_LANGUAGE = "nameInLanguage";
    public static String NAME_IN_ENGLISH = "nameInEnglish";
    private String resource;
    private ArrayList<Holiday> holidays;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO implement layout
        setContentView(R.layout.activity_holidays);

        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.LANGUAGE,
                ResourcesActivity.DIALECT, resource);

        //Set up
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(resource + " - " + ResourcesActivity.DIALECT + " accent");

        TextView tvInstructions = (TextView) findViewById(R.id.tv_instructions);
        tvInstructions.setText(instructions);

        //Get set of Holidays from API
        holidays = APIWrapper.getHolidays(MainActivity.LANGUAGE, ResourcesActivity.DIALECT);

        //Dynamically create images of holidays
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.holiday_layout);

        for(Holiday holiday: holidays) {
            ImageView ivHoliday = new ImageView(getBaseContext());
            //TODO Make it create images dynamically
            ivHoliday.setImageDrawable(getDrawable(R.drawable.a));
            ivHoliday.setMaxHeight(10);
            ivHoliday.setMaxWidth(10);
            ivHoliday.setTag(holiday);
            ivHoliday.setOnClickListener(this);
            mainLayout.addView(ivHoliday);
        }
    }

    @Override
    public void onClick(View v) {
        //Sends intent with contents of view's Holiday object
        Intent intent = new Intent(this, HolidayInstanceActivity.class);
        intent.putExtra(HOLIDAY_IMG, ((Holiday)v.getTag()).getImageURL());
        intent.putExtra(DATE, ((Holiday)v.getTag()).getDate());
        intent.putExtra(NAME_IN_ENGLISH, ((Holiday)v.getTag()).getName_English());
        intent.putExtra(NAME_IN_LANGUAGE, ((Holiday)v.getTag()).getName_InLanguage());

        Log.e("asdf", ((Holiday)v.getTag()).getImageURL());
        Log.e("asdf", ((Holiday)v.getTag()).getDate());
        Log.e("asdf", ((Holiday)v.getTag()).getName_English());

        startActivity(intent);
    }
}