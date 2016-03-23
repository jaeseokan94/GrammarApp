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
import com.example.spanishgrammarapp.Data.LevelData;

import java.util.List;

public class LevelActivity extends Activity implements OnClickListener {
    private String currentLanguage;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        //Get current Language from intent
        currentLanguage = MainActivity.currentLanguage;

        //Set up
        //TextView textView = (TextView) findViewById(R.id.tv_level);
        //textView.setText(currentLanguage);
        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        TextView textViewTitle = (TextView) findViewById(R.id.tv_level_title);
        textViewTitle.setTypeface(font);
        textViewTitle.setTextSize(25f);

        ImageView imageView = new ImageView(this);
        imageView.setBackground(getDrawable(R.drawable.girl));

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.main_layout_id2);
        mainLayout.setBackgroundColor(Color.rgb(118, 178, 197));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_LEFT);

        mainLayout.addView(imageView, rlp);
        mainLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);



        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        APIWrapper apiWrapper = new APIWrapper(database);
        //This will pass subtopicList from Database
        List<LevelData> levels = apiWrapper.apiLevel();
        System.out.println("LEVEL LIST : " + levels);
        int i = 200;
        findViewById(R.id.tv_level_title).setId(i);
        for (LevelData Levels: levels) {
            Button button = new Button(this);
            button.setText(Levels.toString());
            button.setOnClickListener(this);
            button.setTypeface(font);
            button.setBackground(getDrawable(R.drawable.button));
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            p.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            p.addRule(RelativeLayout.BELOW, i++);
            button.setId(i);
            mainLayout.addView(button,p);
        }
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, LanguageActivity.class));
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        MainActivity.currentLevel = ((TextView) view).getText().toString();
        startActivity(intent);
    }
}
