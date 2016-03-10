package com.example.spanishgrammarapp;

import android.content.Context;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.QuestionData;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will be used to connect to the Database where questions are stored, and connect to the CMS to fetch
 * the stages/subtopics.
 */
public class CMSconnector {
    Exercise exercise;
    private DatabaseHelper database;
    Context context;
    QuestionData questionData;


    public CMSconnector(Exercise exercise, String topic, Context context){
        this.context = context;
        this.exercise = exercise;
        database = new DatabaseHelper(context);
    }

    public void constructExercise(){
        exercise.addQuestion(constructQuestions(0));
       // exercise.addQuestion(constructQuestions(2));
      //  exercise.addQuestion(constructQuestions(1));
    }

    /*This will be later replaced with a method that*/
    private Question constructQuestions(int input){
        ArrayList<String> answers1 = new ArrayList<>();
        Question q;
        APIWrapper parser = new APIWrapper(database);
        parser.apiQuestions();

        List<QuestionData> list = database.getAllQuestion(); // getAllquestions list Test purpose
        System.out.println(list);


        if(input==0) {
            answers1.add(database.getQuestionText("osllamàis").getChoice_1()); // "osllamàis" need to be replaced to topic and subtopic name , int API key
            answers1.add(database.getQuestionText("osllamàis").getChoice_2());
            answers1.add(database.getQuestionText("osllamàis").getChoice_3());
            answers1.add(database.getQuestionText("osllamàis").getChoice_4());
            answers1.add(database.getQuestionText("osllamàis").getChoice_5());
            answers1.add(database.getQuestionText("osllamàis").getChoice_6());
            answers1.add(database.getQuestionText("osllamàis").getCorrect_answer());
            q = new Question(ExercisesActivity.multipleChoice, "Question"+input+": " + database.getQuestionText("osllamàis").getQuestionText() , answers1.get(0), answers1);
        }else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1);
        }else{
            q = new Question(ExercisesActivity.trueFalse, "Test question 3", "true", answers1);
        }
        return q;
    }

}
