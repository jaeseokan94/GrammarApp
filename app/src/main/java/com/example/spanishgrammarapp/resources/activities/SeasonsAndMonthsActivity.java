package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Season;
import com.example.spanishgrammarapp.resources.data.Time;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by janaldoustorres on 18/03/2016.
 */
public class SeasonsAndMonthsActivity extends Activity {
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private DatabaseHelper database;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_and_months);

        //Get resource name
        String resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                getIntent().getStringExtra(MainActivity.DIALECT), resource);

        //Set up
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(resource + " - " + getIntent().getStringExtra(MainActivity.DIALECT) + " accent.");

        TextView tvInstructions = (TextView) findViewById(R.id.tv_instructions);
        tvInstructions.setText(instructions);

        imageView = (ImageView) findViewById(R.id.iv_seasons_months);

        //Set up expandable list view
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME).equals(ResourcesActivity.SEASONS_MONTHS)) {
                    String season = "spring";
                    switch (groupPosition) {
                        case 0:
                            season = "spring";
                            break;
                        case 1:
                            season = "summer";
                            break;
                        case 2:
                            season = "autumn";
                            break;
                        case 3:
                            season = "winter";
                            break;
                    }
                    setImageBackground(season);
                }else{
                    setClockBackground();
                }
                return false;
            }
        });


    // preparing list data
        if (resource.equals(ResourcesActivity.SEASONS_MONTHS)) {
            prepareSeasonsListData();
        } else {
            prepareTimeListData();
        }

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
    }

    private void setClockBackground(){
        imageView.setBackground(getDrawable(R.drawable.clock1));
    }

    private void setImageBackground(String season){
        switch (season){
            case "spring":
                imageView.setBackground(getDrawable(R.drawable.spring));
                break;
            case "summer":
                imageView.setBackground(getDrawable(R.drawable.spring));
                break;
            case "autumn":
                imageView.setBackground(getDrawable(R.drawable.autumn));
                break;
            case "winter":
                imageView.setBackground(getDrawable(R.drawable.winter));
                break;
        }
    }

    private void prepareSeasonsListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        APIWrapper apiWrapper = new APIWrapper(new DatabaseHelper(this.getBaseContext()));
        // Adding header data
        ArrayList<Season> seasons = apiWrapper.getSeasonsAndMonthsData(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));

        // Adding child data
        for(Season season: seasons) {
            String name = season.getSeason();
            listDataHeader.add(name);
            listDataChild.put(name, season.getMonths());
//            Log.d("season", season.getSeason());
        }
    }

    private void prepareTimeListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header data
        APIWrapper apiWrapper = new APIWrapper(new DatabaseHelper(this));
        ArrayList<Time> times = apiWrapper.getTimeData(MainActivity.currentLanguage, getIntent().getStringExtra(MainActivity.DIALECT));

        // Adding child data
        for(Time time: times) {
            String time_digital = time.getTime_digital();
            listDataHeader.add(time_digital);
            List<String> item = new ArrayList<String>();
            item.add(time.getTime_language());
            listDataChild.put(time_digital, item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(MainActivity.DIALECT, getIntent().getStringExtra(MainActivity.DIALECT));
        startActivity(intent);
    }
}
