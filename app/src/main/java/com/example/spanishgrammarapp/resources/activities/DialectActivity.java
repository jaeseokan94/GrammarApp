package com.example.spanishgrammarapp.resources.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;

import java.util.ArrayList;

/**
 * This activity lets the user chose the dialect.
 */
public class DialectActivity extends AppCompatActivity implements View.OnClickListener{
    public static String DIALECT = "Dialect";
    DatabaseHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialect);

        //Set up
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dialect);
        linearLayout.setBackgroundColor(Color.rgb(118, 178, 197));
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        APIWrapper apiWrapper = new APIWrapper(database);

        ArrayList<String> dialects = apiWrapper.getDialects(MainActivity.currentLanguage);

        for (String dialect: dialects) {
            Button button = new Button(this);
            button.setText(dialect);
            button.setOnClickListener(this);
            button.setTag(dialect);
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
}
