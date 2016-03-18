package com.example.spanishgrammarapp.resources.data;

/**
 * Created by janaldoustorres on 18/03/2016.
 */
public class Time {
    private String time_digital;
    private String time_language;
    private String picOfClockURL;

    public Time(String tE, String tL, String p) {
        time_digital = tE;
        time_language = tL;
        picOfClockURL = p;
    }

    public String getTime_digital() {
        return time_digital;
    }

    public String getTime_language() {
        return time_language;
    }

    public String getPicOfClockURL() {
        return picOfClockURL;
    }
}
