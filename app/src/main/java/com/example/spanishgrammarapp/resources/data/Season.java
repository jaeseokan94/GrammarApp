package com.example.spanishgrammarapp.resources.data;

import java.util.ArrayList;

/**
 * Created by janaldoustorres on 18/03/2016.
 */
public class Season {
    private String season;
    private String month1, month2, month3;

    // TODO : Doesnt have Season attribute from CMS so I made this constructor
    public Season(String m){
        month1 = m ;

    }

    public Season(String n, String m1, String m2, String m3) {
        season = n;
        month1 = m1;
        month2 = m2;
        month3 = m3;
    }

    public String getSeason() {
        return season;
    }

    public ArrayList<String> getMonths() {
        ArrayList<String> months = new ArrayList();
        months.add(month1);
        months.add(month2);
        months.add(month3);

        return months;
    }
}
