package com.example.spanishgrammarapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class ResourcesActivity extends AppCompatActivity {
    //URl audio address variable
    String Resource_Url[] = {"http://sites.google.com/site/ubiaccessmobile/sample_audio.amr"}; //for temporary address will change later
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
    }

    public void onButton1Clicked(View v){

        Log.d("MainActivity","resource sound played");
        try {
            player = new MediaPlayer();
            player.setDataSource(Resource_Url[0]);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Start the Source", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resources, menu);
        return true;
    }


}
