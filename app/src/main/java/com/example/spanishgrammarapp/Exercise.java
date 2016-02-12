package com.example.spanishgrammarapp;

import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2016.
 */
public class Exercise {

    ArrayList<Question> exercise = new ArrayList<>();

    public void addQuestion(Question q){
        exercise.add(q);
    }

    public ArrayList<Question> getExercise() {
        return exercise;
    }
}
