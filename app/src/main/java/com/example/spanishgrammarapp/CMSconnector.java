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


    public static Exercise getExercise(Context context, String topic, String subtopic) {
        Exercise exercise = new Exercise();
        DatabaseHelper database = new DatabaseHelper(context);

        ArrayList<String> answers1 = new ArrayList<>();
        Question q;

        List<QuestionData> list = database.getAllQuestion(); // getAllquestions list Test purpose
        System.out.println(list);

        int input = 0;
        //adds Questions to Exercise

        //multiple choice
        if(input==0) {
            answers1.add(database.getQuestionText("---os llamàis").getChoice_1()); // "osllamàis" need to be replaced to topic and subtopic name , int API key
            answers1.add(database.getQuestionText("---os llamàis").getChoice_2());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_3());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_4());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_5());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_6());
            answers1.add(database.getQuestionText("---os llamàis").getCorrect_answer());
            q = new Question(ExercisesActivity.multipleChoice, "Question"+input+": " + database.getQuestionText("---os llamàis").getQuestionText() , answers1.get(0), answers1);
        }else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1);
        }else{
            q = new Question(ExercisesActivity.trueFalse, "Test question 3", "true", answers1);
        }

        exercise.addQuestion(q);

        return exercise;
    }

    public static ArrayList getSubtopics(Context context, String topic){

        ArrayList<String> subtopicsList = new ArrayList<String>();
        subtopicsList.add("Pronouns");
        subtopicsList.add("Llamarse");
        subtopicsList.add("Ser y Estar");
        subtopicsList.add("Vocabulary");


        return subtopicsList;
    }

}
