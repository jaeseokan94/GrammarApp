package com.example.spanishgrammarapp.resources.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class HolidaysActivity extends AppCompatActivity {
    private String resource;

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
        ArrayList<Holiday> holidays = APIWrapper.getHolidays(MainActivity.LANGUAGE, ResourcesActivity.DIALECT);

        //Dynamically create images of holidays
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.holiday_layout);

        for(Holiday holiday: holidays) {
            /* code from http://stackoverflow.com/questions/6407324/how-to-get-image-from-url-in-android */
            ImageView ivHoliday = new ImageView(getBaseContext());
//            InputStream is = null;
//            try {
//                is = (InputStream) new URL(holiday.getImageURL()).getContent();
//                Drawable d = Drawable.createFromStream(is, "src name");
//                ivHoliday.setImageDrawable(d);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //TODO Make it create images dynamically
            ivHoliday.setImageDrawable(getDrawable(R.drawable.a));
            ivHoliday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
            ivHoliday.setMaxHeight(10);
            ivHoliday.setMaxWidth(10);
            mainLayout.addView(ivHoliday);
        }
    }
}