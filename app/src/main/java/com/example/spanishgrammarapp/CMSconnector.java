package com.example.spanishgrammarapp;

import android.content.Context;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.Data.LanguageData;
import com.example.spanishgrammarapp.Data.LevelData;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will be used to connect to the Database where questions are stored, and connect to the CMS to fetch
 * the stages/subtopics.
 */
public class CMSconnector {

    Context context;

    public CMSconnector(Exercise exercise, String topic, Context context ){
    this.context = context;
    }

    public static List<LanguageData> getLanguages(Context context, DatabaseHelper database){
        return database.getLanguageList();
    }

    public static List<LevelData> getLevels(Context context, DatabaseHelper database, String language){
        return database.getLevelList(language);
    }
}
