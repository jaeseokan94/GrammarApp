package com.example.spanishgrammarapp.Data;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.spanishgrammarapp.Exercise;
import com.example.spanishgrammarapp.ExercisesActivity;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.Question;
import com.example.spanishgrammarapp.resources.data.Holiday;
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
import java.util.Arrays;

public class APIWrapper extends AsyncTask<String,String,JSONArray>{

    private final DatabaseHelper database;

    private final static String URL = "http://lang-it-up.herokuapp.com/polls/api";

    private final ArrayList<String> topicList = new ArrayList<String>(Arrays.asList("Greeting", "Checking in",
            "Sightseeing", "Directions", "Eating", "Likes", "Planning", "Shopping", "Dating"));


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




    public void getLangLevelAPI(){
        //  ArrayList<String> langList =  new ArrayList<String>(Arrays.asList("Spanish", "Korean"
        // ));  // get Array from Database.

        ArrayList<String> langList = apiLanguage();
        ArrayList<String> levelList = new ArrayList<String>();


        for(int a = 0 ; a < langList.size(); a++){
            String language = langList.get(a);
            String levelListURL = "http://lang-it-up.herokuapp.com/polls/api"+"/"+language+"/levelList";
            levelList.add(levelListURL);
            System.out.println(levelListURL);
            APIWrapper downloadAPI2 = new APIWrapper(database);
            downloadAPI2.apiLevel(levelListURL,language);
        }
    }

    /**
     * @param None
     * @return ArrayList of language list
     */
    public ArrayList<String> apiLanguage() {


        ArrayList<String> languageList = new ArrayList<String>();

        try {
            JSONArray jsonArray = execute(
                    "https://lang-it-up.herokuapp.com/polls/api/languageList/")
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                System.out.println(jsonArray.length());
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String language_name= jsonObject.getString("name");
                database.addLanguage(language_name);
                languageList.add(language_name);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }
        System.out.print("1111111");

        return languageList;

    }


