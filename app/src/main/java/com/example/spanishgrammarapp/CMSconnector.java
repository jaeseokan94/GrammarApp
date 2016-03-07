package com.example.spanishgrammarapp;

import android.content.Context;

import com.example.spanishgrammarapp.Data.DatabaseHelper;

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
    private DatabaseHelper database;
    Context context;


    // Question data
    public int numberOfQuestion; // it gives an integer value of number of question in certain subtopic
    public String questionText; // variable for question_text from API
    public String choice_1;
    public String choice_2;
    public String choice_3;
    public String choice_4;
    public String choice_5;
    public String choice_6;
    public String correct_answer;


    public CMSconnector(Exercise exercise, String topic, DatabaseHelper db){
        this.exercise = exercise;
        this.database = db;


    }

    public void constructExercise(){
        exercise.addQuestion(constructQuestions(0));
        exercise.addQuestion(constructQuestions(2));
        exercise.addQuestion(constructQuestions(1));
    }

    /*This will be later replaced with a method that*/
    private Question constructQuestions(int input){
        ArrayList<String> answers1 = new ArrayList<>();
        Question q;
        apiQuestions();

        if(input==0) {
            answers1.add(choice_1);
            answers1.add(choice_2);
            answers1.add(choice_3);
            answers1.add(choice_4);
            answers1.add(choice_5);
            answers1.add(choice_6);
            answers1.add(correct_answer);
            q = new Question(ExercisesActivity.multipleChoice, "Question"+input+": " + questionText , answers1.get(0), answers1);
        }else if(input==1){
            answers1.add("Correct answer");
            q = new Question(ExercisesActivity.typing, "Test question 2", answers1.get(0), answers1);
        }else{
            q = new Question(ExercisesActivity.trueFalse, "Test question 3", "true", answers1);
        }
        return q;
    }
    /**
     *  This method is to test Database and build API according to
     *  language, topicName, subtopicName
     */
    public void apiQuestions(){

        try {
            JSONArray jsonArray = new JSONParser().execute(
                    "https://lang-it-up.herokuapp.com/polls/api/Spanish/b/Greeting/Pronouns/exerciseQuestion/")
                    .get(); //this link is temporary, it needs to be generalized

            int numberOfQuestion = jsonArray.length();



            for(int i = 0 ; i < jsonArray.length(); i++ ){


                JSONObject jsonObject = jsonArray.getJSONObject(i);

                questionText = jsonObject.getString("question_text"); // get question_text from API
                choice_1 = jsonObject.getString("choice_1");
                choice_2= jsonObject.getString("choice_2");
                choice_3= jsonObject.getString("choice_3");
                choice_4= jsonObject.getString("choice_4");
                choice_5= jsonObject.getString("choice_5");
                choice_6= jsonObject.getString("choice_6");
                correct_answer= jsonObject.getString("correct_answer");

                database.addQuestion(questionText, choice_1, choice_2, choice_3, choice_4, choice_5, choice_6, correct_answer);
                System.out.println("JSON PASSED TO DATABASE");
//, choice_1, choice_2, choice_3, choice_4, choice_5, choice_6, correct_answer
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }

        /**
         public void getData() {

         for(String questionList : Database.question_arraay){

         }
         }**/

    }




}
