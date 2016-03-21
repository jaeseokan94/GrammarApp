package com.example.spanishgrammarapp.Data;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.spanishgrammarapp.Exercise;
import com.example.spanishgrammarapp.Glossary;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.Question;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Holiday;
import com.example.spanishgrammarapp.resources.data.Letter;
import com.example.spanishgrammarapp.resources.data.Season;
import com.example.spanishgrammarapp.resources.data.Time;

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

    ArrayList<Exercise> exercisesList = new ArrayList<>();

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
    public Exercise apiQuestions(String topic, String subtopic, boolean isVocabuary, String exerciseId , String exerciseName) {

        isVocabuary=false;
        String questionURL = URL + "/" + MainActivity.currentLanguage + "/" + MainActivity.currentLevel + "/" + topic + "/" + subtopic + "/"
                + exerciseId;


        if(isVocabuary) {
            questionURL += "/vocabExercisesQuestions";
        } else {
            questionURL += "/exerciseQuestions";
        }
        System.out.println("QUESTION URL DEBUG : "+questionURL);

        // String urlQuestions = URL +"/"+topicName+"/"+subTopicName+"/"+"exerciseQuestion";
        Exercise exercise = new Exercise(topic+"/"+subtopic, exerciseId, exerciseName);


        try {
            JSONArray jsonArray = execute(
                   questionURL)
                    .get(); //this link is temporary, it needs to be generalized


            for (int i = 0; i < jsonArray.length(); i++) {

                ArrayList<String> answers = new ArrayList<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String questionText = jsonObject.getString("question_text"); // get question_text from API
                String questionType = jsonObject.getString("question_type");
                answers.add(jsonObject.getString("choice_1"));
                answers.add(jsonObject.getString("choice_2"));
                answers.add(jsonObject.getString("choice_3"));
                answers.add(jsonObject.getString("choice_4"));
                answers.add(jsonObject.getString("choice_5"));
                answers.add(jsonObject.getString("choice_6"));
                answers.add(jsonObject.getString("correct_answer"));

                String correct_answer = jsonObject.getString("correct_answer");
                System.out.println("question text : " + questionText);
                System.out.println("question type : " + questionType);
                System.out.println("choice_1 "+ answers);
                Question question = new Question(questionType, questionText, correct_answer, answers);
                exercise.addQuestion(question);
                System.out.println("JSON PASSED TO DATABASE");
                System.out.println(i);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
            e.printStackTrace();
        }
        return exercise;
    }

    public ArrayList<Exercise> getExercisesList(String topic, String subtopic) {
        exercisesList.clear();
        String exercisesListURL = URL + "/" + MainActivity.currentLanguage + "/" + MainActivity.currentLevel + "/" + topic + "/" + subtopic +
                "/exercisesList";

        System.out.println("EXERCISES LIST : "+exercisesListURL);



        try {
            JSONArray jsonArray = execute(
                    exercisesListURL)
                    .get(); //this link is temporary, it needs to be generalized

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String exercise_id = jsonObject.getString("exercise_id");
                String exercise_name = jsonObject.getString("exercise_name");
                exercisesList.add(new Exercise(topic+"/"+subtopic, exercise_id, exercise_name));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
            e.printStackTrace();
        }
        return exercisesList;
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
     * Thank you for someone writing these codes. i could work easily due to your nice codes. -Jae
     */

    public  ArrayList<Letter> getLetters(String language, String dialect) {
        //TODO implement this method

        ArrayList<Letter> letters = new ArrayList<Letter>();
        String resourceLetterURL = URL+"/"+language+"/"+dialect+"/Alphabet";
        System.out.println("this is url : " +resourceLetterURL);


        try {
            JSONArray jsonArray = execute(resourceLetterURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String letter= jsonObject.getString("word");
                String pronounciation = jsonObject.getString("pronounciation_guide_or_date");
                String audioUrl = jsonObject.getString("audio_url");

                letters.add(new Letter(letter, pronounciation, audioUrl));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        return letters;
    }






    //for the Calendar activity

    public  ArrayList<Day> getDays(String language, String dialect) {
        //TODO implement this method

        ArrayList<Day> days = new ArrayList<Day>();
        String resourceDayURL = URL+"/"+language+"/"+dialect+"/Day";
        System.out.println("this is url : " +resourceDayURL);


        try {
            JSONArray jsonArray = execute(resourceDayURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String day= jsonObject.getString("Day");
                String pronounciation = jsonObject.getString("pronounciation_guide_or_date");
                String audioUrl = jsonObject.getString("audio_url");

                days.add(new Day(day, pronounciation, audioUrl));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        return days;
    }



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
    public ArrayList<Holiday> getHolidays(String language, String dialect) {
        //TODO implement this method

        String resourceHolidayURL = URL+"/"+language+"/"+dialect+"/Holidays";


        ArrayList<Holiday> holidays = new ArrayList<Holiday>();


        try {
            JSONArray jsonArray = execute(resourceHolidayURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name_english= jsonObject.getString("word");
                String name_language = jsonObject.getString("pronounciation_guide_or_date");
                String date = jsonObject.getString("audio_url");
                String imgURL = jsonObject.getString("audio_url");

                holidays.add(new Holiday(name_english, name_language, date, imgURL));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }


        return holidays;
    }

    /**
     * gets seasons and months data from API
     * @param languageName
     * @param dialect
     * @return ArrayList of Seasons
     */
    public ArrayList<Season> getSeasonsAndMonthsData(String language, String dialect) {
        ArrayList<Season> seasons = new ArrayList<Season>();


        String resourceMonthsURL = URL+"/"+language+"/"+dialect+"/Alphabet";

        /*
        Season spring = new Season("Primavera", "Marso", "Avril", "Mayo");
        Season summer = new Season("Verano", "Junio", "Julio", "Agosto");
        Season autumn = new Season("Otoño", "Septiembre", "Octubre", "Noviembre");
        Season winter = new Season("Invierno", "Diciembre", "Enero", "Febrero");

        seasons.add(spring);
        seasons.add(summer);
        seasons.add(autumn);
        seasons.add(winter);*/
        try {
            JSONArray jsonArray = execute(resourceMonthsURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String months= jsonObject.getString("word");
                seasons.add(new Season(months));

            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }



        return seasons;
    }

    /**
     * gets time data from API
     * @param languageName
     * @param dialect
     * @return ArrayList of Times
     */
    public static ArrayList<Time> getTimeData(String languageName, String dialect) {
        ArrayList<Time> times = new ArrayList<>();
        //TODO get actual data
        Time t1 = new Time("12:05", "Son las doce y cinco", "url");

        for (int i = 0; i < 5; i++) {
            times.add(t1);
        }

        return times;
    }

    public ArrayList<String> getDialects(String language) {
        String dialectListURL = URL+"/"+language+"/dialectList";


        ArrayList<String> dialects = new ArrayList<>();

        try {
            JSONArray jsonArray = execute(dialectListURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String dialect_name= jsonObject.getString("name");


                dialects.add((dialect_name));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        return dialects;
    }

    /*
    THIS IS METHOD RETURNING LIST OF GLOSSARY
    Search for Glossary class for data type
    there are two data (word, word_in_lang) 
     */
    public  ArrayList<Glossary> getGlossary(String language) {

        ArrayList<Glossary> glossary = new ArrayList<Glossary>();
        String glosaryURL = URL+"/"+language+"/glossaryList";
        System.out.println("this is url : " + glosaryURL);


        try {
            JSONArray jsonArray = execute(glosaryURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String word= jsonObject.getString("word");
                String word_in_language = jsonObject.getString("word_in_language");

                glossary.add(new Glossary(word,word_in_language));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        return glossary;
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