    /**
     * @return LevelList
     */
    public ArrayList<String> apiLevel(String levelListURL, String langauge ) {
        ArrayList<String> levelList = new ArrayList<String>();

        try {
            JSONArray jsonArray = execute(
                    levelListURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String level_name= jsonObject.getString("level");
                database.addLevel(langauge, level_name);
                levelList.add(level_name);

            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR API LEVEL");
            e.printStackTrace();
        }

        //System.out.println("level list"+levelList);


        return levelList;
    }

    /**
     * @return ArrayList of subtopic names
     */

    public ArrayList getSubtopicList(String language, String level, String topic) {

        String subtopicURL=URL+"/"+language+"/"+level+"/"+topic+"/subtopicList";
        ArrayList<String> subtopicsList = new ArrayList<String>();

        try {
            JSONArray jsonArray = execute(
                    subtopicURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String subtopic_name= jsonObject.getString("subtopic_name");
                System.out.print("SUBTOPIC JSON PARSER");
                subtopicsList.add(subtopic_name);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }
        return subtopicsList;

    }


    /**
     *  This method is to test Database and build API according to
     *  language, topicName, subtopicName
     */
    public Exercise apiQuestions(String topic, String subtopic, boolean isVocabuary) {

        isVocabuary=false;
        String questionURL = URL + "/" + MainActivity.currentLanguage + "/" + MainActivity.currentLevel + "/" + topic + "/" + subtopic ;

        if(isVocabuary) {
            questionURL += "/vocabExerciseQuestion";
        } else {
            questionURL += "/exerciseQuestion";
        }
        // String urlQuestions = URL +"/"+topicName+"/"+subTopicName+"/"+"exerciseQuestion";
        Exercise exercise = new Exercise(topic+"/"+subtopic);


        try {
            JSONArray jsonArray = execute(
                   questionURL)
                    .get(); //this link is temporary, it needs to be generalized


            for (int i = 0; i < jsonArray.length(); i++) {

                ArrayList<String> answers = new ArrayList<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String questionText = jsonObject.getString("question_text"); // get question_text from API
                answers.add(jsonObject.getString("choice_1"));
                answers.add(jsonObject.getString("choice_2"));
                answers.add(jsonObject.getString("choice_3"));
                answers.add(jsonObject.getString("choice_4"));
                answers.add(jsonObject.getString("choice_5"));
                answers.add(jsonObject.getString("choice_6"));
                answers.add(jsonObject.getString("correct_answer"));

                String correct_answer = jsonObject.getString("correct_answer");

                Question question = new Question(ExercisesActivity.multipleChoice, questionText, correct_answer, answers);
                exercise.addQuestion(question);
                System.out.println("JSON PASSED TO DATABASE");
                System.out.println(i);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
            e.printStackTrace();
        }return exercise;
    }

        /**
         * returns urls of situational video transcript and without transcript
         * @param topicName
         * @return urls[0] of situational video with transcript;
         *          urls[1] situational video without transcript
         */
    public Uri apiSituationalVideoURLs(String topicName) {
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
    public static Uri apiGrammarVideoUri(String topicName, String subtopicName) {
        //TODO implement this method
        String uri = "https://lang-it-up.herokuapp.com/media/U01-01.mp4";

        String url = URL+"/"+topicName+"/"+subtopicName+"/"+"grammarVideo";


        return Uri.parse(uri);
    }



    /**
     * Uses API to make list of letters.
     * @param languageName
     * @param dialect of langauge
     * @return array list of Letters of passed language and dialect
     */
    public static ArrayList<Letter> getLetters(String languageName, String dialect) {
        //TODO implement this method

        ArrayList<Letter> letters = new ArrayList<Letter>();

        String letter = "letter";
        String pronunciation = "pronunciation";
        String audioUrl = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

        for (int i = 0; i < 26; i++) {
            letters.add(new Letter(letter, pronunciation, audioUrl));
        }

        return letters;
    }

    /**
     * gets instructions of how to use resource from API
     * @param languageName
     * @param dialect
     * @param resource
     * @return instructions
     */
    public static String getInstructions(String languageName, String dialect, String resource) {
        //TODO implement this method

        String instructions = "Click a letter to hear it.";

        return instructions;
    }

    /**
     * gets holidays from API
     * @param languageName
     * @param dialect
     * @return instructions
     */
    public static ArrayList<Holiday> getHolidays(String languageName, String dialect) {
        //TODO implement this method

        ArrayList<Holiday> holidays = new ArrayList<Holiday>();

        String name_english = "New year";
        String name_language = "Año nuevo";
        String date = "Primero de javier";
        String imgURL = "http://www.dogoilpress.com/data/wallpapers/3/FDS_355863.jpg";

        for (int i = 0; i < 5; i++) {
            holidays.add(new Holiday(name_english, name_language, date, imgURL));
        }

        return holidays;
    }
}


/* THIS METHOD FOR SUBTOPIC API SAVING INTO DB
    public void getSubtopicAPI(){

        ArrayList<String> langList = apiLanguage();
        ArrayList<String> levelList = new ArrayList<String>();
        ArrayList<String> subtopicList = new ArrayList<String>();

        //TODO: This method need to be improved. String 'level'
        for(int a = 0 ; a < langList.size() ; a++) {
            for (int b = 0; b < levelList.size(); b++) {
                for (int c = 0; c < topicList.size(); c++) {
                    String language = langList.get(a);
                    String level = levelList.get(b);
                    String topic = topicList.get(c);
                    String subtopicListURL = "http://lang-it-up.herokuapp.com/polls/api"+"/"+language+"/"+level+"/"+topic+"/subtopicList";
                    subtopicList.add(subtopicListURL);
                    APIWrapper downloadSubtopic = new APIWrapper(database);
                  //  downloadSubtopic.apiSubtopics(subtopicListURL,language,level,topic);
                }
            }
        }
    }
*/