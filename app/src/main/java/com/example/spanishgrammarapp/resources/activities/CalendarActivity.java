package com.example.spanishgrammarapp.resources.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class CalendarActivity extends AppCompatActivity {
    private String resource;
    private MediaPlayer player;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO implement layout
        setContentView(R.layout.activity_calendar);

        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                ResourcesActivity.DIALECT, resource);

        APIWrapper apiWrapper = new APIWrapper(database);

        ArrayList<Numb> numbs = apiWrapper.getNumbs(MainActivity.currentLanguage, ResourcesActivity.DIALECT);

            Button button1 = (Button) findViewById(R.id.button1);
            button1.setText(numbs.get(0).getNumber()+"\n"+numbs.get(0).getPronunciation());
            button1.setTag(numbs.get(0).getAudioURL());

            Button button2 = (Button) findViewById(R.id.button2);
            button2.setText(numbs.get(1).getNumber()+"\n"+numbs.get(1).getPronunciation());
            button2.setTag(numbs.get(1).getAudioURL());

//            Button button3 = (Button) findViewById(R.id.button3);
//            button3.setText(numbs.get(2).getNumber()+"\n"+numbs.get(2).getPronunciation());
//            button3.setTag(numbs.get(2).getAudioURL());
//
//            Button button4 = (Button) findViewById(R.id.button4);
//            button4.setText(numbs.get(3).getNumber()+"\n"+numbs.get(3).getPronunciation());
//            button4.setTag(numbs.get(3).getAudioURL());
//
//            Button button5 = (Button) findViewById(R.id.button5);
//            button5.setText(numbs.get(4).getNumber()+"\n"+numbs.get(4).getPronunciation());
//            button5.setTag(numbs.get(4).getAudioURL());
//
//            Button button6 = (Button) findViewById(R.id.button6);
//            button6.setText(numbs.get(5).getNumber()+"\n"+numbs.get(5).getPronunciation());
//            button6.setTag(numbs.get(5).getAudioURL());
//
//            Button button7 = (Button) findViewById(R.id.button7);
//            button7.setText(numbs.get(6).getNumber()+"\n"+numbs.get(6).getPronunciation());
//            button7.setTag(numbs.get(6).getAudioURL());
//
//
//            Button button8 = (Button) findViewById(R.id.button8);
//            button8.setText(numbs.get(7).getNumber()+"\n"+numbs.get(7).getPronunciation());
//            button8.setTag(numbs.get(7).getAudioURL());
//
//            Button button9 = (Button) findViewById(R.id.button9);
//            button9.setText(numbs.get(8).getNumber()+"\n"+numbs.get(8).getPronunciation());
//            button9.setTag(numbs.get(8).getAudioURL());
//
//            Button button10 = (Button) findViewById(R.id.button10);
//            button10.setText(numbs.get(9).getNumber()+"\n"+numbs.get(9).getPronunciation());
//            button10.setTag(numbs.get(9).getAudioURL());
//
//            Button button11 = (Button) findViewById(R.id.button11);
//            button11.setText(numbs.get(10).getNumber()+"\n"+numbs.get(10).getPronunciation());
//            button11.setTag(numbs.get(10).getAudioURL());
//
//            Button button12 = (Button) findViewById(R.id.button12);
//            button12.setText(numbs.get(11).getNumber()+"\n"+numbs.get(11).getPronunciation());
//            button12.setTag(numbs.get(11).getAudioURL());
//
//            Button button13 = (Button) findViewById(R.id.button13);
//            button13.setText(numbs.get(12).getNumber()+"\n"+numbs.get(12).getPronunciation());
//            button13.setTag(numbs.get(12).getAudioURL());
//
//            Button button14 = (Button) findViewById(R.id.button14);
//            button14.setText(numbs.get(13).getNumber()+"\n"+numbs.get(1).getPronunciation());
//            button14.setTag(numbs.get(13).getAudioURL());
//
//            Button button15 = (Button) findViewById(R.id.button15);
//            button15.setText(numbs.get(14).getNumber()+"\n"+numbs.get(14).getPronunciation());
//            button15.setTag(numbs.get(14).getAudioURL());
//
//            Button button16 = (Button) findViewById(R.id.button16);
//            button16.setText(numbs.get(15).getNumber()+"\n"+numbs.get(15).getPronunciation());
//            button16.setTag(numbs.get(15).getAudioURL());
//
//            Button button17 = (Button) findViewById(R.id.button17);
//            button17.setText(numbs.get(16).getNumber()+"\n"+numbs.get(16).getPronunciation());
//            button17.setTag(numbs.get(16).getAudioURL());
//
//            Button button18 = (Button) findViewById(R.id.button18);
//            button18.setText(numbs.get(17).getNumber()+"\n"+numbs.get(17).getPronunciation());
//            button18.setTag(numbs.get(17).getAudioURL());
//
//            Button button19 = (Button) findViewById(R.id.button19);
//            button19.setText(numbs.get(18).getNumber()+"\n"+numbs.get(18).getPronunciation());
//            button19.setTag(numbs.get(18).getAudioURL());
//
//            Button button20 = (Button) findViewById(R.id.button20);
//            button20.setText(numbs.get(19).getNumber()+"\n"+numbs.get(19).getPronunciation());
//            button20.setTag(numbs.get(19).getAudioURL());
//
//            Button button21 = (Button) findViewById(R.id.button21);
//            button21.setText(numbs.get(20).getNumber()+"\n"+numbs.get(20).getPronunciation());
//            button21.setTag(numbs.get(20).getAudioURL());
//
//            Button button22 = (Button) findViewById(R.id.button22);
//            button22.setText(numbs.get(21).getNumber()+"\n"+numbs.get(21).getPronunciation());
//            button22.setTag(numbs.get(21).getAudioURL());
//
//            Button button23 = (Button) findViewById(R.id.button23);
//            button23.setText(numbs.get(22).getNumber()+"\n"+numbs.get(22).getPronunciation());
//            button23.setTag(numbs.get(22).getAudioURL());
//
//            Button button24 = (Button) findViewById(R.id.button24);
//            button24.setText(numbs.get(23).getNumber()+"\n"+numbs.get(23).getPronunciation());
//            button24.setTag(numbs.get(23).getAudioURL());
//
//            Button button25 = (Button) findViewById(R.id.button25);
//            button25.setText(numbs.get(24).getNumber()+"\n"+numbs.get(24).getPronunciation());
//            button25.setTag(numbs.get(24).getAudioURL());
//
//            Button button26 = (Button) findViewById(R.id.button26);
//            button26.setText(numbs.get(25).getNumber()+"\n"+numbs.get(25).getPronunciation());
//            button26.setTag(numbs.get(25).getAudioURL());
//
//            Button button27 = (Button) findViewById(R.id.button27);
//            button27.setText(numbs.get(26).getNumber()+"\n"+numbs.get(26).getPronunciation());
//            button27.setTag(numbs.get(26).getAudioURL());
//
//            Button button28 = (Button) findViewById(R.id.button28);
//            button28.setText(numbs.get(27).getNumber()+"\n"+numbs.get(27).getPronunciation());
//            button28.setTag(numbs.get(27).getAudioURL());
//
//            Button button29 = (Button) findViewById(R.id.button29);
//            button29.setText(numbs.get(28).getNumber()+"\n"+numbs.get(28).getPronunciation());
//            button29.setTag(numbs.get(28).getAudioURL());
//
//            Button button30 = (Button) findViewById(R.id.button30);
//            button30.setText(numbs.get(29).getNumber()+"\n"+numbs.get(29).getPronunciation());
//            button30.setTag(numbs.get(29).getAudioURL());
//
//            Button button31 = (Button) findViewById(R.id.button31);
//            button31.setText(numbs.get(30).getNumber()+"\n"+numbs.get(30).getPronunciation());
//            button31.setTag(numbs.get(30).getAudioURL());
    }

    public void playSound(View view){

            Log.d("MainActivity", "resource sound played" + (String) view.getTag());
            try {
                player = new MediaPlayer();
                player.setDataSource((String)view.getTag());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();


    }


    }
