package com.example.spanishgrammarapp;

import java.util.ArrayList;

/**
 * This class will be used to connect to the Database where questions are stored, and connect to the CMS to fetch
 * the stages/subtopics.
 */
public class CMSconnector {
    Exercise exercise;
    public CMSconnector(Exercise exercise, String topic){
        this.exercise = exercise;
    }

    public void constructExercise(){
        exercise.addQuestion(constructQuestions(2));
        exercise.addQuestion(constructQuestions(0));
        exercise.addQuestion(constructQuestions(0));
        exercise.addQuestion(constructQuestions(2));
    }

    /*This will be later replaced with a method that*/
    private Question constructQuestions(int input){
        ArrayList<String> answers1 = new ArrayList<>();
        Question q;
        if(input==0) {
            answers1.add("Correct answer");
            answers1.add("Wrong answer 1");
            answers1.add("Wrong answer 2");
            answers1.add("Wrong answer 3");
            q = new Question(ExercisesActivity.multipleChoice, "Test question 1", answers1.get(0), answers1);
        }else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1);
        }else{
            q = new Question(ExercisesActivity.trueFalse, "Test question 3", "true", answers1);
        }
        return q;
    }
}
