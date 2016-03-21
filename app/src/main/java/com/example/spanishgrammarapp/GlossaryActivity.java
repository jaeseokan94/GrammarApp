package com.example.spanishgrammarapp;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

/**
 * This Activity creates a Glossary Page with a Search Button and clickable Alphabet buttons
 */
public class GlossaryActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchView searchBar; // search bar for the glossary
    private TextView resultsview; // the results are shown here
    // This is just a sample data for testing
    String[] strAry = {"Apple","Ball","Cat","Dog","Egg","Fat","Green","Horse","Ink","Joke","King","Lamp","Mum","Night","Oil","Puddle","Queen","Rabbit","Spoon","Tree","Umbrella","Van","Wet","X-ray","Yoga","Zebra"};
    ArrayList<Glossary> glossData;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);

        //getting the data for the glossary
        database = new DatabaseHelper(this.getBaseContext());
        APIWrapper downloadAPI = new APIWrapper(database);
        glossData = downloadAPI.getGlossary(LanguageActivity.CURRENT_LANGUAGE);
//        resultsview.setText(""+glossData.get(0).getWord());

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
//                updateSearch(query, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                updateSearch(newText, 0);
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

        //Based on the input and how theThe actual data is given , i can sort the data

        String str = "";

        if (integer==0){

            for (int i =0; i < glossData.size();i++){
                if(glossData.get(i).getWord().toLowerCase().contains(input.toLowerCase())){
                 str += glossData.toString() + "\n";
                 }
            }

        resultsview.setText(str);
            str = "";

        } else if(integer==1){

            for (int i =0;i<glossData.size();i++){
                if (glossData.get(i).getWord().toLowerCase().charAt(0)==input.toLowerCase().charAt(0)){
                    str += glossData.toString() + "\n";
                }
            }

            resultsview.setText(str);
            str = "";

        }

    }

    /**
     * based on the clicked button, the search bar will show the results respectively
     * @param v the button being clicked
     */
    @Override
    public void onClick(View v) {
//        updateSearch(v.getTag().toString(),1);
    }
}
