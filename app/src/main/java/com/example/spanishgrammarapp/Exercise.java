package com.example.spanishgrammarapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class is a representation of the Exercises that are available in the online Spanish Grammar Learning app.
 * Each object of this class contains several useful attributes that describe the Exercise, and it also holds
 * a collection of all the Questions that make up the exercise. The primary function of this class is to organise
 * the Questions and store some metadata about the collection.
 */
public class Exercise implements Serializable {

    /**The collection of Questions for this Exercise.*/
    private ArrayList<Question> questions = new ArrayList<>();

    /**This int describes the number of questions within this exercise that have been answered correctly
     * regardless of whether or not the exercise has been finished yet.*/
    private int numCorrectlyAnswered = 0;

    private boolean isVocab = false;

    /**This string describes where the Exercise belongs. Each Topic has a collection of Subtopics, and each
    * of those has its own exercise. This identifier is very important for loading user progress from file
    * because it allows us to use the particular instance with the right subtopic.*/
    public String identifier;

    /**Public constructor for this class. It only needs one argument, which is its identifier.
    * @param identifier is the "Topic/Subtopic" string that is used to identify where the Exercise belongs*/
    public Exercise(String identifier){
        this.identifier = identifier;
    }

    /**Adds the given question object to the collection of Questions for this Exercise.
    * @param q Question type object to be added to this Exercise.*/
    public void addQuestion(Question q){
        questions.add(q);
    }

    /**@return the collection of Questions for this Exercise.*/
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**@return the identifier of this exercise (i.e. "Topic/Subtopic"*/
    public String getIdentifier(){
        return identifier;
    }

    /**Sets the number of questions answered correctly to the given input.
    * This is mainly used for resetting the value to 0 when resetting the exercise.
     * @param i the number of questions answered correctly.*/
    public void setNumCorrectlyAnswered(int i){
        numCorrectlyAnswered = i;
    }

    /**Increases the value of the number of questions answered correctly by one.*/
    public void incrementCorrectlyAnswered(){
        ++numCorrectlyAnswered;
    }

    /**Returns the correctness rating for the exercise (a percentage as decimal)*/
    public double getCorrectnessRating(){
        return numCorrectlyAnswered/ (double) questions.size();
    }

    public void setVocab(boolean vocab){
        isVocab = vocab;
    }

    public boolean getVocab(){
        return isVocab;
    }
}
