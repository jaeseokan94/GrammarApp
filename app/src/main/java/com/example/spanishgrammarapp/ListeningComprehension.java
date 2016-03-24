package com.example.spanishgrammarapp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Data structure to hold listening comprehension
 */
public class ListeningComprehension {
    private String url;
    private ArrayList<String> choices;
    private ArrayList<Integer> cAnswers;

    public ListeningComprehension(String url, ArrayList<String> choices, String answers) {
        this.url = url;
        this.choices = choices;
        String[] ans = answers.split(",");
        this.cAnswers = new ArrayList<Integer>();
        for (int i = 0; i < ans.length; i++) {
            cAnswers.add(Integer.parseInt(ans[i]));
        }
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public ArrayList<Integer> getAnswers() {
        return cAnswers;
    }
}
