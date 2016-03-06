package com.example.spanishgrammarapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JAESEOKAN on 06/03/2016.
 */
public class Database extends SQLiteOpenHelper {

    public final static int DB_VER = 1;

    public static final String QUESTION_TEXT = "questionText";
    public static final String CHOICE_1 = "choice_1";
    public static final String CHOICE_2 = "choice_2";
    public static final String CHOICE_3 = "choice_3";
    public static final String CHOICE_4 = "choice_4";
    public static final String CHOICE_5 = "choice_5";
    public static final String CHOICE_6 = "choice_6";
    public static final String CORRECT_ANSWER = "correct_answer";
    public static final String TABLE_NAME = "question_data";

    /**
    public static String[] question_arraay = {
            Database.numOfQuestion,
            Database.questionText,
            Database.choice_1,
            Database.choice_2,
            Database.choice_3,
            Database.choice_4,
            Database.choice_5,
            Database.choice_6,
            Database.correct_answer,

    };
     **/
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP EXISTING TABLE ");
        onCreate(db);

    }
    // check if data already exist
    public boolean insertQuestionArray(String questionText, String choice_1,
                                    String choice_2, String choice_3, String choice_4, String choice_5, String choice_6, String correct_answer){

        SQLiteDatabase db_exercise_question = this.getWritableDatabase();



        return true;
    }


}
