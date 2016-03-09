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
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Tag just for the logcat window
    private static String TAG = "DataBaseHelper";
    //Database Version
    private final static int DB_VER = 1;

    private Context myContext;

    //Database name
    private static final String DATABASE_NAME = "apiManager";


    private static final String QUESTION_TEXT = "questionText";
    private static final String CHOICE_1 = "choice_1";
    private static final String CHOICE_2 = "choice_2";
    private static final String CHOICE_3 = "choice_3";
    private static final String CHOICE_4 = "choice_4";
    private static final String CHOICE_5 = "choice_5";
    private static final String CHOICE_6 = "choice_6";
    private static final String CORRECT_ANSWER = "correct_answer";
    private static final String QUESTION_TABLE = "question_data"; //table name

    private static final String[] QUESTION_COLUMNS = {QUESTION_TEXT, CHOICE_1, CHOICE_2, CHOICE_3, CHOICE_4, CHOICE_5, CHOICE_6, CORRECT_ANSWER};


    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE " +
            QUESTION_TABLE + "(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, questionText VARCHAR, choice_1 VARCHAR, choice_2 VARCHAR, choice_3 VARCHAR"
            + ",choice_4 VARCHAR, choice_5 VARCHAR, choice_6 VARCHAR, correct_answer VARCHAR);";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
        onCreate(db);

    }


    public String IDGenerator(String questionText) {
        return questionText;
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

/**String questionText, String choice_1, String choice_2,
String choice_3, String choice_4, String choice_5, String choice_6
        , String correct_answer
 **/