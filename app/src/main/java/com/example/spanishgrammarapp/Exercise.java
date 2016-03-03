package com.example.spanishgrammarapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2016.
 */
public class Exercise implements Parcelable, Serializable {

    ArrayList<Question> questions = new ArrayList<>();
    private double correctnessRating; //percentage of questions from this exercise that was answered correctly
    public String identifier;

    protected Exercise(Parcel in) {
        questions = (ArrayList<Question>) in.readSerializable();
        identifier = in.readString();
    }

    public Exercise(String identifier){
        this.identifier = identifier;
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public void addQuestion(Question q){
        questions.add(q);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getIdentifier(){
        return identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(questions);
        dest.writeString(identifier);
    }

    public void setCorrectnessRating(double d){
        correctnessRating = d;
    }
}
