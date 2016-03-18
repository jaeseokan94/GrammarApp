package com.example.spanishgrammarapp.resources.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by janaldoustorres on 18/03/2016.
 */
public class SeasonsAndMonthsActivity extends AppCompatActivity {
    private String resource;
    private ArrayList<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons_and_months);

        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                ResourcesActivity.DIALECT, resource);

        //Set up
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(resource + " - " + ResourcesActivity.DIALECT + " accent");

        TextView tvInstructions = (TextView) findViewById(R.id.tv_instructions);
        tvInstructions.setText(instructions);

        //Set up expandable list view
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);


        // preparing list data
        prepareListData();

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header data
        ArrayList<Season> seasons = APIWrapper.getSeasonsAndMonthsData(MainActivity.currentLanguage, ResourcesActivity.DIALECT);

        // Adding child data
        for(Season season: seasons) {
            String name = season.getSeason();
            listDataHeader.add(name);
            listDataChild.put(name, season.getMonths());
        }
    }

}
