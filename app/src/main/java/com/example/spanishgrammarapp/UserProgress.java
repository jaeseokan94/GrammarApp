package com.example.spanishgrammarapp;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * This app is meant to hold the user's progress with the completion of questions.
 */
public class UserProgress implements java.io.Serializable {

    private Context context;
    public static ArrayList<Exercise> completedExercises = new ArrayList<>();
    public static ArrayList<Exercise> exercisesInProgress = new ArrayList<>();
    private static ArrayList wrapper = new ArrayList(2); //rather than saving 2 files

    public UserProgress(Context context){
        this.context = context;
        wrapper.add(completedExercises);
        wrapper.add(exercisesInProgress);
    }


    /*
    * This method will save a serializable object as a file on the device.
    * This is crucial for the app regardless of synchronization.
    * */
    public void saveProgress() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(context.getFilesDir(),"userProgress.ser"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(wrapper);
            fos.close();
            oos.close();
            System.out.println("Progress saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not written");
        }
    }

    public void loadProgress() {
        try{
            FileInputStream fis = new FileInputStream(new File(context.getFilesDir(), "userProgress.ser"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            wrapper = (ArrayList<Exercise>) ois.readObject();
            completedExercises = (ArrayList<Exercise>) wrapper.get(0);
            exercisesInProgress = (ArrayList<Exercise>) wrapper.get(1);
            fis.close();
            ois.close();
            System.out.println("User Progress loaded");
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR: Progress not loaded");
        }
    }
}
