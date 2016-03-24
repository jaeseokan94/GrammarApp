package com.example.spanishgrammarapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Sets up a Listening Comprehension Activity
 */
public class ListeningComprehensionActivity extends AppCompatActivity implements View.OnClickListener{

    private String url;//url of the audio
    private ArrayList<Integer> cAnswers;// the correct answers
    private Button play;// to play and pause the audio
    private Button stop;//to stop the audio
    private MediaPlayer player;// used to ready the audio to execute
    private String status;// the status of the audio (PLAYED,PAUSED OR STOPPED)
    private SeekBar seekBar;// controls the current position of the audio
    private final Handler handler = new Handler();
    private ArrayList<CheckBox> checkBoxes;// the checkboxes containing all the answers
    private TextView currentTime;//shows current time position of the audio
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_comprehension);

        //intent to get the url and the answers
        url = getIntent().getStringExtra("url");
        ArrayList<String> answers = getIntent().getStringArrayListExtra("answers");
        cAnswers = getIntent().getIntegerArrayListExtra("cAnswers");

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        RelativeLayout topView = (RelativeLayout) findViewById(R.id.questionView);

        // makes the audio ready to be played
        readyAudio();

        //sets up the play/pause button
        play = (Button) findViewById(R.id.playPauseBtn);
        play.setText("Play/Pause");
        play.setOnClickListener(this);

        //sets up the stop button
        stop = (Button) findViewById(R.id.stopBtn);
        stop.setText("Stop");
        stop.setOnClickListener(this);

        //sets up the seekbar
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(player.getDuration());
        //enables the seekbar to give the current progress of the audio
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentTime.setText(convertTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //sets the audio to seek when the seekbar is moved
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seekPlayer(v);
                return false;
            }
        });

        //sets up the current time of teh audio
        currentTime = (TextView) findViewById(R.id.currentTime);
        currentTime.setText("0:00");

        //sets up the total time of the audio
        TextView endTime = (TextView) findViewById(R.id.endTime);
        endTime.setText(convertTime(player.getDuration()));

        LinearLayout optionsView = (LinearLayout) findViewById(R.id.optionsView);

        //sets up the checkboxes containing all the answers
        checkBoxes = new ArrayList<>();
        for (int i =0;i< answers.size();i++){

            checkBoxes.add(new CheckBox(this));
            checkBoxes.get(i).setText(answers.get(i));
            checkBoxes.get(i).setTag(i + 1);
            checkBoxes.get(i).setTextColor(Color.WHITE);
            optionsView.addView(checkBoxes.get(i));

        }

        RelativeLayout bottomView = (RelativeLayout) findViewById(R.id.questionView);

        //sets up the check button
        Button checkButton = (Button) findViewById(R.id.checkBtn);
        checkButton.setText("Check!");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });

    }

    /**
     * This listener method functions the play/pause and stop buttons.
     * @param view the button being clicked
     */
    @Override
    public void onClick(View view) {

        if (view == play){

            if(status=="stopped"){
                readyAudio();
                player.start();
                seekBarUpdater();
                status = "played";
            }else if(status == "played"){
                player.pause();
                status = "paused";
            }else{
                player.start();
                seekBarUpdater();
                status = "played";
            }

        }else if(view == stop){
            player.pause();
            player.reset();
            status = "stopped";

        }
    }


    /**
     * This class loads the audio file from url and makes it ready to play
     */
    public void readyAudio(){

        try {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(url);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * This class enables the SeekBar to control the time position of the audio
     * @param view the seekbar being updated
     */
    public void seekPlayer(View view){
        if (player.isPlaying()){
            SeekBar sB = (SeekBar)view;
            player.seekTo(sB.getProgress());
        }

    }


    /**
     * Updates the SeekBar based on the time position of the audio
     */
    public void seekBarUpdater(){
        seekBar.setProgress(player.getCurrentPosition());

        if(player.isPlaying()){
            Runnable notification = new Runnable() {
                @Override
                public void run() {
                    seekBarUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }else{
            player.pause();
            seekBar.setProgress(0);

        }
    }


    /**
     * Checks the validity of the chosen answers
     */
    public void checkAnswer() {

        ArrayList<CheckBox> list = new ArrayList<>();
        for (int i =0; i<checkBoxes.size();i++){

            if(checkBoxes.get(i).isChecked()){
                list.add(checkBoxes.get(i));
            }
        }


        Integer listSize = list.size();
        if (listSize==cAnswers.size()){
            for (int i=0;i<cAnswers.size();i++){
                for (int j =0;j<list.size();j++){
                    if (list.isEmpty()){
                        break;
                    }
                    if(list.get(j).getTag()==cAnswers.get(i)){
                        list.remove(j);
                    }


                }
            }}



        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        if (list.isEmpty()&&listSize==cAnswers.size()) {

            //do something when correct

            dialogBuilder.setTitle("Well Done");
            dialogBuilder.setMessage("This is the Correct Answer!");
            dialogBuilder.setPositiveButton("Next Question", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do something
                }
            });
        } else {

            //do something when incorrect

            dialogBuilder.setTitle("Try Again");
            dialogBuilder.setMessage("Incorrect Answer!");
            dialogBuilder.setPositiveButton("TryAgain", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do something
                }
            });
            dialogBuilder.setNegativeButton("End Quiz", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do something
                }
            });
        }
        dialogBuilder.show();

    }


    /**
     * converts milliseconds into an appropriate format for minutes
     * @param value the milliseconds value
     * @return minutes format for milliseconds
     */
    public String convertTime(int value){

        Integer int1 = (int)(TimeUnit.MILLISECONDS.toSeconds(value));
        Integer mins = int1 /60;
        Integer secs = int1-(mins*60);
        if(secs<10){return mins +":"+"0"+secs;
        }else{return mins +":"+secs;}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.listen_instructions) {
            String instruction = "Play the audio file and tick the boxes accordingly";
            Toast toast = Toast.makeText(this, instruction, Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
