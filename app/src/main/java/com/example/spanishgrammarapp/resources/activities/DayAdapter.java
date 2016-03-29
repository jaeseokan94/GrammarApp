package com.example.spanishgrammarapp.resources.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Letter;

import java.util.ArrayList;


public class DayAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Day> mobileValues;

    public DayAdapter(Context context, ArrayList<Day> mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.day_item, null);

            // set value into Word
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_day);
            textView.setText(mobileValues.get(position).getDay());

            // set value into Another word
            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.grid_item_another_word);
            textView2.setText(mobileValues.get(position).getPronunciation());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.size();
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