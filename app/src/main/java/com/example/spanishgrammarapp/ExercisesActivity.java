package com.example.spanishgrammarapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("ResourceType")
public class ExercisesActivity extends AppCompatActivity {
    // Member variables for question types, to prevent bugs due to typos.
    public static final String multipleChoice = "mc";
    public static final String trueFalse = "tf";
    public static final String dragAndDrop = "dnd";
    public static final String typing = "t";

    private RelativeLayout relativeLayout; //accessed multiple times throughout the code in various methods.
    private String cAnswer; //accessed in multiple methods.
    private ArrayList<String> answers; //accessed in multiple methods.
    private TextView question;//This was previously declared in onCreate(), but it needs to be private for DragAndDrop
    private ArrayList<Question> exerciseQuestions; //accessed in multiple methods.
    private int currentQuestionIndex = 0;  //member variable because it is going to be accessed by review functionality
    private int correctlyAnswered = 0; //used for displaying results at the end of an exercise

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Intent intent = getIntent();
        Exercise exerciseReceived = intent.getParcelableExtra(MainActivity.QUESTIONS);
        exerciseQuestions = exerciseReceived.getQuestions();

        relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(relativeLayout, relativeParams);
        question = new TextView(this);
        question.setTextSize(30);
        question.setId(10000);
        reconstructGUI();
    }

    private void reconstructGUI(){
        relativeLayout.removeAllViews();
        question.setText(exerciseQuestions.get(currentQuestionIndex).getQuestion());
        relativeLayout.addView(question);
        cAnswer = exerciseQuestions.get(currentQuestionIndex).getCorrectAnswer();
        answers = exerciseQuestions.get(currentQuestionIndex).getAnswers();

        switch (exerciseQuestions.get(currentQuestionIndex).getQuestionType()){
            case multipleChoice:
                constructMultipleChoice();
                break;
            case trueFalse:
                constructTrueFalse();
                break;
            case dragAndDrop:
                constructDragAndDrop();
                break;
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
                System.out.println(trueButton.getTag()+"");
                results(trueFalse, trueButton);
            }
        });
        final Button falseButton = new Button(this);
        falseButton.setText("False");
        falseButton.setId(1020);
        falseButton.setTag("false");
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results(trueFalse, falseButton);
            }
        });
        RelativeLayout.LayoutParams trueBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        trueBtnParams.addRule(RelativeLayout.ABOVE, falseButton.getId());

        RelativeLayout.LayoutParams falseBtnParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        falseBtnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        relativeLayout.addView(trueButton, trueBtnParams);
        relativeLayout.addView(falseButton, falseBtnParams);
    }

    private void constructDragAndDrop(){

        GridLayout optionsLayout = new GridLayout(this);
        optionsLayout.setOrientation(GridLayout.VERTICAL);
        optionsLayout.setId(1001);

        RelativeLayout.LayoutParams gridLayoutParams =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(optionsLayout, gridLayoutParams);


        question.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
//                    v.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });

        Display screen = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        screen.getSize(size);
        int imageWidth = size.x / answers.size();
        int imageHeight = size.y / answers.size();

        ImageView[] images = new ImageView[answers.size()];
        for (int i = 0; i < answers.size(); i++) {
                images[i] = new ImageView(this);
                images[i].setTag(answers.get(i));
                new DownloadImageTask(images[i]).execute(answers.get(i));
                images[i].setOnDragListener(new View.OnDragListener() {
                    @Override
                    public boolean onDrag(View v, DragEvent event) {
                        switch(event.getAction())
                        {
                            case DragEvent.ACTION_DRAG_STARTED:
                                break;
                            case DragEvent.ACTION_DRAG_ENTERED:
                                break;
                            case DragEvent.ACTION_DRAG_EXITED:
                                break;
                            case DragEvent.ACTION_DRAG_LOCATION:
                                break;
                            case DragEvent.ACTION_DRAG_ENDED:
                                break;
                            case DragEvent.ACTION_DROP:
                                results(dragAndDrop,v);
                                break;
                            default: break;
                        }return true;}
                });

                images[i].setLayoutParams(new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT));
                images[i].getLayoutParams().height=imageHeight;
                images[i].getLayoutParams().width=imageWidth;
                optionsLayout.addView(images[i]);
        }

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
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    results(typing, userInput);
                }
                return true;
            }
        });
        RelativeLayout.LayoutParams editTextParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        editTextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayout.addView(userInput, editTextParams);
    }

    public void results(String qType, View view){ //needs rework
        boolean correct = false;
        String userAnswer = view.getTag().toString();
        exerciseQuestions.get(currentQuestionIndex).userGivenAnswer = userAnswer;
        switch (qType){
            case multipleChoice:
                userAnswer = view.getTag().toString();
                if (view.getTag().equals(cAnswer)){
                    correct=true;
                    ++correctlyAnswered;
                }
                break;
            case trueFalse:
                if (view.getTag().equals(cAnswer.toLowerCase().trim())){
                    correct=true;
                    ++correctlyAnswered;
                }
                break;
            case dragAndDrop:
                if(view.getTag().equals(cAnswer)){
                    correct=true;
                    ++correctlyAnswered;
                }
                break;
            case typing:
                if (((EditText) view).getText().toString().trim().equals(cAnswer)){
                    correct=true;
                    ++correctlyAnswered;
                }
        }

        final AlertDialog alert = new AlertDialog.Builder(ExercisesActivity.this).create();
        if(correct) {
            alert.setTitle("Well Done");
            alert.setMessage("This is the Correct Answer!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    exerciseQuestions.get(currentQuestionIndex).setCompleted(true);
                    exerciseQuestions.get(currentQuestionIndex).addAttempt();
                    if(currentQuestionIndex==exerciseQuestions.size()-1) {
                        //we have reached the end of the exercise
                        ratingDialog();
                    }else{
                        ++currentQuestionIndex;
                        reconstructGUI(); //goes to next question
                    }
                }
            });
        }else{
            alert.setTitle("Try Again");
            alert.setMessage("Incorrect Answer!");
            alert.setButton("TryAgain", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    exerciseQuestions.get(currentQuestionIndex).addAttempt(); //you tried, you failed.
                }
            });
        }
        System.out.println(exerciseQuestions.get(currentQuestionIndex).userGivenAnswer);
        alert.show();
    }

    /* This method displays the rating for the exercises with the appropriate pictures*/
    private void ratingDialog(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams((int) (screenWidth*0.6), (int) (screenWidth*0.18));
        ImageView rating = new ImageView(getBaseContext());
        int totalQuestions = exerciseQuestions.size();
        if(correctlyAnswered/totalQuestions<0.4) {
            rating.setBackground(getDrawable(R.drawable.star0));
        }else if(correctlyAnswered/totalQuestions<0.6){
            rating.setBackground(getDrawable(R.drawable.star1));
        }else if(correctlyAnswered/totalQuestions<0.8){
            rating.setBackground(getDrawable(R.drawable.star2));
        }else if(correctlyAnswered/totalQuestions>=0.8){
            rating.setBackground(getDrawable(R.drawable.star3));
        }

        dialog.setTitle("Exercise complete");
        dialog.addContentView(rating, rlp);
        dialog.show();
    }

    //This private class is used to set an Image to an ImageView from the given URL
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
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
