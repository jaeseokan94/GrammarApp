package com.example.spanishgrammarapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.LanguageData;

import java.util.List;

public class LanguageActivity extends AppCompatActivity implements OnClickListener {
    public final static String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        database = new DatabaseHelper(this.getBaseContext());
        APIWrapper downloadAPI = new APIWrapper(database);
        downloadAPI.getLangLevelAPI();

        database.getLanguageList();


        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout_id);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        //This will pass subtopicList from Database
        List<LanguageData> languages = CMSconnector.getLanguages(getBaseContext(), database);

        for (LanguageData languageTitle: languages) {
            Button button = new Button(this);
            button.setText(languageTitle.toString());
            button.setOnClickListener(this);
            button.setBackground(getDrawable(R.drawable.button));

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                    );


            mainLayout.addView(button,p);
        }
    }

    /**
     * Run when subtopic button is clicked
     * @param view clicked button
     */
    public void onClick(View view){
        Intent intent = new Intent(this, LevelActivity.class);
        MainActivity.currentLanguage = ((TextView) view).getText().toString();
        startActivity(intent);
    }
}
