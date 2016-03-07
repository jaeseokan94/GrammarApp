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
            QUESTION_TABLE + "(" + "questionText VARCHAR(100) PRIMARY KEY, choice_1 VARCHAR(100), choice_2 VARCHAR(100), choice_3 VARCHAR(100) "
            + ",choice_4 VARCHAR(100), choice_5 VARCHAR(100), choice_6 VARCHAR(100), correct_answer VARCHAR(100) )";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VER);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table successfully" + CREATE_TABLE_QUESTION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
        onCreate(db);

    }

    public void addQuestion(String questionText, String choice_1, String choice_2,
                            String choice_3, String choice_4, String choice_5, String choice_6
            , String correct_answer ) {

        System.out.println("Data added");

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
    }

    public List<QuestionData> getAllQuestion(String questionText) {
        List<QuestionData> questions = new LinkedList<QuestionData>();

        String query = "SELECT  * FROM " + QUESTION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        QuestionData questionData = null;

        if (cursor.moveToFirst()) {
            do {
                questionData = new QuestionData();
                questionData.setQuestionText(cursor.getString(0));
                questionData.setChoice_1(cursor.getString(1));
                questionData.setChoice_2(cursor.getString(2));
                questionData.setChoice_3(cursor.getString(3));
                questionData.setChoice_4(cursor.getString(4));
                questionData.setChoice_5(cursor.getString(5));
                questionData.setChoice_6(cursor.getString(6));
                questionData.setCorrect_answer(cursor.getString(7));

                questions.add(questionData);


            } while (cursor.moveToNext());
        }
        System.out.println("SELECT WORK!");

        return questions;
    }



}

/**String questionText, String choice_1, String choice_2,
String choice_3, String choice_4, String choice_5, String choice_6
        , String correct_answer
 **/