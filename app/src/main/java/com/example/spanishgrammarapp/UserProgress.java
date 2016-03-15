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

    private Context context; //used for getting the files directory for saving/loading files
    public static ArrayList<Exercise> completedExercises = new ArrayList<>();
    public static ArrayList<Exercise> exercisesInProgress = new ArrayList<>();
    private static ArrayList wrapper = new ArrayList(2); //rather than saving 2 files

    /**The constructor for this class takes in only one parameter for the sake of saving/loading files
    * @param context is the Context object that allows us to get the app's files directory.*/
    public UserProgress(Context context){
        this.context = context;
        wrapper.add(completedExercises);
        wrapper.add(exercisesInProgress);
    }

    /**
    * This method will save a serializable object as a file on the device.
    * This is crucial for the app regardless of online synchronization, as it allows
    * the users to resume exercises they have previously started, avoid repeating ones
    * they have previously completed, and record their performance allowing the users to
    * track their progress.*/
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

    /**This method deserializes objects from the userProgress.ser file. The references to the objects
    * are the completedExercises and exercisesInProgress ArrayLists<Exercise> objects. For simplicity's
    * sake they are put in a wrapper ArrayList and serialized as a single object, and this method splits
    * it back into the two ArrayLists. This method has been tested and confirmed to work properly via many
    * test runs in debug mode. If you are having bugs with User Progress this is unlikely to be their source.*/
    public void loadProgress() {
        try{
            FileInputStream fis = new FileInputStream(new File(context.getFilesDir(), "userProgress.ser"));
            ObjectInputStream ois = new ObjectInputStream(fis);
                wrapper = (ArrayList<Exercise>) ois.readObject();
            if(wrapper.size()==2){
                completedExercises = (ArrayList<Exercise>) wrapper.get(0);
                exercisesInProgress = (ArrayList<Exercise>) wrapper.get(1);
            }
            ois.close();
            fis.close();
            System.out.println("Progress loaded");
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR: Progress not loaded");
        }
    }
}
