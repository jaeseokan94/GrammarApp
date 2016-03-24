package com.example.spanishgrammarapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.linearLayout);

        Typeface font = Typeface.createFromAsset(getAssets(),"font2.ttf");

        TextView androidTitle = (TextView) findViewById(R.id.androidappTitle);
//        androidTitle.setText("ANDROID APP" + "\n");
        androidTitle.setTypeface(font);
        androidTitle.setTextSize(30);

        TextView androidPart = (TextView) findViewById(R.id.androidappText);
//        androidPart.setText("");
        androidPart.setTypeface(font);

        TextView cmsTitle = (TextView) findViewById(R.id.cmsTitle);
//        androidTitle.setText("CMS" + "\n");
        androidTitle.setTypeface(font);
        androidTitle.setTextSize(30);


        TextView cmsPart = (TextView) findViewById(R.id.cmsText);
//        cmsPart.setText("");
        cmsPart.setTypeface(font);

        TextView onlineTitle = (TextView) findViewById(R.id.onlineappTitle);
//        androidTitle.setText("ONLINE APP");
        androidTitle.setTypeface(font);
        androidTitle.setTextSize(30);

        TextView onlinePart = (TextView) findViewById(R.id.androidappText);
//        onlinePart.setText("");
        onlinePart.setTypeface(font);

    }

}
