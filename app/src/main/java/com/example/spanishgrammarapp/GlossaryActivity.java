package com.example.spanishgrammarapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * This Activity creates a Glossary Page with a Search Button and clickable Alphabet buttons
 */
public class GlossaryActivity extends Activity implements View.OnClickListener {

    private SearchView searchBar; // search bar for the glossary
    private TextView resultsview; // the results are shown here
    ArrayList<Glossary> glossData; // the glossary data
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.glossary_page);
        mainLayout.setBackgroundColor(Color.rgb(118, 178, 197));

        //getting the data for the glossary based on the current options
        database = new DatabaseHelper(this.getBaseContext());
        APIWrapper downloadAPI = new APIWrapper(database);
        glossData = downloadAPI.getGlossary(MainActivity.currentLanguage);

        //sets up the search bar to facilitate the search on the glossary
        SearchManager sManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchBar = (SearchView) findViewById(R.id.searchView);
        searchBar.setBackgroundColor(Color.WHITE);

        //sets up the results page
        resultsview = (TextView) findViewById(R.id.resultsView);
        resultsview.setTextSize(20);
        resultsview.setBackgroundColor(Color.WHITE);
        resultsview.setMovementMethod(new ScrollingMovementMethod());

        // this enables the search bar to update while typing and after submitting
        searchBar.setSearchableInfo(sManager.getSearchableInfo(getComponentName()));
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateSearch(query, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateSearch(newText, 0);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_glossary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     *This method uses the input String to filter out the glossary
     * @param input text to search in the glossary
     * @param integer value given to decide if button click or search bar
     */
    public void updateSearch(String input,Integer integer){

        ArrayList<Glossary> currentList = new ArrayList<>();

        if (integer==0){

            for (int i =0; i < glossData.size();i++){
                if(glossData.get(i).getWord().toLowerCase().contains(input.toLowerCase())){
                 currentList.add(glossData.get(i));
                 }
            }

        } else if(integer==1){

            for (int i =0;i<glossData.size();i++){
                if (glossData.get(i).getWord().toLowerCase().charAt(0)==input.toLowerCase().charAt(0)){
                    currentList.add(glossData.get(i));
                }
            }
        }

        //sorts the list of searched data alphabetically
        Collections.sort(currentList, new Comparator<Glossary>() {
            @Override
            public int compare(Glossary line1, Glossary line2) {
                return line1.toString().toLowerCase().compareTo(line2.toString().toLowerCase());
            }
        });

        //adding the data to the TextView
        String str = "";
        for (int i = 0;i<currentList.size();i++){

            str += currentList.get(i).toString() + "\n";

        }
        resultsview.setText(str);
        str = "";
        currentList.clear();

    }

    /**
     * based on the clicked button, the search bar will show the results respectively
     * @param v the button being clicked
     */
    @Override
    public void onClick(View v) {
        updateSearch(v.getTag().toString(),1);
    }
}
