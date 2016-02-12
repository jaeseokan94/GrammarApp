package com.example.spanishgrammarapp;

import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2016.
 */
public class Question {

    private String questionType;
    private String question;
    private String correctAnswer;
    private ArrayList<String> answers;
    private int attempts = 0;
    private boolean completed = false;

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

    public int getAttempts() {
        return attempts;
    }

    public void addAttempt(){
        ++attempts;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setCompleted(boolean c){
        completed = c;
    }
}
