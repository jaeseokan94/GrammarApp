package com.example.spanishgrammarapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JSONParser extends AsyncTask<String,String,JSONArray>{

    @Override
    protected JSONArray doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader bReader = null;

        try{
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream iStream = urlConnection.getInputStream();

            bReader = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sBuilder = new StringBuilder();

            String line ="";
            while ((line = bReader.readLine()) != null){
                sBuilder.append(line);
            }

            JSONArray jsonArray = new JSONArray(sBuilder.toString());

            return jsonArray;


        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();}
            try {
                if (bReader != null){
                    bReader.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray s) {
        super.onPostExecute(s);
    }

    /**
     * returns urls of situational video transcript and without transcript
     * @param topicName
     * @return urls[0] of situational video with transcript;
     *          urls[1] situational video without transcript
     */
    public static String[] getSituationalVideoURLs(String topicName) {
        //TODO code here for getting url for situational video
        //String url is temporary
        String[] urls = new String[2];
        //without transcript
        urls[0] = "https://lang-it-up.herokuapp.com/media/listening_comprehension/U01-E05.mp3";
        //with transcript
        urls[1] = "https://lang-it-up.herokuapp.com/media/U01-01.mp4";

        return urls;
    }

    /**
     * returns ArrayList of subtopic names
     * @param topicName
     * @return ArrayList of subtopic names
     */
    public static ArrayList<String> getSubtopics(String topicName) {
        ArrayList<String> subtopicsList = new ArrayList<String>();
        subtopicsList.add("Pronouns");
        subtopicsList.add("Llamarse");
        subtopicsList.add("Ser y Estar");
        subtopicsList.add("Vocabulary");

        return subtopicsList;
    }
}
