package com.example.spanishgrammarapp;



import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RecordingToolActivity extends AppCompatActivity  {

    // called when the activity is first created
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    File home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_tool);
        home = new File(this.getFilesDir().getPath());
        OUTPUT_FILE = home+"/currentRecording";
        ((ListView) findViewById(R.id.listView1)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    playRecording(home + "/" + ((TextView) view).getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        updatePlaylist();
    }

    public void updatePlaylist() {
        List<String> recordings = new ArrayList<>();

        if (home.listFiles().length > 0 ) {
            File[] files = home.listFiles(new Mp3Filter());
            for (File file1 : files) {
                recordings.add(file1.getName());
            }
            ArrayAdapter<String> recordingList = new ArrayAdapter<>(this, R.layout.recording_item, recordings);
            ((ListView) findViewById(R.id.listView1)).setAdapter(recordingList);
        }
    }

    public void buttonTapped(View view){
        switch(view.getId()){
            case R.id.startBtn:
                try{
                    beginRecording();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.playBtn:
                try{
                    playRecording(OUTPUT_FILE);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.stopBtn:
                try{
                    stopPlayback();
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.saveBtn:
                try{
                    saveLastRecordedAudio();
                } catch (Exception e){
                    e.printStackTrace();
                }
        }
    }


    private void saveLastRecordedAudio() throws IOException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String timeStamp = dateFormat.format(new Date());
            FileOutputStream fos = new FileOutputStream(new File(this.getBaseContext().getFilesDir(),timeStamp+".mp3"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            fos.close();
            oos.close();
            System.out.println("Progress saved");


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not written");

        }
    }

    private void beginRecording() throws IOException {
        ditchMediaRecorder();
        File outFile = new File(OUTPUT_FILE);

        if(outFile.exists())
            outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.prepare();
        recorder.start();
    }

    private void stopPlayback() {
        if(mediaPlayer != null)
            mediaPlayer.stop();

        if(recorder != null)
            recorder.stop();

    }

    private void playRecording(String filename) throws IOException {
        ditchMediaPlayer();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDataSource(filename);
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    private void ditchMediaPlayer() {
        if(mediaPlayer != null){
            try{
                mediaPlayer.release();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //If the media recorder is already in use
    private void ditchMediaRecorder(){
        if(recorder != null)
            recorder.release();
    }



}

