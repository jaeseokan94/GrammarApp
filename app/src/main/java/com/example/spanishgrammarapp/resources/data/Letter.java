package com.example.spanishgrammarapp.resources.data;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class Letter {
    private String letter;
    private String pronunciation;
    private String audioURL;

    public Letter (String l, String p, String a) {
        letter = l;
        pronunciation = p;
        audioURL = a;
    }

    public String getLetter() {
        return letter;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getAudioURL() {
        return audioURL;
    }
}
