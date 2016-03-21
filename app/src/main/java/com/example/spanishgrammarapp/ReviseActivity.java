package com.example.spanishgrammarapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This Activity displays all the
 */
public class ReviseActivity extends Activity implements AdapterView.OnItemClickListener{

    RelativeLayout mainLayout; //This is the layout for this Activity
    Context context; // This is the Context of this Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        context = this;

        /* This sets up the current Activity to show all the completed Exercises on a list View*/
        showListView();
    }

    /**
     * This creates a listview of all the completed exercises based on correctness rating
     */
    public void showListView(){

        ListView listView = new ListView(this);

        ArrayList<Exercise> exerciseList = new ArrayList<>(UserProgress.completedExercises);

        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise1, Exercise exercise2) {
                if (exercise1.getCorrectnessRating() >= exercise2.getCorrectnessRating()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        ArrayAdapter<Exercise> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,exerciseList);

        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        mainLayout.addView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_revise, menu);
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

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {

        Exercise curExe = (Exercise) parent.getAdapter().getItem(position);
        final String[] identifier = curExe.getIdentifier().split("/");

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Do you want to Attempt this Exercise?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, ExercisesActivity.class);
                intent.putExtra(MainActivity.TOPIC, identifier[0]);
                intent.putExtra(MainActivity.SUBTOPIC, identifier[1]);
                intent.putExtra("RESET", "true"); //this is for me, so when you start your Exercise from THIS class, I'll know to reset the exercise rather than ask user to reset it.
                startActivity(intent);
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            //do nothing
            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }
}
