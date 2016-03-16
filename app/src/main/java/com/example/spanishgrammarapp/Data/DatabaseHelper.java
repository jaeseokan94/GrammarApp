package com.example.spanishgrammarapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by JAESEOKAN on 06/03/2016.
 *
 * TODO : SituationalVideo, GrammarVideo -> Need to be add into KEY DATA TABLE
 * TODO : Resources
 * TODO : QuestionExercise TABLE -> Foreign Key : subtopic from KEY DATA TABLE
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Tag just for the logcat window
    private static String TAG = "DataBaseHelper";
    //Database Version
    private final static int DB_VER = 1;

    private Context myContext;

    //Database name
    private static final String DATABASE_NAME = "apiManager";

    //KEY DATA
    private static final String KEY_TABLE = "subtopic_table";
    private static final String LANGUAGE = "language";
    private static final String LEVEL = "level";
    private static final String TOPIC = "topic";
    private static final String SUBTOPIC = "subtopic";
    private static final String KEY_ID = "id";

    private static final String CREATE_KEY_TABLE = "CREATE TABLE " +
           KEY_TABLE+"("+ "id INTEGER PRIMARY KEY AUTOINCREMENT, language VARCHAR, level VARCHAR, topic VARCHAR," +
            " subtopic VARCHAR);";


    //QUESTION_TABLE
    private static final String QUESTION_TABLE = "question_data";
    private static final String QUESTION_TEXT = "questionText";
    private static final String CHOICE_1 = "choice_1";
    private static final String CHOICE_2 = "choice_2";
    private static final String CHOICE_3 = "choice_3";
    private static final String CHOICE_4 = "choice_4";
    private static final String CHOICE_5 = "choice_5";
    private static final String CHOICE_6 = "choice_6";
    private static final String CORRECT_ANSWER = "correct_answer";
    private static final String[] QUESTION_COLUMNS = {QUESTION_TEXT, CHOICE_1, CHOICE_2, CHOICE_3, CHOICE_4, CHOICE_5, CHOICE_6, CORRECT_ANSWER};

    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE " +
            QUESTION_TABLE + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, questionText VARCHAR, choice_1 VARCHAR, choice_2 VARCHAR, choice_3 VARCHAR"
            + ",choice_4 VARCHAR, choice_5 VARCHAR, choice_6 VARCHAR, correct_answer VARCHAR);";

    //FOREIGN KEY(customer_id) REFERENCES customers(id),


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_KEY_TABLE);
        db.execSQL(CREATE_TABLE_QUESTION);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_TABLE);
        onCreate(db);
    }


    public String IDGenerator(String questionText) {
        return questionText;
    }

    public String IDGenerator_language(String language){
        return language;
    }
    public String IDGenerator_subtopic(String language,String level,String topic, String subtopic){
        return language+level+topic+subtopic;
    }
    public String IDGenerator_subtopic_name(String language,String level,String topic){
        return language+level+topic;
    }
    public String IDGenerator_subtopic_name_test(String language, String level){
        return language+" "+level;
    }

    //Add language
    public boolean addLanguage(String language) {
        //check if data exist
        SQLiteDatabase dbCheck = this.getReadableDatabase();
        String Query = "SELECT " + "*" + " FROM " + KEY_TABLE + " WHERE " + LANGUAGE + " = ? LIMIT 1"; //Replace questionText to primary key or ID

        Cursor cursor = dbCheck.rawQuery(Query, new String[]{language});

        System.out.println("DATA Language");

        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        SQLiteDatabase db = this.getWritableDatabase();

        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values
        values.put(LANGUAGE, language);

        db.insert(KEY_TABLE, null, values);

        db.close();
        return true;
    }

    public boolean addLevel(String language, String level) {

        //check if data exist
        String Query = "SELECT *"+ " FROM " + KEY_TABLE + " WHERE language =? AND level =? LIMIT 1" ;

        System.out.println(Query);

        SQLiteDatabase dbc = this.getReadableDatabase();
        Cursor cursor = dbc.rawQuery(Query, new String[]{language, level});

        if (cursor.getCount() != 0) {
            cursor.close();
            System.out.println("DATA LEVEL EXIST WORK!!");
            return false;
        }
        cursor.close();

        SQLiteDatabase db = this.getWritableDatabase();

        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values
        values.put(LANGUAGE, language);
        values.put(LEVEL, level);

        db.insert(KEY_TABLE, null, values);

        db.close();
        System.out.println("DATA ADDED SUCCESSFULLY");

        return true;
    }


    public boolean addTopic(String language, String level, String topic) {

        //check if data exist
        String Query = "SELECT *"+ " FROM " + KEY_TABLE + " WHERE language =? AND level =? AND topic =? LIMIT 1" ;

        System.out.println(Query);
        SQLiteDatabase dbc = this.getReadableDatabase();
        Cursor cursor = dbc.rawQuery(Query, new String[]{language, level, topic});

        if (cursor.getCount() != 0) {
            cursor.close();
            System.out.println("DATA TOPIC EXIST WORK!!");
            return false;
        }
        cursor.close();

        SQLiteDatabase db = this.getWritableDatabase();

        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values
        values.put(LANGUAGE, language);
        values.put(LEVEL, level);
        values.put(TOPIC,topic);

        db.insert(KEY_TABLE, null, values);

        db.close();
        System.out.println("DATA ADDED SUCCESSFULLY");

        return true;
    }


    /*
    INSERT INTO first_table_name [(column1, column2, ... columnN)]
   SELECT column1, column2, ...columnN
   FROM second_table_name
   [WHERE condition];
     */
