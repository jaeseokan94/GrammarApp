package com.example.spanishgrammarapp.resources.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.spanishgrammarapp.Data.APIWrapper;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.ResourcesActivity;

import java.util.ArrayList;

/**
 * This activity lets the user chose the dialect.
 */
public class DialectActivity extends AppCompatActivity implements View.OnClickListener{
    public static String DIALECT = "Dialect";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialect);

        //Set up
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_dialect);

        ArrayList<String> dialects = APIWrapper.getDialects(MainActivity.currentLanguage);

        for (String dialect: dialects) {
            Button button = new Button(this);
            button.setText(dialect);
            button.setOnClickListener(this);
            button.setTag(dialect);
            linearLayout.addView(button);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ResourcesActivity.class);
        intent.putExtra(DIALECT, v.getTag().toString());
        startActivity(intent);
    }
}
