package com.example.spanishgrammarapp;

import android.content.Context;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.LanguageData;
import com.example.spanishgrammarapp.Data.LevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will be used to connect to the Database where questions are stored, and connect to the CMS to fetch
 * the stages/subtopics.
 */
public class CMSconnector {

    Context context;

    public CMSconnector(Exercise exercise, String topic, Context context ){
    this.context = context;
    }



    public static Exercise getExercise(String topic, String subtopic) {

        Exercise exercise = new Exercise(topic+"/"+subtopic);


        ArrayList<String> answers1 = new ArrayList<>();
        Question q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1); //temp



        int input = 0;
        //adds Questions to Exercise

        //multiple choice
        if(input==0) {
            /*
            answers1.add(database.getQuestionText("---os llamàis").getChoice_1()); // "osllamàis" need to be replaced to topic and subtopic name , int API key
            answers1.add(database.getQuestionText("---os llamàis").getChoice_2());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_3());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_4());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_5());
            answers1.add(database.getQuestionText("---os llamàis").getChoice_6());
            answers1.add(database.getQuestionText("---os llamàis").getCorrect_answer());
            q = new Question(ExercisesActivity.multipleChoice, "Question"+input+": " + database.getQuestionText("---os llamàis").getQuestionText() , answers1.get(0), answers1);
            */
        }else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1);
        }else{
            q = new Question(ExercisesActivity.trueFalse, "Test question 3", "true", answers1);
        }

        exercise.addQuestion(q);

        return exercise;
    }

    public static ArrayList getSubtopics(String language, String level,  String topic){

         language = "Spain";  // These three variables are for test purpose!
         level = "b";
         topic = "topic";


        ArrayList<String> subtopicsList = new ArrayList<String>();
        subtopicsList.add("");
        subtopicsList.add("");
        subtopicsList.add("Ser y Estar");
     //   subtopicsList.add(database.getSubtopicName(language,level,topic_name).getSubtopic());


        return subtopicsList;
    }


    public static List<LanguageData> getLanguages(Context context, DatabaseHelper database){



        return database.getLanguageList();
    }

    public static List<LevelData> getLevels(Context context, DatabaseHelper database, String language){



        return database.getLevelList(language);
    }
}
