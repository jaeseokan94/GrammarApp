package com.example.spanishgrammarapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2016.
 */
public class Exercise implements Parcelable {

    ArrayList<Question> questions = new ArrayList<>();

    protected Exercise(Parcel in) {
        questions = (ArrayList<Question>) in.readSerializable();
    }

    public Exercise(){

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(questions);
    }
}
