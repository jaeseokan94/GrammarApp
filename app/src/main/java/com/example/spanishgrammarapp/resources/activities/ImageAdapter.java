package com.example.spanishgrammarapp.resources.activities;

/**
 * Created by Gun Park on 2016-03-22.
 */

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.resources.data.Holiday;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Holiday> holidays;

    public ImageAdapter(Context context, ArrayList<Holiday> holidays) {
        this.context = context;
        this.holidays = holidays;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.holiday_item, null);

            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.grid_holiday_label);
            textView.setText(holidays.get(position).getName_InLanguage());

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_holiday_image);

            String url = holidays.get(position).getImageURL();

            Uri uri = Uri.parse(url);
            imageView.setImageURI(uri);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return holidays.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}