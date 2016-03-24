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
    private String name;
    private String id;

    /**This string describes where the Exercise belongs. Each Topic has a collection of Subtopics, and each
    * of those has its own exercise. This identifier is very important for loading user progress from file
    * because it allows us to use the particular instance with the right subtopic.*/
    public String identifier;

    /**Public constructor for this class. It only needs one argument, which is its identifier.
    * @param identifier is the "Topic/Subtopic" string that is used to identify where the Exercise belongs*/
    public Exercise(String identifier, String id, String name){
        this.identifier = identifier;
        this.id = id;
        this.name = name;
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
        return identifier+"/"+id;
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

    /**
     * @param vocab the boolean to set vocab to, indicating that whether or not this is a vocabulary type exercise
     * */
    public void setVocab(boolean vocab){
        isVocab = vocab;
    }

    /**
     * for creating a string output for Exercise
     * @return a String containing, topic, subtopic and rating
     */
    @Override
    public String toString(){
        String[] order = getIdentifier().split("/");
        return order[0]+" - "+order[1]+" - "+getCorrectnessRating();
    }

    /**
     * @param name the name to be set for the exercise*/
    public void setName(String name){
        this.name = name;
    }

    /**
     * @param id the id to be set for the exercise*/
    public void setId(String id){
        this.id = id;
    }

    /**
     *@return the name of the exercise*/
    public String getName(){
        return name;
    }

    /**
     * @return the id of the exercise (not the same as identifier!)*/
    public String getId(){
        return id;
    }
}
