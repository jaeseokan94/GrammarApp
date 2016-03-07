package com.example.spanishgrammarapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JAESEOKAN on 06/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Tag just for the logcat window
    private static String TAG = "DataBaseHelper";
    //Database Version
    private final static int DB_VER = 1;
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


    private static final String CREATE_TABLE_QUESTION = "CREATE TABLE "+
            QUESTION_TABLE + "("+ "questionText VARCHAR(100) PRIMARY KEY, choice_1 VARCHAR(100), choice_2 VARCHAR(100), choice_3 VARCHAR(100) "
            +",choice_4 VARCHAR(100), choice_5 VARCHAR(100), choice_6 VARCHAR(100), correct_answer VARCHAR(100) )"  ;





    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table successfully" + CREATE_TABLE_QUESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ QUESTION_TABLE);
        onCreate(db);

    }

    public void addQuestion(QuestionData questionData){

        System.out.println("Data added");

        SQLiteDatabase db = this.getWritableDatabase();
        // To add column
        ContentValues values = new ContentValues(); // this class is used to store a values 
        values.put(QUESTION_TEXT, questionData.getQuestionText());
        values.put(CHOICE_1, questionData.getChoice_1());
        values.put(CHOICE_2, questionData.getChoice_2());
        values.put(CHOICE_3, questionData.getChoice_3());
        values.put(CHOICE_4, questionData.getChoice_4());
        values.put(CHOICE_5, questionData.getChoice_5());
        values.put(CHOICE_6, questionData.getChoice_6());
        values.put(CORRECT_ANSWER, questionData.getCorrect_answer());

        db.insert(QUESTION_TABLE, null, values);

        db.close();
    }



    // check if data already exist
    public boolean insertQuestionArray(String questionText, String choice_1,
                                    String choice_2, String choice_3, String choice_4, String choice_5, String choice_6, String correct_answer){

        SQLiteDatabase db_exercise_question = this.getWritableDatabase();



        return true;
    }


}

/**String questionText, String choice_1, String choice_2,
String choice_3, String choice_4, String choice_5, String choice_6
        , String correct_answer
 **/