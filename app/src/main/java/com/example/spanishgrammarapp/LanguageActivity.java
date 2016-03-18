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

public class LanguageActivity extends AppCompatActivity implements OnClickListener {
    public final static String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout_id);

        //This will pass subtopicList from Database
        ArrayList<String> languages = CMSconnector.getLanguages(getBaseContext(), CURRENT_LANGUAGE);



        for (String languageTitle: languages) {
            Button button = new Button(this);
            button.setText(languageTitle);
            button.setTag(languageTitle);
            button.setOnClickListener(this);
            mainLayout.addView(button);
        }
    }

    /**
     * Run when subtopic button is clicked
     * @param view clicked button
     */
    public void onClick(View view){
        Intent intent = new Intent(this, LevelActivity.class);
        ((Button) view).getText();
        String currentLanguage = ((TextView) view).getText().toString();
        intent.putExtra(CURRENT_LANGUAGE, currentLanguage);
        startActivity(intent);
    }


}
