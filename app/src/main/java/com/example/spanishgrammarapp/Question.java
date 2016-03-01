package com.example.spanishgrammarapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class plays an integral role in both creation of exercises and the maintenance of user progress
 * within an exercise.
 */
public class Question implements Serializable {
    //what is love?
    private String questionType;
    private String question;
    private String correctAnswer;
    private ArrayList<String> answers;
    private int attempts = 0;
    private boolean isCompleted = false; //true when the user successfully answered the question
    public String userGivenAnswer;

    /*Each question is constructed in the same way, with the same parameters. There is only ever one correct
    * answer per question of any type. Having questions split up like that, rather than a multiple choice
    * question with multiple answers (for example) allows more precise tracking of user progress.
    *
    * @param questionType One of: multiple choice, true/false, drag&drop, typing
    * @param question The text of the question itself. What are you asking the user to do?
    * @param correctAnswer The correct answer... who knew.
    * @param answers An ArrayList of type String that holds all the possible answers, including the correct one!*/
    public Question(String questionType, String question, String correctAnswer, ArrayList<String> answers){
        this.questionType = questionType;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestionType() {
        return questionType;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    /*Likely to be used in the Revise section*/
    public int getAttempts() {
        return attempts;
    }

    public void addAttempt(){
        ++attempts;
    }

    public boolean getCompleted(){
        return isCompleted;
    }

    public void setCompleted(boolean c){
        isCompleted = c;
    }
}
