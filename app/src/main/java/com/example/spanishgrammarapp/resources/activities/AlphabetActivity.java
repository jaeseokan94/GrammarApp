package com.example.spanishgrammarapp.resources.activities;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Letter;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Alphabet of chosen language, or Numbers or Days is created and shown here depending on the String
 * Extra. Letters are dynamically created and can be clicked to listen to the pronunciation.
 */
public class AlphabetActivity extends AppCompatActivity {
    private MediaPlayer player;
    private String resource;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);


        //Get resource name
        resource = getIntent().getStringExtra(ResourcesActivity.RESOURCE_NAME);

        //Get instructions from API
        String instructions = APIWrapper.getInstructions(MainActivity.currentLanguage,
                ResourcesActivity.DIALECT, resource);

        APIWrapper apiWrapper = new APIWrapper(database);
        ArrayList<Letter> letters = new ArrayList<>();
        letters = apiWrapper.getLetters(MainActivity.currentLanguage, ResourcesActivity.DIALECT);


            Button button1 = (Button) findViewById(R.id.button1);
            button1.setText(letters.get(0).getLetter() + "\n" + letters.get(0).getPronunciation());
            System.out.print(letters.get(0).getLetter() + "\n" + letters.get(0).getPronunciation());
            button1.setTag(letters.get(0).getAudioURL());
//
//            Button button2 = (Button) findViewById(R.id.button2);
//            button2.setText(letters.get(1).getLetter() + "\n" + letters.get(1).getPronunciation());
//            button2.setTag(letters.get(1).getAudioURL());
//
//            Button button3 = (Button) findViewById(R.id.button3);
//            button3.setText(letters.get(2).getLetter() + "\n" + letters.get(2).getPronunciation());
//            button3.setTag(letters.get(2).getAudioURL());

