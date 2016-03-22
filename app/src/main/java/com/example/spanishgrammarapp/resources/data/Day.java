package com.example.spanishgrammarapp.resources.data;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class Day {
    private String day;
    private String pronunciation;
    private String audioURL;

    public Day(String l, String p, String a) {
        day = l;
        pronunciation = p;
        audioURL = "Heroku.com";
    }

    public String getDay() {
        return day;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getAudioURL() {
        return audioURL;
    }
}
