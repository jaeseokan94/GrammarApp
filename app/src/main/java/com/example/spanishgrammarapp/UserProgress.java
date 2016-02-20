package com.example.spanishgrammarapp;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * This app is meant to hold the user's progress with the completion of questions.
 */
public class UserProgress implements java.io.Serializable {

    Context context;
    public UserProgress(Context context){
        this.context = context;
        //constructor
    }

    ArrayList<Exercise> completedExercises = new ArrayList<>();

    /*
    * This method will save a serializable object as a file on the device.
    * This is crucial for the app regardless of synchronization.
    * */
    public void saveProgress() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(),"userProgress.ser"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(completedExercises);
            fos.close();
            oos.close();
            System.out.println("Progress saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not written");;
        }

    }
}
