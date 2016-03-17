package com.example.spanishgrammarapp.resources.data;

import java.net.URI;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class Letter {
    private String letter;
    private String pronunciation;
    private URI audioURL;

    public Letter (String l, String p, String a) {
        letter = l;
        pronunciation = p;
        audioURL = URI.create(a);
    }

    public String getLetter() {
        return letter;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public URI getAudioURL() {
        return audioURL;
    }
}
