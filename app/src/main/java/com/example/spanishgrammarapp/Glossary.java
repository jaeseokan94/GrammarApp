package com.example.spanishgrammarapp;

/**
 * Created by JAESEOKAN on 20/03/2016.
 * DATA FOR GLOSSARY
 */
public class Glossary {
    private String word;
    private String word_in_lang;

    public Glossary (String word, String word_in_lang) {
        word = word;
        word_in_lang = word_in_lang;
    }

    public String getWord() {
        return word;
    }

    public String getWord_in_lang() {
        return word_in_lang;
    }

    public String toString(){
        return word+" : "+word_in_lang ;
    }

}
