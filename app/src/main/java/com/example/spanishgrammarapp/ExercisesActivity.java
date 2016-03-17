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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.spanishgrammarapp.Data.DatabaseHelper;

import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("ResourceType")
/**This class is responsible for the creation of the GUI elements and their functionality for Exercises.
 * It constructs the exercises (conceptually, what the user is doing to improve their language skills)
 * by using an Exercise object that has a collection of questions (ArrayList of Question objects).
 * This class can create Exercises of four types: Multiple Choice, Drag&Drop, Typing, True/False.*/
public class ExercisesActivity extends AppCompatActivity {
    // Member variables for question types, to prevent bugs due to typos.
    public static final String multipleChoice = "mc";
    public static final String trueFalse = "tf";
    public static final String dragAndDrop = "dnd";
    public static final String typing = "t";

    private RelativeLayout relativeLayout; //accessed multiple times throughout the code in various methods.
    private String cAnswer; //removing this would mean it features in 4 new places.
    private ArrayList<String> answers; //accessed in multiple places (10 in the class)
    private TextView question;//This was previously declared in onCreate(), but it needs to be private for DragAndDrop. Features in 3 methods.
    private ArrayList<Question> exerciseQuestions; //accessed in multiple methods (4).
    private int currentQuestionIndex = 0;  //used in 3 different methods
    private Exercise exerciseReceived;
    private Intent afterExerciseIntent;

    /**This String describes if the exercises is in-progress, completed or a new one (not previously started).
    * It saves a lot of redundant work once we have identified the category of the Exercise.*/
    private String exerciseState = "NEW";

    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        //trying to call database here which didnt work.
        database = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent(); //the intent we used to make this activity...

        /*Here's where some magic happens. We get the exercise using the getOrMakeExercise method. If the exercise for this
        * Topic/Subtopic combination has been successfully loaded from the UserProgress.ser file then the exerciseReceived is
        * just a new reference to that object. If it did not exist in the file, then a new exercise is made.*/
        exerciseReceived = getOrMakeExercise(intent.getStringExtra(MainActivity.SUBTOPIC), intent.getStringExtra(MainActivity.TOPIC));
        exerciseQuestions = exerciseReceived.getQuestions();

        /*This intent is used for the functionality of the back button and also for leaving the exercise after it has been completed*/
        afterExerciseIntent = new Intent(this, SubtopicsActivity.class);
        afterExerciseIntent.putExtra(MainActivity.TOPIC, intent.getStringExtra(MainActivity.TOPIC));

        /*There's one container used to hold all the questions of all types, and it is the relativeLayout variable.
        * This block of code initializes it, gives it layout parameters and adds it as the content view for the activity*/
        relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addContentView(relativeLayout, relativeParams);
        /*Regardless of question type, all have a TextView. This block of code initializes the said TextView but does not assign
        * any text to it. It sets the font size, because we want this to be big and clear, and an arbitrary ID.*/
        question = new TextView(this);
        question.setTextSize(30);
        question.setId(10000);

