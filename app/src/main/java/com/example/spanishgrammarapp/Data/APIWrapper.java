package com.example.spanishgrammarapp.Data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spanishgrammarapp.Exercise;
import com.example.spanishgrammarapp.Glossary;
import com.example.spanishgrammarapp.ListeningComprehension;
import com.example.spanishgrammarapp.MainActivity;
import com.example.spanishgrammarapp.Question;
import com.example.spanishgrammarapp.resources.data.Day;
import com.example.spanishgrammarapp.resources.data.Holiday;
import com.example.spanishgrammarapp.resources.data.Letter;
import com.example.spanishgrammarapp.resources.data.Numb;
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

    private  final static String URLMEDIA = "http://lang-it-up.herokuapp.com/";
    private final static String URL = "http://lang-it-up.herokuapp.com/polls/api";

    private final ArrayList<String> topicList = new ArrayList<String>(Arrays.asList("Greeting", "Checking in",
            "Sightseeing", "Directions", "Eating", "Likes", "Planning", "Shopping", "Dating"));

    ArrayList<Exercise> exercisesList = new ArrayList<>();

    private final String imageURL = "https://lang-it-up.herokuapp.com";

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
/*
    public void getLangLevelAPI(){
        List<LanguageData> langList = apiLanguage();
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
*/
    /**
     * @return ArrayList of language list
     */
    public ArrayList<LanguageData> apiLanguage() {

        ArrayList<LanguageData> languageList = new ArrayList<LanguageData>() {};
        try {
            JSONArray jsonArray = execute(
                    "https://lang-it-up.herokuapp.com/polls/api/languageList/")
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                System.out.println(jsonArray.length());
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String language_name= jsonObject.getString("name");
                languageList.add(new LanguageData((language_name)));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }
        return languageList;
    }

    /**
     * @return LevelList
     */
    public ArrayList<LevelData> apiLevel() {
        ArrayList<LevelData> levelList = new ArrayList<LevelData>();

        String levelListURL = "http://lang-it-up.herokuapp.com/polls/api"+"/"+MainActivity.currentLanguage+"/levelList";

        try {
            JSONArray jsonArray = execute(
                    levelListURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String level_name= jsonObject.getString("level");
                levelList.add(new LevelData(level_name));

            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR API LEVEL");
            e.printStackTrace();
        }
        return levelList;
    }

    /*
    TOPICLIST PARAMETER ONLY LEVEL
    IT WILL RETURN ARRAYLIST OF TOPICS
     */
    public ArrayList<String> getTopicList(){
        String topicURL=URL+"/"+MainActivity.currentLevel+"/topicList";
        ArrayList<String> topicList = new ArrayList<String>();

        try {
            JSONArray jsonArray = execute(
                    topicURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String topic_name= jsonObject.getString("topic_name");
                topicList.add(topic_name);
            }

        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }
        return topicList;

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
                System.out.print("subtopic error" + subtopic_name + "URL " + subtopicURL);
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
    public Exercise apiQuestions(String topic, String subtopic, String exerciseId , String exerciseName) {



       // isVocabuary=false;

        System.out.println("SUBTOPIC "+subtopic);

        String questionURL = URL + "/" + MainActivity.currentLanguage + "/" + MainActivity.currentLevel + "/" + topic + "/" + subtopic ;
        Exercise exercise = new Exercise(topic+"/"+subtopic, exerciseId, exerciseName);
        if(subtopic.equals("Vocabulary")) {
            questionURL += "/vocabExercisesQuestions";

            try {
                JSONArray jsonArray = execute(questionURL).get();
                System.out.println(questionURL);
                for(int i = 0 ; i < jsonArray.length(); i++ ){
                    ArrayList<String> answers = new ArrayList<>();

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String questionText = jsonObject.getString("question_text"); // get question_text from API
                    String questionType = "dd";
                    answers.add(jsonObject.getString("choice_1"));
                    answers.add(jsonObject.getString("choice_2"));
                    answers.add(jsonObject.getString("choice_3"));
                    answers.add(jsonObject.getString("choice_4"));
                    answers.add(jsonObject.getString("choice_5"));
                    answers.add(jsonObject.getString("choice_6"));
                    //answers.add(jsonObject.getString("correct_answer"));
                    String correct_answer = jsonObject.getString("correct_answer");
                    Question question = new Question(questionType, questionText, correct_answer, answers);
                    exercise.addQuestion(question);
                    System.out.println("CHOICES :"+question.getAnswers());
                    System.out.println("TEST QUESTION LIST JSON " + question);
                }
            } catch (Exception e) {
                System.out.println("JSON EXCEPTION ERROR HERE");
                e.printStackTrace();
            }
        } else {
            questionURL +=  "/" + exerciseId+"/exerciseQuestions";
            try {
                JSONArray jsonArray = execute(questionURL).get();
                //   jsonArray.getJSONObject("exercise_question");
                System.out.println("JSON ARRAY "+jsonArray);

                for(int k = 0 ; k <jsonArray.length() ; k++){
                    JSONObject questions = jsonArray.getJSONObject(k);
                    JSONArray sublist = questions.getJSONArray("exercise_questions");

                    for (int i = 0; i < sublist.length(); i++) {

                        ArrayList<String> answers = new ArrayList<>();

                        JSONObject jsonObject = sublist.getJSONObject(i);

                        String questionText = jsonObject.getString("question_text"); // get question_text from API
                        String questionType = jsonObject.getString("question_type");
                        answers.add(jsonObject.getString("choice_1"));
                        answers.add(jsonObject.getString("choice_2"));
                        answers.add(jsonObject.getString("choice_3"));
                        answers.add(jsonObject.getString("choice_4"));
                        answers.add(jsonObject.getString("choice_5"));
                        answers.add(jsonObject.getString("choice_6"));
                        answers.add(jsonObject.getString("correct_answer"));

                       // String type = Character.toString(questionType.charAt(2))+Character.toString(questionType.charAt(3)) ;

                        String correct_answer = jsonObject.getString("correct_answer");
                        System.out.println("question text : " + questionText);
                        System.out.println("question type : " + questionType);
                        System.out.println("choice_1 " + answers);
                        Question question = new Question(questionType, questionText, correct_answer, answers);
                        exercise.addQuestion(question);
                        System.out.println("TEST QUESTION LIST JSON " + question);
                    }
                }

            } catch (Exception e) {
                System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
                e.printStackTrace();
            }
        }
        System.out.println("QUESTION URL DEBUG : " + questionURL);
        return exercise;
    }

    /**
     *  Get listening comprehension array list. Please use this method, and change it accordingly. (a bit buggy now)
     *  Probably you can send fixed exerciseId and exerciseName , and I can make change accordingly in CMS
     */
    public ArrayList getListeningComprehension(String topic, String subtopic, String exerciseId , String exerciseName) {


        ArrayList<String> answers = new ArrayList<>();

        // isVocabuary=false;

        System.out.println("SUBTOPIC "+subtopic);

        String questionURL = URL + "/" + MainActivity.currentLanguage + "/" + MainActivity.currentLevel + "/" + topic + "/" + subtopic ;
        Exercise exercise = new Exercise(topic+"/"+subtopic, exerciseId, exerciseName);

            questionURL +=  "/" + exerciseId+"/exerciseQuestions";
            try {
                JSONArray jsonArray = execute(questionURL).get();
                //   jsonArray.getJSONObject("exercise_question");
                System.out.println("JSON ARRAY "+jsonArray);

                for(int k = 0 ; k <jsonArray.length() ; k++){
                    JSONObject questions = jsonArray.getJSONObject(k);
                    JSONArray sublist = questions.getJSONArray("exercise_questions");

                    for (int i = 0; i < sublist.length(); i++) {


                        JSONObject jsonObject = sublist.getJSONObject(i);
                        answers.add(jsonObject.getString("question_text"));
                        answers.add(jsonObject.getString("choice_1"));
                        answers.add(jsonObject.getString("choice_2"));
                        answers.add(jsonObject.getString("choice_3"));
                        answers.add(jsonObject.getString("choice_4"));
                        answers.add(jsonObject.getString("choice_5"));
                        answers.add(jsonObject.getString("choice_6"));
                        answers.add(jsonObject.getString("correct_answer"));


                        System.out.println("LIST  " + answers);

                    }
                }

            } catch (Exception e) {
                System.out.println("JSON EXCEPTION ERROR IN QUESTONS");
                e.printStackTrace();
            }

        System.out.println("QUESTION URL DEBUG : " + questionURL);
        return answers;
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
                exercisesList.add(new Exercise(topic + "/" + subtopic, exercise_id, exercise_name));
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
    public ArrayList<String> apiSituationalVideoURLs(String topicName) {
        String SituationalVideoURL = URL+"/"+MainActivity.currentLanguage+"/"+MainActivity.currentLevel+"/"+topicName+"/situationalVideo/";

        ArrayList<String> urlList = new ArrayList<>(3);
        try {
            JSONArray jsonArray = execute(
            SituationalVideoURL)
                    .get(); //this link is temporary, it needs to be generalized

            System.out.println("URL FOR SITUATIONAL VIDEO  " + SituationalVideoURL);

            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String situation_description =jsonObject.getString("situation_description"); // get question_text from API
                String video_with_transcript =  jsonObject.getString("video_with_transcript");
                String video_without_transcript=  jsonObject.getString("video_without_transcript");

                urlList.add(situation_description);
                urlList.add(video_with_transcript);
                urlList.add(video_without_transcript);

                System.out.println("JSON PASSED TO DATABASE");
                System.out.println("url info  " + urlList.get(1));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }


    return urlList;
    }


    /**
     * returns urls of situational video transcript and without transcript
     * @param topicName
     * @return urls[0] of situational video with transcript;
     *          urls[1] situational video without transcript
     */
    public ListeningComprehension apiListeningComprhension(String topicName) {
        String situationalVideoURL = URL+"/"+MainActivity.currentLanguage+"/"+MainActivity.currentLevel+"/"+topicName+"/listeningComprehension" +
                "/";


        ListeningComprehension listeningComprehension = null;

        try {
            JSONArray jsonArray = execute(
                    situationalVideoURL)
                    .get(); //this link is temporary, it needs to be generalized

            System.out.println("URL FOR SITUATIONAL VIDEO - LISTEING COMP  " + situationalVideoURL);

            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String url =  jsonObject.getString("audio_url");
                String choice1 =  jsonObject.getString("choice_1");
                String choice2 =  jsonObject.getString("choice_2");
                String choice3 =  jsonObject.getString("choice_3");
                String choice4 =  jsonObject.getString("choice_4");
                String choice5 =  jsonObject.getString("choice_5");
                String choice6 =  jsonObject.getString("choice_6");

                ArrayList<String> choices = new ArrayList<>();
                choices.add(choice1);
                choices.add(choice2);
                choices.add(choice3);
                choices.add(choice4);
                choices.add(choice5);
                choices.add(choice6);

                String cAnswers = jsonObject.getString("correct_answers");

                listeningComprehension = new ListeningComprehension(url, choices, cAnswers);

                System.out.println("JSON PASSED TO DATABASE");
                System.out.println("url info  "+listeningComprehension.getUrl());
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }

        return listeningComprehension;

    }

    /**
     * return grammar video url of a subtopic
     * @param topicName
     * @param subtopicName
     * @return grammar video url
     *
     * I PASS IT TO ARRAY LIST, MAKE SURE TO PARSE() -> YOU CAN SEE IN SITAUTIAONAL VIDEO CASE
     */
    public  String apiGrammarVideoUri(String topicName, String subtopicName) {
        String urlGrammar="";
        String grammarVideoURL = URL+"/"+MainActivity.currentLanguage+"/"+MainActivity.currentLevel+"/"+ topicName+"/"+subtopicName+"/"+"grammarVideo";
        System.out.println("grammarVideoURL  "+grammarVideoURL);
        try {
            JSONArray jsonArray = execute(grammarVideoURL).get(); //this link is temporary, it needs to be generalized

            for(int i = 0 ; i < jsonArray.length(); i++ ){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                urlGrammar = jsonObject.getString("grammar_video_file");
                System.out.println("JSON PASSED TO DATABASE");
                System.out.println(i);
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR");
            e.printStackTrace();
        }

        return urlGrammar;
    }

    /**
     * Uses API to make list of letters.
     * @param language
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
                System.out.println("Letter " + letters.toString() + "letter one" + letter.toString());


            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }
        return letters;
    }


    public  ArrayList<Day> getDays(String language, String dialect) {
        //TODO implement this method

        ArrayList<Day> days = new ArrayList<Day>();
        String resourceDayURL;
        resourceDayURL = URL+"/"+language+"/"+dialect+"/Days";


        System.out.println("this is url : " +resourceDayURL);


        try {
            JSONArray jsonArray = execute(resourceDayURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String day= jsonObject.getString("word");
                String dayPronounciation = jsonObject.getString("word_in_language");
                String dayAudioUrl = jsonObject.getString("audio_url");

                days.add(new Day(day, dayPronounciation, dayAudioUrl));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        return days;
    }



    //for the Calendar activity tempor

    public ArrayList<Numb> getNumbs(String language, String dialect) {
        //TODO implement this method

        ArrayList<Numb> numbs = new ArrayList<Numb>();
        String resourceNumbURL = URL+"/"+language+"/"+dialect+"/Numbers";
        System.out.println("this is url : " +resourceNumbURL);


        try {
            JSONArray jsonArray = execute(resourceNumbURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String numb = jsonObject.getString("word");
                String pronounciationNumb = jsonObject.getString("word_in_language");
                String numAudioUrl = jsonObject.getString("audio_url");

                numbs.add(new Numb(numb, pronounciationNumb, numAudioUrl));

            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }

        //this code is for testing
//        ArrayList<Numb> fake = new ArrayList<>();
//        fake.add(new Numb("fake1", "fake2", "fake3"));
//        return fake;


        return numbs;
    }



    public static String getInstructions(String languageName, String dialect, String resource) {
        //TODO implement this method

        String instructions = "Click a letter to hear it.";

        return instructions;
    }

    /**
     * gets holidays from API
     * @param language
     * @param dialect
     * @return instructions
     */
    public ArrayList<Holiday> getHolidays(String language, String dialect) {
        //TODO implement this method
        String resourceHolidayURL = URL+"/"+language+"/"+dialect+"/Holidays";
        System.out.print("URL     HOLIDAY URL " + resourceHolidayURL);
        ArrayList<Holiday> holidays = new ArrayList<Holiday>();

        try {
            JSONArray jsonArray = execute(resourceHolidayURL)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name_english= jsonObject.getString("phrase");
                String name_language = jsonObject.getString("phrase_in_language"); //
                String pictureUrl = jsonObject.getString("picture_url"); //image url
                String audioUrl = jsonObject.getString("audio_url");

                holidays.add(new Holiday(name_english, name_language, audioUrl, pictureUrl));
                System.out.println(" HOLIDAY DEBUG " + holidays);
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
        }
        return holidays;
    }

    /**
     * gets seasons and months data from API
  //   * @param languageName
     * @param dialect
     * @return ArrayList of Seasons
     */
    public ArrayList<Season> getSeasonsAndMonthsData(String language, String dialect) {
        ArrayList<Season> seasons = new ArrayList<Season>();

        String resourceMonthsURL = URL+"/"+language+"/"+dialect+"/Months";

        Season spring;
        Season summer;
        Season autumn;
        Season winter;

        try {
            JSONArray jsonArray = execute(resourceMonthsURL)
                    .get(); //this link is temporary, it needs to be generalized
            //Spring
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String season = jsonObject.getString("Spring");
            String month1 = jsonObject.getString("Month1");
            String month2 = jsonObject.getString("Month2");
            String month3 = jsonObject.getString("Month3");
            spring = new Season(season);
            spring.setMonth1(month1);
            spring.setMonth2(month2);
            spring.setMonth3(month3);

            seasons.add(spring);
            //Summer
            jsonObject = jsonArray.getJSONObject(1);
            season = jsonObject.getString("Summer");
            month1 = jsonObject.getString("Month1");
            month2 = jsonObject.getString("Month2");
            month3 = jsonObject.getString("Month3");
            summer = new Season(season);
            summer.setMonth1(month1);
            summer.setMonth2(month2);
            summer.setMonth3(month3);
            seasons.add(summer);
            //Autumn
            jsonObject = jsonArray.getJSONObject(2);
            season = jsonObject.getString("Autumn");
            month1 = jsonObject.getString("Month1");
            month2 = jsonObject.getString("Month2");
            month3 = jsonObject.getString("Month3");
            autumn = new Season(season);
            autumn.setMonth1(month1);
            autumn.setMonth2(month2);
            autumn.setMonth3(month3);
            seasons.add(autumn);
            //Winter
            jsonObject = jsonArray.getJSONObject(3);
            season = jsonObject.getString("Winter");
            month1 = jsonObject.getString("Month1");
            month2 = jsonObject.getString("Month2");
            month3 = jsonObject.getString("Month3");
            winter = new Season(season);
            winter.setMonth1(month1);
            winter.setMonth2(month2);
            winter.setMonth3(month3);
            seasons.add(winter);

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
    public ArrayList<Time> getTimeData(String languageName, String dialect) {
        String url = URL+"/"+languageName+"/"+dialect+"/Time";

        ArrayList<Time> times = new ArrayList<>();

        try {
            JSONArray jsonArray = execute(url)
                    .get(); //this link is temporary, it needs to be generalized
            for(int i = 0 ; i < jsonArray.length(); i++ ){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String time = jsonObject.getString("phrase");
                String timeInLanguage = jsonObject.getString("phrase_in_language");
                String audioUrl = jsonObject.getString("audio_url");

                times.add((new Time(time, timeInLanguage, audioUrl)));
            }
        } catch (Exception e) {
            System.out.println("JSON EXCEPTION ERROR HERE");
            e.printStackTrace();
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
