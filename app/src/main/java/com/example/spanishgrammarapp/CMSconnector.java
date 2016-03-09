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


    // Question data
    public int numberOfQuestion; // it gives an integer value of number of question in certain subtopic
    public String questionText; // variable for question_text from API
   // public String choice_1;
    //public String choice_2;
    public String choice_3;
    public String choice_4;
    public String choice_5;
    public String choice_6;
    public String correct_answer;


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
        JSONParser parser = new JSONParser(database);
        parser.apiQuestions();

        //database.getQuestionText(questionText, choice_1, choice_2, choice_3, choice_4, choice_5, choice_6, correct_answer);

     //   circleList.add(new Circle(Database.ENERGY_TYPE_FOSSIL_FUEL, context, this, res.getColor(R.color.energy_fossil_fuel))); to be removed
        List<QuestionData> list = database.getAllQuestion(); // getAllquestions list
        System.out.println(list);
        String a = database.getQuestionText(TESTPURPOSE);

        if(input==0) {
            answers1.add();
            answers1.add(choice_3);
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






}
