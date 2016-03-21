package com.example.spanishgrammarapp.resources.data;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class Numb {
    private String numb;
    private String pronunciation;
    private String audioURL;

    public Numb(String l, String p, String a) {
        numb = l;
        pronunciation = p;
        audioURL = a;
    }

    public String getNumber() {return numb;}

    public String getPronunciation() {
        return pronunciation;
    }

    public String getAudioURL() {
        return audioURL;
    }
}
