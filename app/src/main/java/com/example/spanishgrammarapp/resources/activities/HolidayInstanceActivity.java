package com.example.spanishgrammarapp.resources.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spanishgrammarapp.R;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class HolidayInstanceActivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO implement layout
        setContentView(R.layout.activity_holidays);

        //Get intents
        String imgURL = getIntent().getStringExtra(HolidaysActivity.HOLIDAY_IMG);
        String date = getIntent().getStringExtra(HolidaysActivity.DATE);
        String nameInLanguage = getIntent().getStringExtra(HolidaysActivity.NAME_IN_LANGUAGE);
        String nameInEnglish = getIntent().getStringExtra(HolidaysActivity.NAME_IN_ENGLISH);

        //Set references to xml views
        ImageView ivImg = (ImageView) findViewById(R.id.iv_holiday_image);
        TextView tvDate = (TextView) findViewById(R.id.tv_date);
        TextView tvNameInLanguage = (TextView) findViewById(R.id.tv_name_in_language);
        TextView tvNameInEnglish = (TextView) findViewById(R.id.tv_name_english);

        //Set values to views
        //TODO make drawable into actual image
        //ivImg.setImageDrawable(getDrawable(R.drawable.b));
        //tvDate.setText(date);
        //tvNameInLanguage.setText(nameInLanguage);
        //tvNameInEnglish.setText(nameInEnglish);
    }
}