//language, level, topic, subtopic)
    public boolean addSubtopic(String language, String level, String topic, String subtopic) {
        //check if data exist
        System.out.println("AAAAAAAAAAAAAA");


        String Query = "SELECT *"+ " FROM " + KEY_TABLE + " WHERE language =? AND level =? AND topic =? AND subtopic =? LIMIT 1" ;

        System.out.println(Query);


        SQLiteDatabase dbc = this.getReadableDatabase();
        Cursor cursor = dbc.rawQuery(Query, new String[]{language,level,topic,subtopic});

        if (cursor.getCount() != 0) {
            cursor.close();
            System.out.println("DATA SUBTOPIC EXIST WORK!!");
            return false;
        }
        cursor.close();

        SQLiteDatabase db = this.getWritableDatabase();

        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values
        values.put(LANGUAGE, language);
        values.put(LEVEL, level);
        values.put(TOPIC,topic);
        values.put(SUBTOPIC, subtopic);

        db.insert(KEY_TABLE, null, values);

        db.close();
        System.out.println("DATA ADDED SUCCESSFULLY");

        return true;
    }




    /* simply add questionData into Database.
     */
    public boolean addQuestion(String questionText, String choice_1, String choice_2,
                            String choice_3, String choice_4, String choice_5, String choice_6
            , String correct_answer ) {

        //check if data exist
        SQLiteDatabase dbCheck = this.getReadableDatabase();
        String Query = "SELECT " + "*" + " FROM " + QUESTION_TABLE + " WHERE " + QUESTION_TEXT + " = ?"; //Replace questionText to primary key or ID
        String ID = IDGenerator(questionText);
        Cursor cursor = dbCheck.rawQuery(Query, new String[]{ID});
        System.out.println("DATA Check");

        if (cursor.getCount() != 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        System.out.println("DATA ADDED SUCCESSFULLY");
        SQLiteDatabase db = this.getWritableDatabase();
        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values
        values.put(QUESTION_TEXT, questionText);
        values.put(CHOICE_1, choice_1);
        values.put(CHOICE_2, choice_2);
        values.put(CHOICE_3, choice_3);
        values.put(CHOICE_4, choice_4);
        values.put(CHOICE_5, choice_5);
        values.put(CHOICE_6, choice_6);
        values.put(CORRECT_ANSWER, correct_answer);

        db.insert(QUESTION_TABLE, null, values);

        db.close();
        return true;
    }




    /**
     * returns table of questionData -> "ID, question_text, choice1..6 , correct_answer"
     * TODO: Change param to subtopic name and topic name
     */
    public QuestionData getQuestionText(String questionText) // this need to be modified to get subtopic and topic name as parameters
    {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT " + "*" + " FROM " + QUESTION_TABLE + " WHERE " + QUESTION_TEXT + " = ?"; //Replace questionText to primary key or ID
        String ID = IDGenerator(questionText);
        Cursor cursor = db.rawQuery(Query, new String[]{ID});


        if( cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();
            QuestionData questionData = new QuestionData();
            questionData.setQuestionText(cursor.getString(cursor.getColumnIndex(QUESTION_TEXT)));
            questionData.setChoice_1(cursor.getString(cursor.getColumnIndex(CHOICE_1)));
            questionData.setChoice_2(cursor.getString(cursor.getColumnIndex(CHOICE_2)));
            questionData.setChoice_3(cursor.getString(cursor.getColumnIndex(CHOICE_3)));
            questionData.setChoice_4(cursor.getString(cursor.getColumnIndex(CHOICE_4)));
            questionData.setChoice_5(cursor.getString(cursor.getColumnIndex(CHOICE_5)));
            questionData.setChoice_6(cursor.getString(cursor.getColumnIndex(CHOICE_6)));
            questionData.setCorrect_answer(cursor.getString(cursor.getColumnIndex(CORRECT_ANSWER)));

        cursor.close();
        System.out.println("Get question data work");


        return questionData;
    }

    public List<KeyData> getAllKey() {
        List<KeyData> keys = new LinkedList<KeyData>();

        String query = "SELECT * FROM " + KEY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        KeyData keyData = null;

        if (cursor.moveToFirst()) {
            do {
                keyData = new KeyData();
                keyData.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                keyData.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
                keyData.setLevel(cursor.getString(cursor.getColumnIndex(LEVEL)));
                keyData.setTopic(cursor.getString(cursor.getColumnIndex(TOPIC)));
                keyData.setSubtopic(cursor.getString(cursor.getColumnIndex(SUBTOPIC)));

                keys.add(keyData);

            } while (cursor.moveToNext());
        }
        System.out.println("SELECT ALL WORK IN KEY DATA!");

        return keys;
    }

    /**
     * returns languageNameList
     */
    public List<LanguageData> getLanguageList() // this need to be modified to get subtopic and topic name as parameters
    {
        List<LanguageData> languages = new LinkedList<LanguageData>();

        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT DISTINCT language"+ " FROM " + KEY_TABLE  ;

        System.out.println(Query);

        Cursor cursor = db.rawQuery(Query, null);

        LanguageData languageList = null;

        if (cursor.moveToFirst()) {
            do {
                languageList = new LanguageData();
                languageList.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
                languages.add(languageList);

            } while (cursor.moveToNext());
        }

        return languages;

    }

    /**
     * returns languageNameList
     */
    public List<LevelData> getLevelList(String language) // this need to be modified to get subtopic and topic name as parameters
    {
        List<LevelData> levels = new LinkedList<LevelData>();

        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT DISTINCT level"+ " FROM " + KEY_TABLE + " WHERE language =? "  ;

        System.out.println(Query);

        Cursor cursor = db.rawQuery(Query, new String[]{language});

        LevelData levelList = null;
        if(cursor!=null) {
            if (cursor.moveToFirst()) {
                System.out.println("DO I ADD?");
                do {
                    levelList = new LevelData();
                    levelList.setLevel(cursor.getString(cursor.getColumnIndex(LEVEL)));
                    levels.add(levelList);

                } while (cursor.moveToNext());
            }

        }
        System.out.println("DID I ADD?");

        return levels;

    }

    public List<KeyData> getSubTopicList(String language, String level, String topic) // this need to be modified to get subtopic and topic name as parameters
    {
        List<KeyData> subtopics = new LinkedList<KeyData>();

        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT DISTINCT level"+ " FROM " + KEY_TABLE + " WHERE language =? AND level =? AND topic =? "  ;

        System.out.println(Query);

        Cursor cursor = db.rawQuery(Query, null);

        KeyData subtopicList = null;

        if (cursor.moveToFirst()) {
            do {
                subtopicList = new KeyData();
                subtopicList.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
                subtopicList.setLanguage(cursor.getString(cursor.getColumnIndex(LEVEL)));
                subtopicList.setLanguage(cursor.getString(cursor.getColumnIndex(TOPIC)));
                subtopicList.setLanguage(cursor.getString(cursor.getColumnIndex(SUBTOPIC)));
                subtopics.add(subtopicList);

            } while (cursor.moveToNext());
        }

        return subtopics;

    }

    /**
     * returns subtopic Name
     */
    public KeyData getLevelName(String language) // this need to be modified to get subtopic and topic name as parameters
    {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT *"+ " FROM " + KEY_TABLE + " WHERE language =? " ;

        System.out.println(Query);

        Cursor cursor = db.rawQuery(Query, new String[]{language});

        if( cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();

        KeyData keyData = new KeyData();
        keyData.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        keyData.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
        keyData.setLevel(cursor.getString(cursor.getColumnIndex(LEVEL)));

        cursor.close();
        System.out.println("Get LEVEL NAME WORK !!");


        return keyData;
    }


    /**
     * returns subtopic Name
     * TODO: @param to Lang, level, topic name
     */
    public KeyData getSubtopicName(String language, String level, String topic) // this need to be modified to get subtopic and topic name as parameters
    {
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        String Query = "SELECT *"+ " FROM " + KEY_TABLE + " WHERE language =? AND level =? AND topic =?" ;

        System.out.println(Query);

        Cursor cursor = db.rawQuery(Query, new String[]{language,level,topic});

        if( cursor != null && cursor.getCount()>0)
            cursor.moveToFirst();

        KeyData keyData = new KeyData();
        keyData.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
        keyData.setLanguage(cursor.getString(cursor.getColumnIndex(LANGUAGE)));
        keyData.setLevel(cursor.getString(cursor.getColumnIndex(LEVEL)));
        keyData.setTopic(cursor.getString(cursor.getColumnIndex(TOPIC)));
        keyData.setSubtopic(cursor.getString(cursor.getColumnIndex(SUBTOPIC)));

        cursor.close();
        System.out.println("Get SUBTOPIC WORK !!");


        return keyData;
    }


    public List<QuestionData> getAllQuestion() {
        List<QuestionData> questions = new LinkedList<QuestionData>();

        String query = "SELECT * FROM " + QUESTION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        QuestionData questionData = null;

        if (cursor.moveToFirst()) {
            do {
                questionData = new QuestionData();
                questionData.setQuestionText(cursor.getString(cursor.getColumnIndex(QUESTION_TEXT)));
                questionData.setChoice_1(cursor.getString(cursor.getColumnIndex(CHOICE_1)));
                questionData.setChoice_2(cursor.getString(cursor.getColumnIndex(CHOICE_2)));
                questionData.setChoice_3(cursor.getString(cursor.getColumnIndex(CHOICE_3)));
                questionData.setChoice_4(cursor.getString(cursor.getColumnIndex(CHOICE_4)));
                questionData.setChoice_5(cursor.getString(cursor.getColumnIndex(CHOICE_5)));
                questionData.setChoice_6(cursor.getString(cursor.getColumnIndex(CHOICE_6)));
                questionData.setCorrect_answer(cursor.getString(cursor.getColumnIndex(CORRECT_ANSWER)));

                questions.add(questionData);

            } while (cursor.moveToNext());
        }
        System.out.println("SELECT ALL WORK!");

        return questions;
    }



}

/**
 topicName, situation_description, video_with_transcript, video_without_transcript

 private static final String SITUATIONAL_VIDEO_TABLE = "situational_video_table";
 private static final String TOPIC_NAME = "topic_name";
 private static final String SITUATION_DESCRIPTION = "situation_description";
 private static final String VIDEO_WITH_TRANSCRIPT = "video_with_transcript";
 private static final String VIDEO_WITHOUT_TRANSCRIPT = "video_without_transcript";

 private static final String CREATE_SITUATIONAL_VIDEO_TABLE = "CREATE TABLE " +
 SITUATIONAL_VIDEO_TABLE + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, TOPIC_NAME VARCHAR, SITUATION_DESCRIPTION VARCHAR, " +
 "VIDEO_WITH_TRANSCRIPT VARCHAR, VIDEO_WITHOUT_TRANSCRIPT VARCHAR);";

 public boolean addSituationalVideo(topic_name, situation_description, video_with_transcript, video_without_transcript) {

 //check if data exist
 SQLiteDatabase dbCheck = this.getReadableDatabase();
 String Query = "SELECT " + "*" + " FROM " + QUESTION_TABLE + " WHERE " + QUESTION_TEXT + " = ?"; //Replace questionText to primary key or ID
 String ID = IDGenerator(questionText);
 Cursor cursor = dbCheck.rawQuery(Query, new String[]{ID});
 System.out.println("DATA Check");

 if (cursor.getCount() != 0) {
 cursor.close();
 return false;
 }
 cursor.close();

 System.out.println("DATA ADDED SUCCESSFULLY");
 SQLiteDatabase db = this.getWritableDatabase();
 // To add column
 ContentValues values = new ContentValues(); // this class is used to store a values
 values.put(QUESTION_TEXT, questionText);
 values.put(CHOICE_1, choice_1);
 values.put(CHOICE_2, choice_2);


 db.insert(QUESTION_TABLE, null, values);

 db.close();
 return true;
 }
 **/