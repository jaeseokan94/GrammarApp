package com.example.spanishgrammarapp.resources.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spanishgrammarapp.R;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Numb;

import java.util.ArrayList;

/**
 * Created by http://www.mkyong.com/android/android-gridview-example/
 */
public class CDayAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Numb> mobileValues;

    public CDayAdapter(Context context, ArrayList<Numb> mobileValues) {
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
            gridView = inflater.inflate(R.layout.calendar_item, null);

            // set value into Word
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_calendar);
            textView.setText(mobileValues.get(position).getNumber());

            // set value into Another word
            TextView textView2 = (TextView) gridView
                    .findViewById(R.id.grid_item_calendar);
            textView.setText(mobileValues.get(position).getPronunciation());

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