package com.example.spanishgrammarapp;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
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


public class RecordingActivity extends AppCompatActivity  {

    // called when the activity is first created
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    File home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        home = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        System.out.println("OUTPUT_FILE: "+OUTPUT_FILE);
        ((ListView) findViewById(R.id.listView1)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    System.out.println("playRecording parameter: " + Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ((TextView) view).getText().toString());
//                    playRecording(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ((TextView) view).getText().toString());
                    OUTPUT_FILE = home+"/"+((TextView) view).getText().toString();
                    playRecording();
                    System.out.println("Playing saved recording");

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
                    playRecording();
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String timeStamp = dateFormat.format(new Date());
//          FileOutputStream fos = new FileOutputStream(new File(this.getBaseContext().getFilesDir(),timeStamp+".mp3"));
            FileOutputStream fos = new FileOutputStream(new File(this.getBaseContext().getFilesDir()+ "/" + timeStamp +".mp3"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            fos.close();
            oos.close();
            System.out.println("Progress saved");
            System.out.println(new File(this.getBaseContext().getFilesDir(),"test1.mp3"));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not written");

        }
    }

    private void beginRecording() throws IOException {
        ditchMediaRecorder();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());
        OUTPUT_FILE = home+"/"+timeStamp+".mp3";

        File outFile = new File(OUTPUT_FILE);
        System.out.println("beginRecording:   "+OUTPUT_FILE);
        if(outFile.exists())
            outFile.delete();

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(OUTPUT_FILE);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.prepare();
        recorder.start();
    }


    private void stopPlayback() {
        if(mediaPlayer != null)
            mediaPlayer.stop();

        if(recorder != null)
            recorder.stop();
    }


    private void playRecording() throws IOException {
        ditchMediaPlayer();
        mediaPlayer=new MediaPlayer();
//        System.out.println("filename: "+filename);
        mediaPlayer.setDataSource(OUTPUT_FILE);
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



