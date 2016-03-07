package com.example.spanishgrammarapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        exercise.addQuestion(constructQuestions(0));
        exercise.addQuestion(constructQuestions(1));
        exercise.addQuestion(constructQuestions(2));
        exercise.addQuestion(constructQuestions(3));
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
        }/*else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2, the correct answer is: Correct answer", answers1.get(0), answers1);
        }*/else{
            q = new Question(ExercisesActivity.trueFalse, "Test question "+(input+1)+", the correct answer is: true ", "true", answers1);
        }
        return q;

    }

    /**
     * This will be used to get the question structure from a link containing a JSON Object
         */
    public void downloadQuestions(){

        try {
            JSONArray jsonArray = new JSONParser().execute(
                    "https://lang-it-up.herokuapp.com/polls/spanish/i/dating/subtopicList/").get();

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String value = jsonObject.getString("subtopic_name");//use this to get the right part of the JSON Object

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
