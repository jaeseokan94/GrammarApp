package com.example.spanishgrammarapp;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class RecordingToolActivity extends AppCompatActivity {

    // called when the activity is first created
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    private String OUTPUT_FILE = "files/TEST.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_tool);
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
            case R.id.finishBtn:
                try{
                    stopRecording();
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
//        String state;
//        state = Environment.getExternalStorageState();
//        ListView lv = new ListView(this);
//
//        if (Environment.MEDIA_MOUNTED.equals(state));
//        {
//            File root = Environment.getExternalStorageDirectory();
//            File Dir = new File(root.getAbsolutePath()+"/MyAppFile");
//            if(!Dir.exists())
//            {
//                Dir.mkdir();
//            }
//            File file = new File(Dir, "MyMessage.txt");
//            String message = lv.toString();
//            try {
//                FileOutputStream fop = new FileOutputStream(file);
//                fop.write(message.getBytes());
//                fop.close();
//            }
//            catch (FileNotFoundException e){
//                e.printStackTrace();
//            }
//            catch (IOException e ){
//                e.printStackTrace();
//            }
        try {
            FileOutputStream fos = new FileOutputStream(new File(this.getBaseContext().getFilesDir(),"userProgress.ser"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(wrapper);
            fos.close();
            oos.close();
            System.out.println("Progress saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: File not written");
        }




       ListView lv = new ListView(this);



        fileList();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    playRecording();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

    }


    private void stopRecording() {
        if(recorder != null)
            recorder.stop();
    }

    private void playRecording() throws IOException {
        ditchMediaPlayer();
        mediaPlayer=new MediaPlayer();
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