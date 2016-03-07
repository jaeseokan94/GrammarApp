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
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private int totalQuestions;
    private Exercise exerciseReceived;
    private Intent afterExerciseIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Intent intent = getIntent();
        /*What object am I pointing to with exerciseReceived? Well, if it was a previously started
        * or completed exercise, then it's that. Otherwise it's the one created in
        * SubtopicsActivity, upon entering the subtopic/exercise. It is passed to CMSconnector
        * which populates it with the actual data.*/
//        exerciseReceived = (Exercise) intent.getSerializableExtra(MainActivity.QUESTIONS);

        exerciseReceived = getOrMakeExercise(intent.getStringExtra(MainActivity.SUBTOPIC), intent.getStringExtra(MainActivity.TOPIC));

        exerciseQuestions = exerciseReceived.getQuestions();
        totalQuestions = exerciseQuestions.size();

        afterExerciseIntent = new Intent(this, SubtopicsActivity.class);
        intent.putExtra(MainActivity.TOPIC, intent.getStringExtra(MainActivity.TOPIC));

        relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(relativeLayout, relativeParams);
        question = new TextView(this);
        question.setTextSize(30);
        question.setId(10000);

        String identifier = intent.getStringExtra(MainActivity.TOPIC)+"/"+intent.getStringExtra(MainActivity.SUBTOPIC);
        resumeUserProgress(identifier);
        reconstructGUI();
    }

    /*This method will return an exercise if it either finds it in the collection of either completed or in-progress
    * exercises, and if not found in there then it will make and populate a new exercise for this topic/subtopic
    * @param subtopic The subtopic of the Topic that we want to get the exercise for
    * @param topic The topic of the exercise
    * @return Exercise for this configuration of topic/subtopic*/
    private Exercise getOrMakeExercise(String subtopic, String topic){
        String identifier = topic+"/"+subtopic;
        Exercise exercise = new Exercise(identifier);
        CMSconnector connector = new CMSconnector(exercise, topic); //pass that empty Exercise to the CMSconnector
        connector.constructExercise(); //the connector populates it with data from the DB
        getExercise(identifier);
//        if(UserProgress.exercisesInProgress.size()>0) {
//            for (Exercise e : UserProgress.exercisesInProgress) {
//                if (e.getIdentifier().equals(identifier)) {
//                    System.out.println("We did it Reddit! Exercise in Progress detected.");
//                    exercise = e;
//                }
//            }
//        }
//        if(UserProgress.completedExercises.size()>0) {
//            for (Exercise e : UserProgress.completedExercises) {
//                if (e.getIdentifier().equals(identifier)) {
//                    System.out.println("We did it Reddit! Completed Exercise detected.");
//                    exercise = e;
//                }
//            }
//        }
        return exercise; //create a new Exercise, a set of questions (empty)
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
                System.out.println(trueButton.getTag() + "");
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
        userInput.setImeActionLabel("Check", KeyEvent.KEYCODE_ENTER);
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                System.out.println("keyCode is:   "+keyCode);
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    System.out.println("TEST SUCCESS: KEYCODE_ENTER");
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void results(String qType, View view){
        exerciseStarted(); //mark this exercise as started after completing first question
        boolean correct = false;
        String userAnswer;
        if (qType.equals(typing)){ //typing questions need a little different modification to the userAnswer
            userAnswer = ((EditText) view).getText().toString().trim();
        }else{
            userAnswer = view.getTag().toString();
        }
        if (userAnswer.equals(cAnswer)){
            correct=true;
            ++correctlyAnswered;
        }
        exerciseQuestions.get(currentQuestionIndex).userGivenAnswer = userAnswer;

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        if(correct) {
            alert.setTitle("Well Done");
            alert.setMessage("This is the Correct Answer!");
            exerciseQuestions.get(currentQuestionIndex).setCompleted(true);
        }else{
            alert.setTitle("Try Again");
            alert.setMessage("Incorrect Answer!");
            exerciseQuestions.get(currentQuestionIndex).setCompleted(true); //the definition of "Completed" has changed.
        }
        exerciseQuestions.get(currentQuestionIndex).addAttempt();
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (currentQuestionIndex == exerciseQuestions.size() - 1) {
                    //we have reached the end of the exercise
                    endOfExercise(correctlyAnswered / totalQuestions);
                } else {
                    ++currentQuestionIndex;
                    reconstructGUI(); //goes to next question
                }
            }
        });

        MainActivity.userProgress.saveProgress();
        System.out.println("USER ANSWER:    " + exerciseQuestions.get(currentQuestionIndex).userGivenAnswer);
        alert.create();
        alert.show();
    }

    /*This method is used to identify an exercise as in-progress*/
    private void exerciseStarted(){
        if(!UserProgress.exercisesInProgress.contains(exerciseReceived)){
            UserProgress.exercisesInProgress.add(exerciseReceived);
        }
    }

    /*This method does a couple of necessary things when we reach the end of the exercise.*/
    private void endOfExercise(double correctnessRating){
        exerciseReceived.setCorrectnessRating(correctnessRating);
        UserProgress.completedExercises.add(exerciseReceived);
        UserProgress.exercisesInProgress.remove(exerciseReceived); //we've completed the exercise, it's no longer in progress
        MainActivity.userProgress.saveProgress();
        ratingDialog(correctnessRating);
    }

    /* This method displays the rating for the exercises with the appropriate pictures*/
    private void ratingDialog(double correctnessRating){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams((int) (getScreenWidth()*0.6), (int) (getScreenWidth()*0.18));
        ImageView rating = new ImageView(getBaseContext());
        //unfortunately switch does not work with double
        if(correctnessRating<0.4) {
            rating.setBackground(getDrawable(R.drawable.star0));
        }else if(correctnessRating<0.6){
            rating.setBackground(getDrawable(R.drawable.star1));
        }else if(correctnessRating<0.8){
            rating.setBackground(getDrawable(R.drawable.star2));
        }else if(correctnessRating>=0.8){
            rating.setBackground(getDrawable(R.drawable.star3));
        }
        dialog.setTitle("Exercise complete");
        dialog.addContentView(rating, rlp);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(afterExerciseIntent);
            }
        });
        dialog.show();
    }

    private int getScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        return screenWidth;
    }

    private boolean isInProgressExercise(String identifier){
        if(UserProgress.exercisesInProgress.size()>0) {
            for (Exercise e : UserProgress.exercisesInProgress) {
                if (e.getIdentifier().equals(identifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCompletedExercise(String identifier){
        if(UserProgress.completedExercises.size()>0) {
            for (Exercise e : UserProgress.completedExercises) {
                if (e.getIdentifier().equals(identifier)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Exercise getExercise(String identifier){
        if(UserProgress.completedExercises.size()>0) {
            for (Exercise e : UserProgress.completedExercises) {
                if (e.getIdentifier().equals(identifier)) {
                    return e;
                }
            }
        }
        if(UserProgress.exercisesInProgress.size()>0) {
            for (Exercise e : UserProgress.exercisesInProgress) {
                if (e.getIdentifier().equals(identifier)) {
                    return e;
                }
            }
        }
        return null;
    }

    private void resumeUserProgress(final String identifier){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (isInProgressExercise(identifier)) {
            System.out.println("User has previously started this exercise");
            dialogBuilder.setTitle("You have previously started this exercise");
            dialogBuilder.setMessage("Would you like to resume from where you have finished?\n" +
                    "Pressing \"No\" will restart this exercise.");
        }else if (isCompletedExercise(identifier)) {
            System.out.println("User has completed this exercise");
            dialogBuilder.setTitle("You have already completed this exercise");
            dialogBuilder.setMessage("Would you like to restart this exercise?");
        }else {
            return;
        }
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isCompletedExercise(identifier)) {
                        restartExercise(identifier);
                    }
                    if(isInProgressExercise(identifier)){
                        //resume from first uncompleted question
                        while (exerciseQuestions.get(currentQuestionIndex).isCompleted()) {
                            System.out.println("Completed question detected");
                            ++currentQuestionIndex;
                        }
                        reconstructGUI();
                    }
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(isInProgressExercise(identifier)){
                        restartExercise(identifier);
                    } else{
                        startActivity(afterExerciseIntent);
                    }
                }
            });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    private void restartExercise(String identifier){
        /* Restart the exercise:
         * Remove from the list of completed exercises, set all questions to be not completed,
         * set all the user answers to null
         * start constructing the questions from scratch*/
        for(Question q : getExercise(identifier).getQuestions()){
            q.setCompleted(false);
            q.userGivenAnswer = null;
        }
        if(isCompletedExercise(identifier)) { //we may only have the exercise in-progress
            UserProgress.completedExercises.remove(getExercise(identifier));
        }else if(isInProgressExercise(identifier)){
            UserProgress.exercisesInProgress.remove(getExercise(identifier));
        }
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
