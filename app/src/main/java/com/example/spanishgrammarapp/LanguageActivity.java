package com.example.spanishgrammarapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.LanguageData;

import java.util.List;

public class LanguageActivity extends Activity implements OnClickListener {
    public final static String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";

    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        database = new DatabaseHelper(this.getBaseContext());
        APIWrapper downloadAPI = new APIWrapper(database);
        downloadAPI.getLangLevelAPI();

        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        TextView textView = ((TextView) findViewById(R.id.tv_language));
        textView.setTypeface(font);
        textView.setTextSize(25f);

        ImageView imageView = new ImageView(this);
        imageView.setBackground(getDrawable(R.drawable.girl));

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout_id);
        mainLayout.setBackgroundColor(Color.rgb(118,178,197));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_LEFT);

        mainLayout.addView(imageView, rlp);
        //mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);

        database.getLanguageList();



        //This will pass subtopicList from Database
        List<LanguageData> languages = CMSconnector.getLanguages(getBaseContext(), database);
        int i = 200;
        findViewById(R.id.tv_language).setId(i);
        for (LanguageData languageTitle : languages) {
            Button button = new Button(this);
            button.setText(languageTitle.toString());
            button.setOnClickListener(this);
            button.setBackground(getDrawable(R.drawable.button));
            button.setTypeface(font);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            p.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            p.addRule(RelativeLayout.BELOW, i++);
            button.setId(i);
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
