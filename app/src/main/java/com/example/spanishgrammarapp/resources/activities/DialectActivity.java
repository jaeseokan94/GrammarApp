package com.example.spanishgrammarapp.resources.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;

import java.util.ArrayList;

/**
 * This activity lets the user chose the dialect.
 */
public class DialectActivity extends Activity implements View.OnClickListener{
    public static String DIALECT = "Dialect";
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialect);

        Typeface font = Typeface.createFromAsset(getAssets(), "font2.ttf");
        TextView textView = ((TextView) findViewById(R.id.textView));
        textView.setTypeface(font);
        textView.setTextSize(25f);

        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.dialect_main);
        mainLayout.setBackgroundColor(Color.rgb(118, 178, 197));

        //Set up
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dialect);

        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        APIWrapper apiWrapper = new APIWrapper(database);

        ArrayList<String> dialects = apiWrapper.getDialects(MainActivity.currentLanguage);

        for (String dialect: dialects) {
            Button button = new Button(this);
            button.setText(dialect);
            button.setOnClickListener(this);
            button.setTag(dialect);
            button.setTypeface(font);
            button.setBackground(getDrawable(R.drawable.button));
            linearLayout.addView(button,pp);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(DIALECT, v.getTag().toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(MainActivity.DIALECT, getIntent().getStringExtra(MainActivity.DIALECT));
        startActivity(intent);
    }
}
