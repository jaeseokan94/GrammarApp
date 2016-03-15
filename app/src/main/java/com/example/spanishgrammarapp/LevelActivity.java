package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity implements OnClickListener {
    public final static String CURRENT_LEVEL = "CURRENT_LEVEL";
    public final static String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
    private String currentLanguage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //Get current Language from intent
        currentLanguage = getIntent().getStringExtra("CURRENT_LANGUAGE");

        //Set up
        TextView textView = (TextView) findViewById(R.id.tv_level);
        textView.setText(currentLanguage);



        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout_id);

        //This will pass subtopicList from Database
        ArrayList<String> levels = CMSconnector.getLevels(getBaseContext(), CURRENT_LEVEL);

        for (String Levels: levels) {
            Button button = new Button(this);
            button.setText(Levels);
            button.setOnClickListener(this);
            mainLayout.addView(button);
        }
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        String currentLevel = ((TextView) view).getText().toString();
        intent.putExtra(CURRENT_LEVEL, currentLevel);
        intent.putExtra(CURRENT_LANGUAGE, currentLanguage);
        startActivity(intent);
    }
}