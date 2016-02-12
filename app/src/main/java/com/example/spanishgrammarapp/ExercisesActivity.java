package com.example.spanishgrammarapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressWarnings("ResourceType")
public class ExercisesActivity extends AppCompatActivity {

    private Intent intent;
    private RelativeLayout relativeLayout;
    private TextView question;
    private String cAnswer;
    private ArrayList<String> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        intent = getIntent();
        relativeLayout = new RelativeLayout(this);
        question = new TextView(this);
        cAnswer = intent.getStringExtra("cAnswer");
        answers = intent.getStringArrayListExtra("answers");

        RelativeLayout.LayoutParams relativeParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        question.setText(intent.getStringExtra("question"));
        question.setId(10000);

        addContentView(relativeLayout, relativeParams);
        relativeLayout.addView(question);

        switch (intent.getStringExtra("questionType")){
            case "mc":
                constructMultipleChoice();
                return;
            case "tf":
                constructTrueFalse();
                return;
            case "dnd":
                constructDragAndDrop();
                return;
            case "t":
                constructTypingActivity();
        }
//        constructTrueFalse();
    }

    private void constructMultipleChoice(){
        LinearLayout top = new LinearLayout(this);
        top.setId(10001);
        GridLayout bottom = new GridLayout(this);
        bottom.setId(10002);
        bottom.setColumnCount(1);

        RelativeLayout.LayoutParams linearLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        relativeLayout.addView(top, linearLayoutParams);

        RelativeLayout.LayoutParams gridLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(bottom, gridLayoutParams);

        Button[] optionsButton = new Button[answers.size()];
        for (int i = 0; i < answers.size(); i++) {
            optionsButton[i] = new Button(this);
            optionsButton[i].setLayoutParams(new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));
            optionsButton[i].setText(answers.get(i));
            bottom.addView(optionsButton[i]);
        }

        // This code seems highly inefficient, but the reason for having two listeners rather than one is
        // so that it is possible to use an anonymous inner class. Otherwise we'd have to deal with final variables,
        // or having a separate class for the listener.
        for (int i = 0; i < optionsButton.length; i++) {
            if (optionsButton[i].getText().equals(cAnswer)) {
                optionsButton[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alert = new AlertDialog.Builder(ExercisesActivity.this).create();
                        alert.setTitle("Well Done");
                        alert.setMessage("This is the Correct Answer!");
                        alert.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //write what it should do when the answer is incorrect
                            }
                        });
                        alert.show();
                    }
                });
            } else {
                optionsButton[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alert = new AlertDialog.Builder(ExercisesActivity.this).create();
                        alert.setTitle("Try Again");
                        alert.setMessage("Incorrect Answer!");
                        alert.setButton("TryAgain", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //write what it should do when the answer is incorrect
                            }
                        });
                        alert.show();
                    }
                });
            }
        }
    }


    /*
    * Unfinished method
    * */
    private void constructTrueFalse(){
        Button trueButton = new Button(this);
        trueButton.setText("True");
        trueButton.setId(1010);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results();
            }
        });

        Button falseButton = new Button(this);
        falseButton.setText("False");
        falseButton.setId(1020);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results();
            }
        });

        RelativeLayout.LayoutParams trueBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        trueBtnParams.addRule(RelativeLayout.BELOW, 10000);

        RelativeLayout.LayoutParams falseBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        falseBtnParams.addRule(RelativeLayout.BELOW, trueButton.getId());

        relativeLayout.addView(trueButton, trueBtnParams);
        relativeLayout.addView(falseButton, falseBtnParams);
    }

    private void constructDragAndDrop(){
//        Placeholder
    }

    private void constructTypingActivity(){
//        Placeholder
    }

    public void results(){
        Dialog resultsDialog = new Dialog(this);
        resultsDialog.setTitle("Results");
        TextView credits = new TextView(this);
        credits.setText("*insert dank meme*");
        resultsDialog.addContentView(credits,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        resultsDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercises, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
