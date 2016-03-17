package com.example.spanishgrammarapp;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.spanishgrammarapp.Data.DatabaseHelper;
import com.example.spanishgrammarapp.resources.data.Letter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class APIWrapper extends AsyncTask<String,String,JSONArray>{

    private final DatabaseHelper database;

    private final static String URL = "http://lang-it-up.herokuapp.com/polls/api/Spanish/b"; // Spanish and level need to be replaced


    public APIWrapper(DatabaseHelper db) {
        database = db;
    }

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
     *  This method is to test Database and build API according to
     *  language, topicName, subtopicName
     */
 public void apiQuestions() {

   // String urlQuestions = URL +"/"+topicName+"/"+subTopicName+"/"+"exerciseQuestion";

     try {
         JSONArray jsonArray = execute(
                 "https://lang-it-up.herokuapp.com/polls/api/Spanish/b/Greeting/Pronouns/exerciseQuestion/")
                    .get(); //this link is temporary, it needs to be generalized

            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String questionText = jsonObject.getString("question_text"); // get question_text from API
                String choice_1 = jsonObject.getString("choice_1");
                String choice_2= jsonObject.getString("choice_2");
                String choice_3= jsonObject.getString("choice_3");
                String choice_4= jsonObject.getString("choice_4");
                String choice_5= jsonObject.getString("choice_5");
                String choice_6= jsonObject.getString("choice_6");
                String correct_answer= jsonObject.getString("correct_answer");

                database.addQuestion(questionText, choice_1, choice_2, choice_3, choice_4, choice_5, choice_6, correct_answer);
                System.out.println("JSON PASSED TO DATABASE");
                System.out.println(i);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
            e.printStackTrace();
        }


        /**
         public void getData() {

         for(String questionList : Database.question_arraay){

         }
         }**/

    }

    /**
     * returns ArrayList of subtopic names
     * @param topicName
     * @return ArrayList of subtopic names
     */
    public void getSubtopics(String language, String level, String topicName) {
        String subtopicListURL = URL+"/"+topicName+"/subtopicList/";
        String topic_name = topicName;

        language = "Spain";  // These three variables are for test purpose!
        level = "b";
        topic_name = "topic";


        try {
            JSONArray jsonArray = execute(
                    "https://lang-it-up.herokuapp.com/polls/api/Spanish/b/Likes/subtopicList/")
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
System.out.println(jsonArray.length());
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String subtopic_name= jsonObject.getString("subtopic_name");
                System.out.print("LANGUAGE????"+language);
                database.addSubtopic(language, level, topic_name, subtopic_name);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

    }

    /**
     * returns urls of situational video transcript and without transcript
     * @param topicName
     * @return urls[0] of situational video with transcript;
     *          urls[1] situational video without transcript
     */
    public Uri getSituationalVideoURLs(String topicName) {
        //TODO code here for getting url for situational video
        String SituationalVideoURL = URL+"/"+topicName+"/situationalVideo/";

        //String url is temporary
        Uri[] urls = new Uri[2];
        //without transcript
        urls[0] = Uri.parse("https://lang-it-up.herokuapp.com/media/listening_comprehension/U01-E05.mp3");
        //with transcript
        urls[1] = Uri.parse("https://lang-it-up.herokuapp.com/media/U01-01.mp4");
        try {
            JSONArray jsonArray = execute(
                    "https://lang-it-up.herokuapp.com/polls/api/Spanish/b/Greeting/situationalVideo/")
                    .get(); //this link is temporary, it needs to be generalized

            // [{"language_topic":2,"situation_description":"Situational Description","video_with_transcript":"/media/U01-01-Gra-Pronombres_qpIFGPh.mp4",
            // "video_without_transcript":"/media/U01-02-Gra-llamarse.mp4"}]


            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String situation_description = jsonObject.getString("situation_description"); // get question_text from API
                String video_with_transcript = jsonObject.getString("video_with_transcript");
                String video_without_transcript= jsonObject.getString("video_without_transcript");

                System.out.println("JSON PASSED TO DATABASE");
                System.out.println(i);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }


    return null;
    }




    /**
     * return grammar video url of a subtopic
     * @param topicName
     * @param subtopicName
     * @return grammar video url
     */
    public static Uri getGrammarVideoUri(String topicName, String subtopicName) {
        //TODO implement this method
        String uri = "https://lang-it-up.herokuapp.com/media/U01-01.mp4";

        String url = URL+"/"+topicName+"/"+subtopicName+"/"+"grammarVideo";


        return Uri.parse(uri);
    }


    public static ArrayList<Letter> getLetters(String languageName, String dialect) {
        //TODO implement this method

        ArrayList<Letter> letters = new ArrayList<Letter>();

        String letter = "a";
        String pronunciation = "b";
        String audioUrl = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

        for (int i = 0; i < 26; i++) {
            letters.add(new Letter(letter, pronunciation, audioUrl));
        }

        return letters;
    }
}
