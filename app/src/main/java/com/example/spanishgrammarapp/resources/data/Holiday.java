package com.example.spanishgrammarapp.resources.data;

/**
 * Created by janaldoustorres on 17/03/2016.
 */
public class Holiday {
    private String name_English;
    private String name_InLanguage;
    private String date;
    private String imageURL;

    public Holiday(String nE, String nL, String d, String i) {
        name_English = nE;
        name_InLanguage = nL;
        date = d;
        imageURL = i;
    }
}