        /*This is where we attempt to resume user's progress for this exercise. It may be the case that they have no progress
        * for this exercise, in which case this method does nothing. If it has been started, the user can resume, if it has been
        * completed the user can choose to retart the exercise.*/
        resumeUserProgress(intent.getStringExtra(MainActivity.TOPIC) + "/" + intent.getStringExtra(MainActivity.SUBTOPIC));
        reconstructGUI();
    }

    /**This method will return an exercise if it finds it in the collection of either completed or in-progress
    * exercises, and if not found in there then it will make and populate a new exercise for this topic/subtopic
    * @param subtopic The subtopic of the Topic that we want to get the exercise for
    * @param topic The topic of the exercise
    * @return Exercise for this configuration of topic/subtopic*/
    private Exercise getOrMakeExercise(String subtopic, String topic){
        String identifier = topic+"/"+subtopic; //define the identifier...
        Exercise exercise = getExerciseFromSaved(identifier); //initially use the getExerciseFromSaved method, this will return an existing exercise is there is one, otherwise null
        if(exercise==null){ //if getExerciseFromSaved returned null, then create a new exercise
            exercise = new Exercise(identifier);
            CMSconnector connector = new CMSconnector(exercise, topic, this.getBaseContext()); //pass that empty Exercise to the CMSconnector
            connector.getExercise( topic, subtopic); //the connector populates it with data from the DB
        }
        return exercise; //create a new Exercise, a set of questions (empty)
    }

    /**This method searches the saved exercises collection for one with a matching identifier
     * @return an exercises from either the completedExercises or exercisesInProgress collections, or null if featured in neither.
     * @param identifier the identifier string of the exercise to search for in the exercises saved to file*/
    private Exercise getExerciseFromSaved(String identifier){
        if(UserProgress.completedExercises.size()>0) {
            for (Exercise e : UserProgress.completedExercises) {
                if (e.getIdentifier().equals(identifier)) {
                    exerciseState = "COMPLETED";
                    return e;
                }
            }
        }
        if(UserProgress.exercisesInProgress.size()>0) {
            for (Exercise e : UserProgress.exercisesInProgress) {
                if (e.getIdentifier().equals(identifier)) {
                    exerciseState = "PROGRESS";
                    return e;
                }
            }
        }
        return null; //unlucky, didn't find the exercise in either collection
    }

    /**This method is responsible for constructing the GUI based on the Question object that we
    * are currently referring to from the exerciseQuestions variable at the index of currentQuestionIndex.
    * It achieves this by calling the right method for each question type. Each question type has its own
    * method for constructing the correct GUI. Each of the methods adds some vital Tag data that is used in
    * determining if the user has the correct answer.*/
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

    /**This method constructs the GUI of Multiple Choice type questions.*/
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

    /**This method constructs the GUI of True or False type questions*/
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

    /**This method constructs the GUI of Drag & Drop type questions*/
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

    /**This method constructs the GUI of Typing type questions*/
    private void constructTypingActivity(){
        final EditText userInput = new EditText(this);
        userInput.setId(10100);
        userInput.setInputType(InputType.TYPE_CLASS_TEXT);
        userInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                System.out.println("keyCode is:   " + keyCode);
                if (event.getAction() == KeyEvent.ACTION_DOWN && (keyCode == KeyEvent.KEYCODE_ENTER)) {
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

    /**This method creates the dialog showing the result of the user's chosen answer (e.g. if it is right or wrong).
    * It also does a few things to facilitate and do save the user progress.
    * @param qType is the type of Question that this method is called for.
    * @param view is the view that was used to select the answer (e.g. a Button)*/
    public void results(String qType, View view){
        exerciseStarted(); //mark this exercise as started after completing first question (i.e. it not counts as in-progress)
        boolean correct = false; //we assume the user is wrong unless proven to be correct below
        String userAnswer; //this String is recorded in the Question object, and is useful for tracking user's progress. Initially it had more functionality intended.
        if (qType.equals(typing)){ //typing questions need a little different modification to the userAnswer
            userAnswer = ((EditText) view).getText().toString().trim();
        }else{
            userAnswer = view.getTag().toString(); //initialization of userAnswer in the general case. The GUI elements constructed have Tag data added, which is now read.
        }
        if (userAnswer.equals(cAnswer)){
            correct=true; //we have proved the user has answered the question correctly
            exerciseReceived.incrementCorrectlyAnswered(); //increment the number of correctly answered questions for the Exercise object
        }
        exerciseQuestions.get(currentQuestionIndex).userGivenAnswer = userAnswer; //record the user answer in the Exercise object
        exerciseQuestions.get(currentQuestionIndex).addAttempt(); //the user has attempted the exercise

        final AlertDialog.Builder alert = new AlertDialog.Builder(this); //this Alert Dialog Builder allows flexible creation of the content depending on user answer (e.g. right/wrong)
        if(correct) {
            alert.setTitle("Well done!");
            alert.setMessage("This is the Correct Answer!");
            exerciseQuestions.get(currentQuestionIndex).setAnswered(true);
        }else{
            alert.setTitle("Try again later");
            alert.setMessage("Incorrect Answer!");
            exerciseQuestions.get(currentQuestionIndex).setAnswered(true); //the definition of "Completed" has changed.
        }
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (currentQuestionIndex == exerciseQuestions.size() - 1) { //we have reached the end of the exercise
                    endOfExercise();
                } else {
                    ++currentQuestionIndex;
                    reconstructGUI(); //goes to next question
                }
            }
        });

        MainActivity.userProgress.saveProgress(); //save the user progress
        alert.create();
        alert.show();
    }

    /**This method is used to identify an exercise as in-progress. If it did not yet feature in the
    * exercisesInProgress collection in UserProgress then it is added.*/
    private void exerciseStarted(){
        if(!exerciseState.equals("PROGRESS")){
            UserProgress.exercisesInProgress.add(exerciseReceived);
        }
    }

    /**This method does a couple of necessary things when we reach the end of the exercise.*/
    private void endOfExercise(){
        UserProgress.completedExercises.add(exerciseReceived);
        UserProgress.exercisesInProgress.remove(exerciseReceived); //we've completed the exercise, it's no longer in progress
        MainActivity.userProgress.saveProgress();
        ratingDialog(exerciseReceived.getCorrectnessRating());
    }

    /** This method displays the rating for the exercises with the appropriate pictures*/
    private void ratingDialog(double correctnessRating){
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams((int) (getScreenWidth()*0.6), (int) (getScreenWidth()*0.18));
        ImageView rating = new ImageView(getBaseContext());
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

    /**This method creates a dialog to communicate with the user and based on their input it
    * can resume their progress for a given Exercise or it will allow them to restart it.*/
    private void resumeUserProgress(final String identifier){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (exerciseState.equals("PROGRESS")) {
            System.out.println("User has previously started this exercise");
            dialogBuilder.setTitle("You have previously started this exercise");
            dialogBuilder.setMessage("Would you like to resume from where you have finished?\n" +
                    "Pressing \"No\" will restart this exercise.");
        }else if (exerciseState.equals("COMPLETED")) {
            System.out.println("User has completed this exercise");
            dialogBuilder.setTitle("You have already completed this exercise");
            dialogBuilder.setMessage("Would you like to restart this exercise?");
        }else {
            return; //if there is no Exercise with the given identifier, then stop executing
        }
            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (exerciseState.equals("COMPLETED")) {
                        restartExercise(identifier);
                    }
                    if(exerciseState.equals("PROGRESS")){
                        //resume from first uncompleted question
                        while (exerciseQuestions.get(currentQuestionIndex).isAnswered()) {
                            System.out.println("Completed question detected");
                            ++currentQuestionIndex;
                        }
                        reconstructGUI(); //redraw the new question
                    }
                }
            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(exerciseState.equals("PROGRESS")){
                        restartExercise(identifier);
                    }else{
                        startActivity(afterExerciseIntent);
                    }
                }
            });

        dialogBuilder.create();
        dialogBuilder.show();
    }

    /**Like you might expect it, this method restarts the exercise. It defaults some values.
     * @param identifier the identifier of the exercise to be restarted*/
    private void restartExercise(String identifier){
        /* Restart the exercise:
         * Remove from the list of completed exercises, set all questions to be not completed,
         * set all the user answers to null
         * start constructing the questions from scratch*/
        for(Question q : getExerciseFromSaved(identifier).getQuestions()){
            q.setCompleted(false);
            q.setAnswered(false);
            q.userGivenAnswer = null;
        }
        exerciseReceived.setNumCorrectlyAnswered(0);
        if(exerciseState.equals("COMPLETED")) { //we may only have the exercise in-progress
            UserProgress.completedExercises.remove(getExerciseFromSaved(identifier));
        }else if(exerciseState.equals("PROGRESS")){
            UserProgress.exercisesInProgress.remove(getExerciseFromSaved(identifier));
        }
    }

    private void reviewExercise(){
        currentQuestionIndex = 0;
        //placeholder for now
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

    /**This method override ensure the correct behaviour of the app when the back button is pressed.*/
    @Override
    public void onBackPressed() {
        startActivity(afterExerciseIntent);
        return;
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
