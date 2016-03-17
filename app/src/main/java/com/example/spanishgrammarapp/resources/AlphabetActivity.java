package com.example.spanishgrammarapp.resources;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.R;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class AlphabetActivity extends AppCompatActivity {
    //URl audio address variable
    //String Resource_Url[] = {"http://sites.google.com/site/ubiaccessmobile/sample_audio.amr"}; //for temporary address will change later
    //MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.alphabet_layout);



        View letterView;
        for(int i = 0; i < 10; i++) {
            letterView = getLayoutInflater().inflate(R.layout.letter_layout, mainLayout, false);
            mainLayout.addView(letterView);
            TextView label =
                    (TextView)letterView.findViewById(R.id.letter);
            label.setText(Integer.toString(i));
        }
    }

    public void playSound(View view) {
        //        Log.d("MainActivity","resource sound played");
//        try {
//            player = new MediaPlayer();
//            player.setDataSource(Resource_Url[0]);
//            player.prepare();
//            player.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();
    }
}