//        Button button4 = (Button) findViewById(R.id.button4);
//        button4.setText(letters.get(3).getLetter() + "\n" + letters.get(3).getPronunciation());
//        button4.setTag(letters.get(3).getAudioURL());
//
//        Button button5 = (Button) findViewById(R.id.button5);
//        button5.setText(letters.get(4).getLetter() + "\n" + letters.get(4).getPronunciation());
//        button5.setTag(letters.get(4).getAudioURL());
//
//        Button button6 = (Button) findViewById(R.id.button6);
//        button6.setText(letters.get(5).getLetter() + "\n" + letters.get(5).getPronunciation());
//        button6.setTag(letters.get(5).getAudioURL());
//
//        Button button7 = (Button) findViewById(R.id.button7);
//        button7.setText(letters.get(6).getLetter() + "\n" + letters.get(6).getPronunciation());
//        button7.setTag(letters.get(6).getAudioURL());
//
//        Button button8 = (Button) findViewById(R.id.button8);
//        button8.setText(letters.get(7).getLetter() + "\n" + letters.get(7).getPronunciation());
//        button8.setTag(letters.get(7).getAudioURL());
//
//        Button button9 = (Button) findViewById(R.id.button9);
//        button9.setText(letters.get(8).getLetter() + "\n" + letters.get(8).getPronunciation());
//        button9.setTag(letters.get(8).getAudioURL());
//
//        Button button10 = (Button) findViewById(R.id.button10);
//        button10.setText(letters.get(9).getLetter() + "\n" + letters.get(9).getPronunciation());
//        button10.setTag(letters.get(9).getAudioURL());
//
//        Button button11 = (Button) findViewById(R.id.button11);
//        button11.setText(letters.get(10).getLetter() + "\n" + letters.get(10).getPronunciation());
//        button11.setTag(letters.get(10).getAudioURL());
//
//        Button button12 = (Button) findViewById(R.id.button12);
//        button12.setText(letters.get(11).getLetter() + "\n" + letters.get(11).getPronunciation());
//        button12.setTag(letters.get(11).getAudioURL());
//
//        Button button13 = (Button) findViewById(R.id.button13);
//        button13.setText(letters.get(12).getLetter() + "\n" + letters.get(12).getPronunciation());
//        button13.setTag(letters.get(12).getAudioURL());
//
//        Button button14 = (Button) findViewById(R.id.button14);
//        button14.setText(letters.get(13).getLetter() + "\n" + letters.get(13).getPronunciation());
//        button14.setTag(letters.get(13).getAudioURL());
//
//        Button button15 = (Button) findViewById(R.id.button15);
//        button15.setText(letters.get(14).getLetter() + "\n" + letters.get(14).getPronunciation());
//        button15.setTag(letters.get(14).getAudioURL());
//
//        Button button16 = (Button) findViewById(R.id.button16);
//        button16.setText(letters.get(15).getLetter() + "\n" + letters.get(15).getPronunciation());
//        button16.setTag(letters.get(15).getAudioURL());
//
//        Button button17 = (Button) findViewById(R.id.button17);
//        button17.setText(letters.get(16).getLetter() + "\n" + letters.get(16).getPronunciation());
//        button17.setTag(letters.get(16).getAudioURL());
//
//        Button button18 = (Button) findViewById(R.id.button18);
//        button18.setText(letters.get(17).getLetter() + "\n" + letters.get(17).getPronunciation());
//        button18.setTag(letters.get(17).getAudioURL());
//
//        Button button19 = (Button) findViewById(R.id.button19);
//        button19.setText(letters.get(18).getLetter() + "\n" + letters.get(18).getPronunciation());
//        button19.setTag(letters.get(18).getAudioURL());
//
//        Button button20 = (Button) findViewById(R.id.button20);
//        button20.setText(letters.get(19).getLetter() + "\n" + letters.get(19).getPronunciation());
//        button20.setTag(letters.get(19).getAudioURL());
//
//        Button button21 = (Button) findViewById(R.id.button21);
//        button21.setText(letters.get(20).getLetter() + "\n" + letters.get(20).getPronunciation());
//        button21.setTag(letters.get(20).getAudioURL());
//
//        Button button22 = (Button) findViewById(R.id.button22);
//        button22.setText(letters.get(21).getLetter() + "\n" + letters.get(21).getPronunciation());
//        button22.setTag(letters.get(21).getAudioURL());
//
//        Button button23 = (Button) findViewById(R.id.button23);
//        button23.setText(letters.get(22).getLetter() + "\n" + letters.get(22).getPronunciation());
//        button23.setTag(letters.get(22).getAudioURL());
//
//        Button button24 = (Button) findViewById(R.id.button24);
//        button24.setText(letters.get(23).getLetter() + "\n" + letters.get(23).getPronunciation());
//        button24.setTag(letters.get(23).getAudioURL());
//
//        Button button25 = (Button) findViewById(R.id.button25);
//        button25.setText(letters.get(24).getLetter() + "\n" + letters.get(24).getPronunciation());
//        button25.setTag(letters.get(24).getAudioURL());
//
//        Button button26 = (Button) findViewById(R.id.button26);
//        button26.setText(letters.get(25).getLetter() + "\n" + letters.get(25).getPronunciation());
//        button26.setTag(letters.get(25).getAudioURL());
//
//        Button button27 = (Button) findViewById(R.id.button27);
//        button27.setText(letters.get(26).getLetter() + "\n" + letters.get(26).getPronunciation());
//        button27.setTag(letters.get(26).getAudioURL());
//
//        Button button28 = (Button) findViewById(R.id.button28);
//        button28.setText(letters.get(27).getLetter() + "\n" + letters.get(27).getPronunciation());
//        button28.setTag(letters.get(27).getAudioURL());
//
//        Button button29 = (Button) findViewById(R.id.button29);
//        button29.setText(letters.get(28).getLetter() + "\n" + letters.get(28).getPronunciation());
//        button29.setTag(letters.get(28).getAudioURL());
//
//        }else if(resource.equals(("Days"))){;
//            setContentView(R.layout.activity_day);
//            ArrayList<Day> days = apiWrapper.getDays(MainActivity.currentLanguage, ResourcesActivity.DIALECT);
//
//            Button button1 = (Button) findViewById(R.id.button1);
//            button1.setText(days.get(0).getDay() + "\n" + days.get(0).getPronunciation());
//            System.out.print(days.get(0).getDay() + "\n" + days.get(0).getPronunciation());
//            button1.setTag(days.get(0).getAudioURL());
//
//            Button button2 = (Button) findViewById(R.id.button2);
//            button2.setText(days.get(1).getDay() + "\n" + days.get(1).getPronunciation());
//            button2.setTag(days.get(1).getAudioURL());
//
//            Button button3 = (Button) findViewById(R.id.button3);
//            button3.setText(days.get(2).getDay() + "\n" + days.get(2).getPronunciation());
//            button3.setTag(days.get(2).getAudioURL());

//        Button button4 = (Button) findViewById(R.id.button4);
//        button4.setText(letters.get(3).getLetter() + "\n" + letters.get(3).getPronunciation());
//        button4.setTag(letters.get(3).getAudioURL());
//
//        Button button5 = (Button) findViewById(R.id.button5);
//        button5.setText(letters.get(4).getLetter() + "\n" + letters.get(4).getPronunciation());
//        button5.setTag(letters.get(4).getAudioURL());
//
//        Button button6 = (Button) findViewById(R.id.button6);
//        button6.setText(letters.get(5).getLetter() + "\n" + letters.get(5).getPronunciation());
//        button6.setTag(letters.get(5).getAudioURL());
//
//        Button button7 = (Button) findViewById(R.id.button7);
//        button7.setText(letters.get(6).getLetter() + "\n" + letters.get(6).getPronunciation());
//        button7.setTag(letters.get(6).getAudioURL());
//        }else
//            setContentView(R.layout.activity_ca);
//
//








    }


    /**
     * Plays audio recording of letter being pronounced
     *
     * @param view clicked letter_layout
     */

    public void playSound(View view) {
        Log.d("MainActivity", "resource sound played" + (String) view.getTag());
        try {
            player = new MediaPlayer();
            player.setDataSource((String) view.getTag());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();
    }
}

