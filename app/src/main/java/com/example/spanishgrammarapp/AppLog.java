package com.example.spanishgrammarapp;

/**
 * Created by NitinKapoor on 27/02/16.
 */
import android.util.Log;

public class AppLog {
    private static final String APP_TAG = "AudioRecorder";

    public static int logString(String message) {
        return Log.i(APP_TAG, message);
    }
}
