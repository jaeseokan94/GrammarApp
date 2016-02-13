package com.example.spanishgrammarapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressWarnings("ResourceType")
public class ExercisesActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private String cAnswer;
    private ArrayList<String> answers;
    public static final String multipleChoice = "mc";
    public static final String trueFalse = "tf";
    public static final String dragAndDrop = "dnd";
    public static final String typing = "t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Intent intent = getIntent();
        relativeLayout = new RelativeLayout(this);
        TextView question = new TextView(this);
        question.setTextSize(30);
        cAnswer = intent.getStringExtra("cAnswer");
        answers = intent.getStringArrayListExtra("answers");

        RelativeLayout.LayoutParams relativeParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        question.setText(intent.getStringExtra("question"));
        question.setId(10000);

        addContentView(relativeLayout, relativeParams);
        relativeLayout.addView(question);

        switch (intent.getStringExtra("questionType")){
            case multipleChoice:
                constructMultipleChoice();
                return;
            case trueFalse:
                constructTrueFalse();
                return;
            case dragAndDrop:
                constructDragAndDrop();
                return;
            case typing:
                constructTypingActivity();
        }
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
            optionsButton[i].setTag(answers.get(i));
            bottom.addView(optionsButton[i]);
            optionsButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    results(multipleChoice, v);
                }
            });
        }
    }

    private void constructTrueFalse(){
        final Button trueButton = new Button(this);
        trueButton.setText("True");
        trueButton.setId(1010);
        trueButton.setTag("true");
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results(trueFalse, trueButton);
            }
        });
        final Button falseButton = new Button(this);
        falseButton.setText("False");
        falseButton.setId(1020);
        falseButton.setTag("false");
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results(trueFalse, falseButton);
            }
        });

        RelativeLayout.LayoutParams trueBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        trueBtnParams.addRule(RelativeLayout.ABOVE, falseButton.getId());
        trueBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelativeLayout.LayoutParams falseBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        falseBtnParams.addRule(RelativeLayout.ALIGN_BOTTOM);
        falseBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        relativeLayout.addView(trueButton, trueBtnParams);
        relativeLayout.addView(falseButton, falseBtnParams);
    }

    private void constructDragAndDrop(){
//        Placeholder
    }

    private void constructTypingActivity(){
        final EditText userInput = new EditText(this);
        userInput.setId(10100);
        userInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        userInput.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    results(typing, userInput);
                }
                return true;
            }
        });
        RelativeLayout.LayoutParams editTextParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editTextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(userInput, editTextParams);
    }

    public void results(String qType, View view){
        boolean correct = false;
        switch (qType){
            case multipleChoice:
                if (view.getTag().equals(cAnswer)){correct=true;}
            case trueFalse:
                if (view.getTag().equals(cAnswer.toLowerCase())){correct=true;}
            case dragAndDrop:
                //TODO: Drag and drop question type
            case typing:
                if (((EditText) view).getText().toString().trim().equals(cAnswer)){correct=true;}
        }

        AlertDialog alert = new AlertDialog.Builder(ExercisesActivity.this).create();
        if(correct) {
            alert.setTitle("Well Done");
            alert.setMessage("This is the Correct Answer!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //write what it should do when the answer is correct
                    //TODO: probably go to next question?
                }
            });
        }else{
            alert.setTitle("Try Again");
            alert.setMessage("Incorrect Answer!");
            alert.setButton("TryAgain", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //write what it should do when the answer is incorrect
                }
            });
        }
        alert.show();
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
